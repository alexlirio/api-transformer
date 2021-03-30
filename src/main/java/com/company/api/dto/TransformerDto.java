package com.company.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;

public class TransformerDto {

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 10;

	private static final String NOT_NULL_BLANK_ERROR_MSG = "The above field must be null or blank";
	private static final String NULL_BLANK_ERROR_MSG = "The above field must not be null or blank";
	private static final String MIN_RANGE_ERROR_MSG = "The above field must not be less than " + MIN_RANGE;
	private static final String MAX_RANGE_ERROR_MSG = "The above field must not be greater than " + MAX_RANGE;

	@Null(message = NOT_NULL_BLANK_ERROR_MSG)
	protected Long id;

	@NotBlank(message = NULL_BLANK_ERROR_MSG)
	private String name;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	private TransformerTeamEnum team;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer strength;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer intelligence;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer speed;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer endurance;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer rank;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer courage;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer firepower;

	@NotNull(message = NULL_BLANK_ERROR_MSG)
	@Min(value = MIN_RANGE, message = MIN_RANGE_ERROR_MSG)
	@Max(value = MAX_RANGE, message = MAX_RANGE_ERROR_MSG)
	private Integer skill;

	public TransformerDto() {
		super();
	}

	public TransformerDto(String name, TransformerTeamEnum team, Integer strength, Integer intelligence,
			Integer speed, Integer endurance, Integer rank, Integer courage, Integer firepower, Integer skill) {
		super();
		this.name = name;
		this.team = team;
		this.strength = strength;
		this.intelligence = intelligence;
		this.speed = speed;
		this.endurance = endurance;
		this.rank = rank;
		this.courage = courage;
		this.firepower = firepower;
		this.skill = skill;
	}

	public TransformerDto(Transformer entity) {
		super();
		this.id = entity.getId();
		this.name = entity.getName();
		this.team = entity.getTeam();
		this.strength = entity.getStrength();
		this.intelligence = entity.getIntelligence();
		this.speed = entity.getSpeed();
		this.endurance = entity.getEndurance();
		this.rank = entity.getRank();
		this.courage = entity.getCourage();
		this.firepower = entity.getFirepower();
		this.skill = entity.getSkill();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TransformerTeamEnum getTeam() {
		return team;
	}

	public void setTeam(TransformerTeamEnum team) {
		this.team = team;
	}

	public Integer getStrength() {
		return strength;
	}

	public void setStrength(Integer strength) {
		this.strength = strength;
	}

	public Integer getIntelligence() {
		return intelligence;
	}

	public void setIntelligence(Integer intelligence) {
		this.intelligence = intelligence;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public Integer getEndurance() {
		return endurance;
	}

	public void setEndurance(Integer endurance) {
		this.endurance = endurance;
	}

	public Integer getRank() {
		return rank;
	}

	public void setRank(Integer rank) {
		this.rank = rank;
	}

	public Integer getCourage() {
		return courage;
	}

	public void setCourage(Integer courage) {
		this.courage = courage;
	}

	public Integer getFirepower() {
		return firepower;
	}

	public void setFirepower(Integer firepower) {
		this.firepower = firepower;
	}

	public Integer getSkill() {
		return skill;
	}

	public void setSkill(Integer skill) {
		this.skill = skill;
	}

	@Override
	public String toString() {
		return "TransformerDto [id=" + id + ", name=" + name + ", team=" + team + ", strength=" + strength
				+ ", intelligence=" + intelligence + ", speed=" + speed + ", endurance=" + endurance + ", rank=" + rank
				+ ", courage=" + courage + ", firepower=" + firepower + ", skill=" + skill + "]";
	}
}
