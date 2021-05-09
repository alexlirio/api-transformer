package com.company.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.company.api.dto.TransformerDto;
import com.company.api.dto.TransformerWarDto;
import com.company.api.service.TransformerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1")
public class TransformerController {

	@Autowired
	TransformerService service;

	@PostMapping("/registry/transformers")
	public ResponseEntity<TransformerDto> create(@Valid @RequestBody TransformerDto transformerDto) {
		log.info("create() object '{}' with: {}", transformerDto.getClass().getSimpleName(), transformerDto);
		transformerDto = service.create(transformerDto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(transformerDto.getId()).toUri();
		return ResponseEntity.created(uri).body(transformerDto);
	}

	@PutMapping("/registry/transformers/{id}")
	public TransformerDto update(@Valid @PathVariable Long id, @RequestBody TransformerDto transformerDto) {
		log.info("update() object '{}' id '{}' with: {}", transformerDto.getClass().getSimpleName(), id, transformerDto);
		transformerDto.setId(id);
		return service.update(transformerDto);
	}

	@DeleteMapping("/registry/transformers/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		log.info("delete() object '{}' id '{}'", TransformerDto.class.getSimpleName(), id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/registry/transformers")
	public Page<TransformerDto> list(Pageable pegeable) {
		log.info("list() object '{}'", TransformerDto.class.getSimpleName());
		return service.list(pegeable);
	}

	@PostMapping("/war/transformers")
	public TransformerWarDto war(@RequestBody List<Long> ids) {
		log.info("War() object '{}' with: {}", ids.getClass().getSimpleName(), ids);
		return service.war(ids);
	}
}
