package com.company.api.controller;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
public class TransformerControllerTest {

	TransformerDto t1;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	public void setup() {
		t1 = TransformerDto.builder()
				.team(TransformerTeamEnum.DECEPTICON)
				.strength(7)
				.intelligence(6)
				.speed(5)
				.endurance(4)
				.rank(3)
				.courage(2)
				.firepower(1)
				.skill(1)
				.build();
	}

	@Test
	public void create() throws Exception {
		String transformerName = "Rumble";
		t1.setName(transformerName);
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
		t1.setName(transformerName);
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
		t1.setName(transformerName);
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
		String losingSurvivorName = "Hubcap";
		List<Integer> list = List.of(6, 3, 4);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(list)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("losingSurvivors[0].name", CoreMatchers.is(losingSurvivorName)));
	}

	@Test
	public void givenTransformerWar_thenBadRequest() throws Exception {
		List<Long> ids = List.of(1L, 5L);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(ids)))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	public void givenTransformerWar_thenStatus200() throws Exception {
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(list)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void givenTransformerWar_whenStatus200_thenGetChampionTeam() throws Exception {
		String teamName = "AUTOBOT";
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(list)))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
			.andExpect(MockMvcResultMatchers.jsonPath("championTeam", CoreMatchers.is(teamName)));
	}

	@Test
	public void givenTransformerWar_whenStatus200_thenGetFirstLosingSurvivorName() throws Exception {
		String losingSurvivorName = "Predaking";
		List<Integer> list = List.of(1, 2, 3, 4, 5, 6, 7, 8);
		mvc.perform(MockMvcRequestBuilders.post("/api/v1/war/transformers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(list)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.jsonPath("losingSurvivors[0].name", CoreMatchers.is(losingSurvivorName)));
	}
}
