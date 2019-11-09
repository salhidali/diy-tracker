package com.diytracker.app.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author salhidali
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	 @Bean
	 public Docket api() {
	    return new Docket(DocumentationType.SWAGGER_2).select()
	            .apis(RequestHandlerSelectors
	                    .basePackage("com.diytracker.app.rest.controller"))
	            .paths(PathSelectors.any())
	            .build();
	 }
    
    private ApiInfo metaData() {
        return new ApiInfoBuilder()
                .title("DIY Tracker documentation")
                .description("Project to track user diy projects")
                .version("version 1.0")
                .contact(new Contact("DIYTracker", "https://www.diytracker.com", 
                      "test@gmail.com")).build();
    }
}