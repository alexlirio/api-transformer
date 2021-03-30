package com.company.api.dto;

import java.util.List;

import com.company.domain.entity.TransformerTeamEnum;

public class TransformerWarDto {

	protected Integer battles;
	protected TransformerTeamEnum championTeam;
	protected List<TransformerDto> losingSurvivors;

	public TransformerWarDto() {
		super();
	}

	public TransformerWarDto(Integer battles, TransformerTeamEnum championTeam,
			List<TransformerDto> losingSurvivors) {
		super();
		this.battles = battles;
		this.championTeam = championTeam;
		this.losingSurvivors = losingSurvivors;
	}

	public Integer getBattles() {
		return battles;
	}

	public void setBattles(Integer battles) {
		this.battles = battles;
	}

	public TransformerTeamEnum getChampionTeam() {
		return championTeam;
	}

	public void setChampionTeam(TransformerTeamEnum championTeam) {
		this.championTeam = championTeam;
	}

	public List<TransformerDto> getLosingSurvivors() {
		return losingSurvivors;
	}

	public void setLosingSurvivors(List<TransformerDto> losingSurvivors) {
		this.losingSurvivors = losingSurvivors;
	}

	@Override
	public String toString() {
		return "TransformerWarDto [battles=" + battles + ", championTeam=" + championTeam + ", losingSurvivors="
				+ losingSurvivors + "]";
	}
}
