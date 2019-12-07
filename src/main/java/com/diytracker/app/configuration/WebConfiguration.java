package com.diytracker.app.configuration;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.diytracker.app.logging.MDCLoggingFilter;

@Configuration
@EnableAutoConfiguration
@EnableAspectJAutoProxy
public class WebConfiguration {// implements WebMvcConfigurer {



	private static final String dateFormat = "yyyy-MM-dd";
    private static final String dateTimeFormat = "yyyy-MM-dd HH:mm:ss";
    
    
//	@Override
//	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		
//		registry.addResourceHandler("/swagger-ui.html**")
//				.addResourceLocations("classpath:/resources/swagger-ui.html");
//		
//		registry.addResourceHandler("/webjars/**")
//				.addResourceLocations("classpath:/resources/webjars/");
//	}
	
//	@Override
//	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(new TokenHeaderInterceptor()).addPathPatterns("/**").excludePathPatterns(SecurityConstants.SIGN_UP_URL, SecurityConstants.SIGN_IN_URL, SecurityConstants.ACTIVATION_URL, SecurityConstants.PWD_RESET_URL, SecurityConstants.PWD_RECOVER_URL, "/configuration/image/*", "/configuration/thumbnail/*");
//	}

//	@Bean
//	public MultipartConfigElement multipartConfigElement() {
//		return new MultipartConfigElement("");
//	}
//
////	@Bean
////	public MultipartResolver multipartResolver() {
////		org.springframework.web.multipart.commons.CommonsMultipartResolver multipartResolver = new org.springframework.web.multipart.commons.CommonsMultipartResolver();
////		multipartResolver.setMaxUploadSize(100000000);
////		return multipartResolver;
////	}
////
////	@Bean
////	@Order(0)
////	public MultipartFilter multipartFilter() {
////		return new MultipartFilter();
////	}
	
	@Bean
	@Order(1)
	public MDCLoggingFilter getMDCFilter() {
		return new MDCLoggingFilter();
	}

	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(
				"This is the test email template for your email:\n%s\n");
		return message;
	}
 
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		registry.addMapping("/**")
//		.allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
//		.allowCredentials(true)
//		.allowedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Content-Type", "content-type", "x-requested-with", "x-auth-token", "x-app-id", "Origin","Accept", "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Authorization", "Accept-Language")
//		.allowedOrigins("*");
//	}
}
