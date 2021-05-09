package com.company.api.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
}
