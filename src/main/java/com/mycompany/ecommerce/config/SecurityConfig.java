package com.mycompany.ecommerce.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

// ---------- source: https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
// ---------- Security configuration
// ---------- Set allowed enpoints
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	private static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**", "/clients/**" };

	@Autowired
	private Environment environment;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// ---------- H2 config
		// ---------- Getting active profiles and check if it is test
		if (Arrays.asList(environment.getActiveProfiles()).contains("test")) {
			// ---------- Granting access
			http.headers().frameOptions().disable();
		}

		// ---------- Source:
		// https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/cors/CorsConfiguration.html
		// ---------- If there is a corsConfigurationSource(), apply them
		// ---------- Disabling csrf, since this is a stateless application
		http.cors().and().csrf().disable();

		// ---------- Authorizing routes from PUBLIC_MATCHERS
		// ---------- Authorizing GET routes from PUBLIC_MATCHERS_GET
		// ---------- For all others endpoints, request authentication
		http.authorizeRequests()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.anyRequest().authenticated();

		// ---------- This application will not store user SESSIONS
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	// ---------- Allowing basic access for all endpoints
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
	
	// ---------- Configuration for encrypting password
	// ---------- Can be injected in any class
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		
		return new BCryptPasswordEncoder();
		
	}

}
