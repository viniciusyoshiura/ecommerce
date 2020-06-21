package com.mycompany.ecommerce.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.mycompany.ecommerce.resources.exceptions.CustomAccessDeniedHandler;
import com.mycompany.ecommerce.security.JWTAuthenticationFilter;
import com.mycompany.ecommerce.security.JWTAuthorizationFilter;
import com.mycompany.ecommerce.security.JWTUtils;

// ---------- source: https://auth0.com/blog/implementing-jwt-authentication-on-spring-boot/
// ---------- Security configuration
// ---------- Set allowed enpoints
// ---------- @EnableGlobalMethodSecurity allows pre-authorization annotations (@PreAuthorize)
// ---------- See CategoryResource for example
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	private static final String[] PUBLIC_MATCHERS_GET = { "/products/**", "/categories/**", "/states/**"};

	private static final String[] PUBLIC_MATCHERS_POST = { "/clientes/**", "/auth/forgot/**"};
	
	@Autowired
	private Environment environment;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtils jwtUtils;

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
		// ---------- Authorizing POST routes from PUBLIC_MATCHERS_POST
		// ---------- For all others endpoints, request authentication
		http.authorizeRequests()
				.antMatchers(PUBLIC_MATCHERS).permitAll()
				.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
				.antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll()
				.anyRequest().authenticated();

		// ---------- This application will not store user SESSIONS
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// ---------- Adding JWTAuthentication filter
		// ---------- authenticationManager() is available in WebSecurityConfigurerAdapter
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtils));

		// ---------- Adding JWTAuthorization filter
		// ---------- authenticationManager() is available in WebSecurityConfigurerAdapter
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtils, userDetailsService));
		
		// ---------- Exception Handlers
		http.exceptionHandling().accessDeniedHandler(accessDeniedHandler());
	}

	// ---------- Overriding Spring Authentication
	// ---------- Configuring Authentication Mechanism
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		// ---------- Setting who is the service and who is the encoder
		// ---------- Spring searches from userDetailsService implementation, since it
		// is an interface
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
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
	
	// ---------- Configuration for handling AccessDeniedException
	// Source: https://www.baeldung.com/spring-security-custom-access-denied-page
	@Bean
	public AccessDeniedHandler accessDeniedHandler(){
	    return new CustomAccessDeniedHandler();
	}

}
