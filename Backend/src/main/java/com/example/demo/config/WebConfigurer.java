package com.example.demo.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.web.server.WebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebConfigurer implements ServletContextInitializer, WebServerFactoryCustomizer<WebServerFactory> {

	private final Environment env;

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		if (env.getActiveProfiles().length != 0) {
			log.info("Web application configuration, using profiles: {}", (Object[]) env.getActiveProfiles());
		}

		log.info("Web application fully configured");
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.addAllowedOrigin("http://localhost:4200");
		config.addAllowedOrigin("https://localhost:4200");
		config.addAllowedOrigin("blob:http://localhost:4200");
		config.addAllowedOrigin("blob:http://localhost:4200");
		
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addExposedHeader("Authorization,Link,X-Total-Count,X-FoodDeliveryBySpringBoot-alert,X-FoodDeliveryBySpringBoot-error,X-FoodDeliveryBySpringBoot-params");
		config.setAllowCredentials(true);
		config.setMaxAge((long) 1800);

		if (!CollectionUtils.isEmpty(config.getAllowedOrigins())
				|| !CollectionUtils.isEmpty(config.getAllowedOriginPatterns())) {
			log.debug("Registering CORS filter");
			source.registerCorsConfiguration("/api/**", config);
			source.registerCorsConfiguration("/management/**", config);
			source.registerCorsConfiguration("/v3/api-docs", config);
			source.registerCorsConfiguration("/swagger-ui/**", config);
		}
		return new CorsFilter(source);
	}

	@Override
	public void customize(WebServerFactory server) {
		// When running in an IDE or with ./mvnw spring-boot:run, set location of the
		// static web assets.
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
