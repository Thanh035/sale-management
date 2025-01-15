package com.example.demo.config;
import com.example.demo.constants.RolesConstants;
import com.example.demo.security.SpaWebFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

	private final SupportProperties supportProperties;

	public SecurityConfiguration(SupportProperties supportProperties) {
		this.supportProperties = supportProperties;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, MvcRequestMatcher.Builder mvc) throws Exception {
		http
				.cors(withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.addFilterAfter(new SpaWebFilter(), BasicAuthenticationFilter.class)
				.headers(headers ->
						headers
								.contentSecurityPolicy(csp -> csp.policyDirectives(supportProperties.getSecurity().getContentSecurityPolicy()))
								.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
								.referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
								.permissionsPolicy(permissions ->
										permissions.policy(
												"camera=(), fullscreen=(self), geolocation=(), gyroscope=(), magnetometer=(), microphone=(), midi=(), payment=(), sync-xhr=()"
										)
								)
				)
				.authorizeHttpRequests(authz ->
						// prettier-ignore
						authz
								.requestMatchers(mvc.pattern("/index.html"), mvc.pattern("/*.js"), mvc.pattern("/*.map"), mvc.pattern("/*.css")).permitAll()
								.requestMatchers(mvc.pattern("/app/**")).permitAll()
								.requestMatchers(mvc.pattern("/i18n/**")).permitAll()
								.requestMatchers(mvc.pattern("/swagger-ui/**")).permitAll()
								.requestMatchers(mvc.pattern(HttpMethod.OPTIONS, "/ping")).permitAll()
								.requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/login")).permitAll()
								.requestMatchers(mvc.pattern(HttpMethod.GET, "/api/auth/login")).permitAll()
								.requestMatchers(mvc.pattern(HttpMethod.POST, "/api/auth/register")).permitAll()

								.requestMatchers(mvc.pattern("/api/v1.0/account/reset-password/init")).permitAll()
								.requestMatchers(mvc.pattern("/api/v1.0/account/reset-password/finish")).permitAll()
								.requestMatchers(mvc.pattern("/api/v1.0/admin/**")).hasAuthority(RolesConstants.ADMIN)
								.requestMatchers(mvc.pattern("/api/v1.0/**")).authenticated()
								.requestMatchers(mvc.pattern("/v1.0/api-docs/**")).hasAuthority(RolesConstants.ADMIN)
								.requestMatchers(mvc.pattern("/management/health")).permitAll()
								.requestMatchers(mvc.pattern("/management/health/**")).permitAll()
								.requestMatchers(mvc.pattern("/management/info")).permitAll()
								.requestMatchers(mvc.pattern("/management/prometheus")).permitAll()
								.requestMatchers(mvc.pattern("/management/**")).hasAuthority(RolesConstants.ADMIN)
				)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exceptions ->
						exceptions
								.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
								.accessDeniedHandler(new BearerTokenAccessDeniedHandler())
				)
				.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
		return http.build();
	}

	@Bean
	MvcRequestMatcher.Builder mvc(HandlerMappingIntrospector introspector) {
		return new MvcRequestMatcher.Builder(introspector);
	}
}

