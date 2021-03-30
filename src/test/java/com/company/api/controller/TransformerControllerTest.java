package com.company.api.controller;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.company.api.dto.TransformerDto;
import com.company.domain.entity.TransformerTeamEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class TransformerControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void create() throws Exception {
		String transformerName = "Rumble";
		TransformerDto t1 = new TransformerDto(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/registry/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(t1)))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(transformerName)));
	}

	@Test
	public void update() throws Exception {
		String transformerName = "Overkill";
		TransformerDto t1 = new TransformerDto(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/v1/registry/transformers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(t1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		t1 = objectMapper.readValue(contentAsString, TransformerDto.class);

		transformerName = "Blackout";
		t1.setName(transformerName);
		mvc.perform(MockMvcRequestBuilders.put("/api/v1/registry/transformers/{id}", t1.getId())
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(t1)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("name", CoreMatchers.is(transformerName)));
	}

	@Test
	public void delete() throws Exception {
		String transformerName = "Frenzy";
		TransformerDto t1 = new TransformerDto(transformerName, TransformerTeamEnum.DECEPTICON, 7, 6, 5, 4, 3, 2, 1, 1);
		ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/v1/registry/transformers")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(t1)))
				.andExpect(MockMvcResultMatchers.status().isCreated());

		MvcResult result = resultActions.andReturn();
		String contentAsString = result.getResponse().getContentAsString();
		t1 = objectMapper.readValue(contentAsString, TransformerDto.class);

		mvc.perform(MockMvcRequestBuilders.delete("/api/v1/registry/transformers/{id}", t1.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	@Test
	public void list() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/v1/registry/transformers"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void war() throws Exception {
		List<Long> ids = List.of(1L, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ids)))
			.andExpect(MockMvcResultMatchers.status().is5xxServerError());
	}
}
