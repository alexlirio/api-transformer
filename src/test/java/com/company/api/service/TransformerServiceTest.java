package com.company.api.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import com.company.api.dto.TransformerDto;
import com.company.api.exception.EntityNotFoundException;
import com.company.api.exception.NoSurvivorException;
import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;
import com.company.domain.repository.ITransformerRepository;

@ActiveProfiles("test")
public class TransformerServiceTest {

	@Mock
	private ITransformerRepository repository;

	@InjectMocks
	private TransformerService service;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void create() {
		Long transformerId = 11L;
		String transformerName = "Rumble";
		Transformer t1 = new Transformer(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		t1.setId(transformerId);
		Mockito.when(repository.save(Mockito.any())).thenReturn(t1);

		TransformerDto t2 = new TransformerDto(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		TransformerDto t3 = service.create(t2);
		Assertions.assertEquals(t3.getId(), transformerId);
	}

	@Test
	public void update() {
		Long transformerId = 12L;
		String transformerName = "Overkill";
		Transformer t1 = new Transformer(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		t1.setId(transformerId);
		Mockito.when(repository.existsById(Mockito.eq(transformerId))).thenReturn(true);
		Mockito.when(repository.save(Mockito.any())).thenReturn(t1);

		TransformerDto t2 = new TransformerDto("wrong name", TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		t2.setId(transformerId);
		TransformerDto t3 = service.update(t2);
		Assertions.assertEquals(t3.getName(), transformerName);
	}

	@Test
	public void delete() {
		Long transformerId = 13L;
		String exceptionMessage = String.format("Object with id %s not found", transformerId);
		Mockito.doThrow(new EntityNotFoundException(exceptionMessage)).when(repository)
				.deleteById(Mockito.eq(transformerId));

		Exception ex = Assertions.assertThrows(EntityNotFoundException.class, () -> service.delete(transformerId));
		Assertions.assertEquals(ex.getMessage(), exceptionMessage);
	}

	@Test
	public void list() {
		int listSize = 2;
		Page<Transformer> tl1 = new PageImpl<Transformer>(List.of(
				new Transformer("Rumble", TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1),
				new Transformer("Overkill", TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1)));
		Pageable peageble = PageRequest.of(0, listSize);
		Mockito.when(repository.findAll(peageble)).thenReturn(tl1);

		List<TransformerDto> tl2 = service.list(peageble).getContent();
		Assertions.assertEquals(tl2.size(), listSize);
	}

	@Test
	public void war() {
		List<Long> ids = List.of(1L, 5L);
		String exceptionMessage = "Method not implemented yet";
		Mockito.doThrow(new NoSurvivorException(exceptionMessage)).when(repository).findAllByIdInAndTeamOrderByRankDesc(ids, TransformerTeamEnum.AUTOBOT);
		Mockito.doThrow(new NoSurvivorException(exceptionMessage)).when(repository).findAllByIdInAndTeamOrderByRankDesc(ids, TransformerTeamEnum.DECEPTICON);

		Exception ex = Assertions.assertThrows(NoSurvivorException.class, () -> service.war(ids));
		Assertions.assertEquals(ex.getMessage(), exceptionMessage);
	}
}
