package com.company.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityViolationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityViolationException(String message) {
		super(message);
	}
}
