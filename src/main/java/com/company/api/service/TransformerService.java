package com.company.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.api.dto.TransformerDto;
import com.company.api.dto.TransformerWarDto;
import com.company.api.exception.EntityNotFoundException;
import com.company.api.exception.EntityViolationException;
import com.company.api.exception.NoSurvivorException;
import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;
import com.company.domain.repository.ITransformerRepository;

@Service
public class TransformerService {

	private Logger logger = LoggerFactory.getLogger(TransformerService.class);
	
	private static final String OPTIMUS_PRIME = "Optimus Prime";
	private static final String PREDAKING = "Predaking";
	
	private static final String BATTLE_RESULT_TEMPLATE_LOG = "['{}' vs '{}'] - '{}' {}";

	private static enum IndividualBattleResultEnum {
		DRAW, AUTOBOT_WON, DECEPTICON_WON, AUTOBOT_RAN_AWAY, DECEPTICON_RAN_AWAY
	}

	@Autowired
	ITransformerRepository repository;

	@Transactional
	public TransformerDto create(TransformerDto dto) {
		logger.info("create() object '{}' with: {}", dto.getClass().getSimpleName(), dto);
		boolean exists = repository.existsByNameIgnoreCase(dto.getName());
		if (exists) {
			String errorMsg = "Object with name " + dto.getName() + " found";
			logger.error(errorMsg);
			throw new EntityViolationException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public TransformerDto update(TransformerDto dto) {
		logger.info("update() object '{}' with: {}", dto.getClass().getSimpleName(), dto);
		boolean exists = repository.existsById(dto.getId());
		if (!exists) {
			String errorMsg = "Object with id " + dto.getId() + " not found";
			logger.error(errorMsg);
			throw new EntityNotFoundException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public void delete(Long id) {
		logger.info("delete() object '{}' id '{}'", TransformerDto.class.getSimpleName(), id);
		boolean exists = repository.existsById(id);
		if (!exists) {
			String errorMsg = "Object with id " + id + " not found";
			logger.error(errorMsg);
			throw new EntityNotFoundException(errorMsg);
		}
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<TransformerDto> list() {
		logger.info("list() object '{}'", TransformerDto.class.getSimpleName());
		List<Transformer> result = repository.findAll();
		return result.stream().map(o -> new TransformerDto(o)).collect(Collectors.toList());
	}

	@Transactional
	public TransformerWarDto war(List<Long> ids) {
		logger.info("war() object '{}' ids '{}'", TransformerWarDto.class.getSimpleName(), ids);

		TransformerWarDto transformerWarResponseDto = new TransformerWarDto();

		// #1. The transformers are split into two teams based on if they are Autobots or Decepticons.
		// #2. The teams should be sorted by rank and faced off one on one against each other in order to determine a victor, the loser is eliminated.
		List<TransformerDto> autobots = repository.findAllByIdInAndTeamOrderByRankAsc(ids, TransformerTeamEnum.AUTOBOT) // #2. Doubt: The worst ranked will battle before?
				.stream()
				.map(t -> new TransformerDto(t))
				.collect(Collectors.toList());
		List<TransformerDto> decepticons = repository.findAllByIdInAndTeamOrderByRankAsc(ids, TransformerTeamEnum.DECEPTICON) // #2. Doubt: the worst ranked will battle before?
				.stream()
				.map(t -> new TransformerDto(t))
				.collect(Collectors.toList());

		// #3. A battle between opponents uses the following rules:
		int individualBattles = Math.min(autobots.size(), decepticons.size());
		transformerWarResponseDto.setBattles(individualBattles);
		int autobotWins = 0;
		int decepticonWins = 0;
		List<TransformerDto> autobotSurvivors = new ArrayList<>();
		List<TransformerDto> decepticonSurvivors = new ArrayList<>();
		for (int i = 0; i < individualBattles; i++) {

			TransformerDto autobot = autobots.get(i);
			TransformerDto decepticon = decepticons.get(i);

			IndividualBattleResultEnum individualBattleResult = individualBattle(autobots.get(i), decepticons.get(i));

		    switch (individualBattleResult) {
		        case AUTOBOT_WON:
					autobotSurvivors.add(autobot);
					autobotWins++;
		        	break;
		        case DECEPTICON_WON:
		        	decepticonSurvivors.add(decepticon);
		        	decepticonWins++;
		        	break;
		        case AUTOBOT_RAN_AWAY:
		        	autobotSurvivors.add(autobot);
		        	decepticonSurvivors.add(decepticon);
		        	decepticonWins++;
		        	break;
		        case DECEPTICON_RAN_AWAY:
		        	autobotSurvivors.add(autobot);
		        	decepticonSurvivors.add(decepticon);
		        	autobotWins++;
		        	break;
		        case DRAW:
		        	break;
		    }
		}

		// #6. Any Transformers who don’t have a fight are skipped (i.e. if it’s a team of 2 vs. a team of 1, there’s only going to be one battle).
		autobotSurvivors.addAll(autobots.stream().skip(individualBattles).collect(Collectors.toList())); // If any Autobot has 'skipped', he is a survivor.
		logger.info("war() autobotSurvivors: '{}'", autobotSurvivors);
		decepticonSurvivors.addAll(decepticons.stream().skip(individualBattles).collect(Collectors.toList())); // If any Decepticon has 'skipped', he is a survivor.
		logger.info("war() decepticonsSurvivors: '{}'", decepticonSurvivors);

		// #7. The team who eliminated the largest number of the opposing team is the winner.
		if (autobotWins > decepticonWins) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobotWins, decepticonWins, "Autobot", "won");
			transformerWarResponseDto.setChampionTeam(TransformerTeamEnum.AUTOBOT);
			transformerWarResponseDto.setLosingSurvivors(decepticonSurvivors);
		} else if (autobotWins < decepticonWins) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobotWins, decepticonWins, "Decepticon", "won");
			transformerWarResponseDto.setChampionTeam(TransformerTeamEnum.DECEPTICON);
			transformerWarResponseDto.setLosingSurvivors(autobotSurvivors);
		}

		logger.info("war() object '{}' returned with: {}", transformerWarResponseDto.getClass().getSimpleName(), transformerWarResponseDto);
		return transformerWarResponseDto;
	}

	/**
	 * Rules: <br><br>
	 * #1. The transformers are split into two teams based on if they are Autobots or Decepticons. <br><br>
	 * #2. The teams should be sorted by rank and faced off one on one against each other in order to determine a victor, the loser is eliminated. <br><br>
	 * #3. A battle between opponents uses the following rules: <br><br>
	 * #3.1. If any fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent, the opponent automatically wins the face-off regardless of overall rating (opponent has ran away). <br><br>
	 * #3.2. Otherwise, if one of the fighters is 3 or more points of skill above their opponent, they win the fight regardless of overall rating. <br><br>
	 * #4. The winner is the Transformer with the highest overall rating. <br><br>
	 * #5. In the event of a tie, both Transformers are considered destroyed. <br><br>
	 * #6. Any Transformers who don’t have a fight are skipped (i.e. if it’s a team of 2 vs. a team of 1, there’s only going to be one battle). <br><br>
	 * #7. The team who eliminated the largest number of the opposing team is the winner. <br><br>
	 * <br>
	 * Special rules: <br><br>
	 * #8. Any Transformer named Optimus Prime or Predaking wins his fight automatically regardless of any other criteria. <br><br>
	 * #9. In the event either of the above face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed.
	 * 
	 * @param autobot
	 * @param decepticon
	 * @return IndividualBattleResultEnum
	 */
	private IndividualBattleResultEnum individualBattle(TransformerDto autobot, TransformerDto decepticon) {

		// Special Rules:
		// #9. In the event either of the above face each other (or a duplicate of each other), the game immediately ends with all competitors destroyed.
		if (autobot.getName().trim().equalsIgnoreCase(OPTIMUS_PRIME) && decepticon.getName().trim().equalsIgnoreCase(PREDAKING)) {
			String warnMsg = String.format("Battle ['%s' vs '%s'] - '%s' %s", autobot.getName(), decepticon.getName(), "both teams", "were destroyed");
			logger.warn(warnMsg);
			throw new NoSurvivorException(warnMsg);
		}

		// #8. Any Transformer named Optimus Prime or Predaking wins his fight automatically regardless of any other criteria
		if (autobot.getName().trim().equalsIgnoreCase(OPTIMUS_PRIME)) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), autobot.getName(), "won");
			return IndividualBattleResultEnum.AUTOBOT_WON;
		}
		if (decepticon.getName().trim().equalsIgnoreCase(PREDAKING)) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), decepticon.getName(), "won");
			return IndividualBattleResultEnum.DECEPTICON_WON;
		}

		// #3.1. If any fighter is down 4 or more points of courage and 3 or more points of strength compared to their opponent,
		// the opponent automatically wins the face-off regardless of overall rating (opponent has ran away).
		if (autobot.getCourage() - decepticon.getCourage() <= -4
				&& autobot.getStrength() - decepticon.getStrength() <= -3) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), autobot.getName(), "ran away");
			return IndividualBattleResultEnum.AUTOBOT_RAN_AWAY; // If the opponent has 'run away', he is a survivor.
		}
		if (decepticon.getCourage() - autobot.getCourage() <= -4
				&& decepticon.getStrength() - autobot.getStrength() <= -3) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), decepticon.getName(), "ran away");
			return IndividualBattleResultEnum.DECEPTICON_RAN_AWAY; // If the opponent has 'run away', he is a survivor.
		}

		// #3.2. Otherwise, if one of the fighters is 3 or more points of skill above their opponent,
		// they win the fight regardless of overall rating.
		if (autobot.getSkill() - decepticon.getSkill() >= 3) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), autobot.getName(), "won");
			return IndividualBattleResultEnum.AUTOBOT_WON;
		}
		if (decepticon.getSkill() - autobot.getSkill() >= 3) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), decepticon.getName(), "won");
			return IndividualBattleResultEnum.DECEPTICON_WON;
		}

		// #4. The winner is the Transformer with the highest overall rating.
		// #5. In the event of a tie, both Transformers are considered destroyed.
		if (getOverral(autobot) > getOverral(decepticon)) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), autobot.getName(), "won");
			return IndividualBattleResultEnum.AUTOBOT_WON;
		} else if (getOverral(autobot) < getOverral(decepticon)) {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), decepticon.getName(), "won");
			return IndividualBattleResultEnum.DECEPTICON_WON;
		} else {
			logger.info(BATTLE_RESULT_TEMPLATE_LOG, autobot.getName(), decepticon.getName(), "both", "were destroyed");
			return IndividualBattleResultEnum.DRAW;
		}
	}

	private int getOverral(TransformerDto transformer) {
		return transformer.getStrength() + transformer.getIntelligence() + transformer.getSpeed()
				+ transformer.getEndurance() + transformer.getFirepower();
	}

}
