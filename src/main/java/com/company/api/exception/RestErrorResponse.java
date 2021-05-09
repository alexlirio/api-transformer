package com.company.api.exception;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@XmlRootElement(name = "error")
public class RestErrorResponse {

	// General error message about nature of error
	private String message;
	
	// Specific errors in API request processing
	private List<String> details;
}
