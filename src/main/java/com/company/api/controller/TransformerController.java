package com.company.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping(value = "/api/v1")
public class TransformerController {

	private Logger logger = LoggerFactory.getLogger(TransformerController.class);

	@Autowired
	TransformerService service;

	@PostMapping("/registry/transformers")
	public ResponseEntity<TransformerDto> create(@Valid @RequestBody TransformerDto dto) {
		logger.info("create() object '{}' with: {}", dto.getClass().getSimpleName(), dto);
		dto = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping("/registry/transformers/{id}")
	public ResponseEntity<TransformerDto> update(@Valid @PathVariable Long id, @RequestBody TransformerDto dto) {
		logger.info("update() object '{}' id '{}' with: {}", dto.getClass().getSimpleName(), id, dto);
		dto.setId(id);
		dto = service.update(dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping("/registry/transformers/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		logger.info("delete() object '{}' id '{}'", TransformerDto.class.getSimpleName(), id);
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/registry/transformers")
	public ResponseEntity<List<TransformerDto>> list() {
		logger.info("list() object '{}'", TransformerDto.class.getSimpleName());
		List<TransformerDto> dtos = service.list();
		return ResponseEntity.ok().body(dtos);
	}

	@PostMapping("/war/transformers")
	public ResponseEntity<TransformerWarDto> war(@RequestBody List<Long> ids) {
		logger.info("War() object '{}' with: {}", ids.getClass().getSimpleName(), ids);
		return ResponseEntity.ok().body(service.war(ids));
	}
}
