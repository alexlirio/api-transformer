package com.company.api.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {

	@Value("${app.version}")
	private String appVersion;

	@Bean
    public Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
        		.select()
        		.apis(RequestHandlerSelectors.basePackage("com.company.api.controller"))
        		.paths(PathSelectors.ant("/api/**"))
        		.build()
        		.apiInfo(metaData());
    }

	private ApiInfo metaData() {
		return new ApiInfoBuilder()
		        .title("API Transformer")
		        .description("Spring Boot REST API for the Transformer Tech Challenge")
		        .version(appVersion)
		        .license("Apache 2.0")
		        .contact(new Contact("Alex Lirio", "https://www.linkedin.com/in/alexlirio", "alexlirioti@gmail.com"))
		        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0")
		        .build();
	}
}
