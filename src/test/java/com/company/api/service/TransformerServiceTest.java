package com.company.api.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.company.api.dto.TransformerDto;
import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;
import com.company.domain.repository.ITransformerRepository;

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
		String exceptionMessage = "Entity Not Found";
		Mockito.when(repository.existsById(Mockito.eq(transformerId))).thenReturn(true);
		Mockito.doThrow(new EntityNotFoundException(exceptionMessage)).when(repository)
				.deleteById(Mockito.eq(transformerId));

		Exception ex = Assertions.assertThrows(EntityNotFoundException.class, () -> service.delete(transformerId));
		Assertions.assertEquals(ex.getMessage(), exceptionMessage);
	}

	@Test
	public void list() {
		int listSize = 2;
		List<Transformer> tl1 = List.of(
				new Transformer("Rumble", TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1),
				new Transformer("Overkill", TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1));
		Mockito.when(repository.findAll()).thenReturn(tl1);

		List<TransformerDto> tl2 = service.list();
		Assertions.assertEquals(tl2.size(), listSize);
	}
}
