package com.company.api.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.api.dto.TransformerDto;
import com.company.api.dto.TransformerWarDto;
import com.company.api.exception.EntityNotFoundException;
import com.company.api.exception.EntityViolationException;
import com.company.domain.entity.Transformer;
import com.company.domain.repository.ITransformerRepository;

@Service
public class TransformerService {

	private Logger logger = LoggerFactory.getLogger(TransformerService.class);
	
	@Autowired
	ITransformerRepository repository;

	@Transactional
	public TransformerDto create(TransformerDto dto) {
		logger.info("create() object '{}' with: {}", dto.getClass().getSimpleName(), dto);
		boolean exists = repository.existsByNameIgnoreCase(dto.getName());
		if (exists) {
			String errorMsg = "Object with name " + dto.getName() + " found";
			logger.error(errorMsg);
			throw new EntityViolationException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public TransformerDto update(TransformerDto dto) {
		logger.info("update() object '{}' with: {}", dto.getClass().getSimpleName(), dto);
		boolean exists = repository.existsById(dto.getId());
		if (!exists) {
			String errorMsg = "Object with id " + dto.getId() + " not found";
			logger.error(errorMsg);
			throw new EntityNotFoundException(errorMsg);
		}
		Transformer entity = new ModelMapper().map(dto, Transformer.class);
		return new TransformerDto(repository.save(entity));
	}

	@Transactional
	public void delete(Long id) {
		logger.info("delete() object '{}' id '{}'", TransformerDto.class.getSimpleName(), id);
		boolean exists = repository.existsById(id);
		if (!exists) {
			String errorMsg = "Object with id " + id + " not found";
			logger.error(errorMsg);
			throw new EntityNotFoundException(errorMsg);
		}
		repository.deleteById(id);
	}

	@Transactional(readOnly = true)
	public List<TransformerDto> list() {
		logger.info("list() object '{}'", TransformerDto.class.getSimpleName());
		List<Transformer> result = repository.findAll();
		return result.stream().map(o -> new TransformerDto(o)).collect(Collectors.toList());
	}

	//TODO - Implement war logic
	@Transactional
	public TransformerWarDto war(List<Long> ids) {
		logger.info("war() object '{}' ids '{}'", TransformerWarDto.class.getSimpleName(), ids);

		repository.findAllById(ids);
		throw new RuntimeException("Method not implemented yet");
	}
}
