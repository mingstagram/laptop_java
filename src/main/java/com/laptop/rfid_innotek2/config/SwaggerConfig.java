package com.laptop.rfid_innotek2.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
 
//@Configuration
//@EnableSwagger2 // Swagger 활성화
public class SwaggerConfig {
	
	private static final String API_NAME = "Study API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "Study API 명세서";
    
//    @Bean
    public Docket api() {
    	Parameter prameterBuilder = new ParameterBuilder()
    			.name(HttpHeaders.AUTHORIZATION)
    			.description("Access Token")
    			.modelRef(new ModelRef("string"))
    			.parameterType("header")
    			.required(false)
    			.build();
    	
    	List<Parameter> globalParameters = new ArrayList<>();
    	globalParameters.add(prameterBuilder);
    	
    	return new Docket(DocumentationType.SWAGGER_2)
    			.globalOperationParameters(globalParameters)
    			.apiInfo(apiInfo())
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("com.laptop.rfid_innotek2.controller.api"))
    			.paths(PathSelectors.any())
    			.build();
    }

	private ApiInfo apiInfo() { 
		return new ApiInfoBuilder()
					.title(API_NAME)
					.version(API_VERSION)
					.description(API_DESCRIPTION)
					.build();
	}

}
