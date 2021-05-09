package com.company.domain.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Range;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transformer")
public class Transformer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 10;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "name", unique = true, nullable = false)
	private String name;

	@Enumerated(EnumType.STRING)
	private TransformerTeamEnum team;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer strength;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer intelligence;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer speed;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer endurance;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer rank;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer courage;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer firepower;

	@Range(min = MIN_RANGE, max = MAX_RANGE)
	private Integer skill;
}
