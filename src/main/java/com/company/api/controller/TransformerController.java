package com.company.api.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

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
import com.company.api.service.TransformerService;

@RestController
@RequestMapping(value = "/api/v1")
public class TransformerController {

	@Autowired
	TransformerService service;

	@PostMapping("/registry/transformers")
	public ResponseEntity<TransformerDto> create(@Valid @RequestBody TransformerDto dto) {
		dto = service.create(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@PutMapping("/registry/transformers/{id}")
	public ResponseEntity<TransformerDto> update(@Valid @PathVariable Long id, @RequestBody TransformerDto dto) {
		dto.setId(id);
		dto = service.update(dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping("/registry/transformers/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/registry/transformers")
	public ResponseEntity<List<TransformerDto>> list() {
		List<TransformerDto> dtos = service.list();
		return ResponseEntity.ok().body(dtos);
	}
}
