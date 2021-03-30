package com.company.api.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.api.dto.TransformerDto;
import com.company.domain.entity.Transformer;
import com.company.domain.repository.ITransformerRepository;

@Service
public class TransformerService {

	@Autowired
	ITransformerRepository repository;

	@Transactional
	public TransformerDto create(TransformerDto dto) {
		boolean exists = repository.existsByNameIgnoreCase(dto.getName());
		if (exists) {
			String errorMsg = "Object with name " + dto.getName() + " found";
			throw new RuntimeException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public TransformerDto update(TransformerDto dto) {
		boolean exists = repository.existsById(dto.getId());
		if (!exists) {
			String errorMsg = "Object with id " + dto.getId() + " not found";
			throw new EntityNotFoundException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public void delete(Long id) {
		boolean exists = repository.existsById(id);
		if (!exists) {
			String errorMsg = "Object with id " + id + " not found";
			throw new EntityNotFoundException(errorMsg);
		}
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<TransformerDto> list() {
		List<Transformer> result = repository.findAll();
		return result.stream().map(o -> new TransformerDto(o)).collect(Collectors.toList());
	}
}
