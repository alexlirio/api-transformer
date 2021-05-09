package com.company.api.dto;

import java.util.List;

import com.company.domain.entity.TransformerTeamEnum;

import lombok.Data;

@Data
public class TransformerWarDto {

	protected Integer battles;
	protected TransformerTeamEnum championTeam;
	protected List<TransformerDto> losingSurvivors;
}
