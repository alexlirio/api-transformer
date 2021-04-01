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

@Entity
@Table(name = "transformer")
public class Transformer implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final int MIN_RANGE = 1;
	private static final int MAX_RANGE = 10;

	public Transformer() {
		super();
	}

	public Transformer(String name, TransformerTeamEnum team, Integer strength, Integer intelligence,
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
}
