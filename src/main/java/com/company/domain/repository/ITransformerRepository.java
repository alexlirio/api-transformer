package com.company.domain.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.company.domain.entity.Transformer;
import com.company.domain.entity.TransformerTeamEnum;

public interface ITransformerRepository extends PagingAndSortingRepository<Transformer, Long> {

	Boolean existsByNameIgnoreCase(String name);

	List<Transformer> findAllByIdInAndTeamOrderByRankAsc(Iterable<Long> ids, TransformerTeamEnum team);
}
