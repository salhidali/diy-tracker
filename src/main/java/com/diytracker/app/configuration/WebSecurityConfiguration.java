package com.diytracker.app.configuration;

import java.util.Arrays;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.diytracker.app.util.SecurityConstants;


@Configuration
//@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(SecurityConstants.SIGN_UP_URL, SecurityConstants.SIGN_IN_URL, SecurityConstants.ACTIVATION_URL, SecurityConstants.PWD_RESET_URL, SecurityConstants.PWD_RECOVER_URL, "/configuration/image/*", "/configuration/thumbnail/*");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
		http.csrf().disable().authorizeRequests().anyRequest().permitAll(); 
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("*"));
		configuration.setAllowedMethods(Arrays.asList("HEAD",
				"GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type", "refreshToken", "Accept", "Accept-Language"));
		configuration.setExposedHeaders(Arrays.asList("Authorization", "refreshToken", "Accept"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	FilterRegistrationBean<CorsFilter> corsConfFilter(CorsConfigurationSource corsConfigurationSource)
	{
		CorsFilter corsFilter = new CorsFilter(corsConfigurationSource);

		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(corsFilter);
		bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return bean;
	}
}