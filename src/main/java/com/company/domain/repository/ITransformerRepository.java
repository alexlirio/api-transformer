package com.company.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.company.domain.entity.Transformer;

public interface ITransformerRepository extends JpaRepository<Transformer, Long> {

	Boolean existsByNameIgnoreCase(String name);
}
