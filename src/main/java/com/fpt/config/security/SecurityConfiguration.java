package com.fpt.config.security;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fpt.config.exception.AuthExceptionHandler;
import com.fpt.enums.UserRole;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private AuthExceptionHandler authExceptionHandler;
	
	@Autowired
	private JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Bean
	public AuthenticationManager authenticationManager(
			AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain configureSecurity(HttpSecurity http) throws Exception {
		http
			.cors(withDefaults())
			.csrf((csrf) -> csrf.disable())
			.authorizeHttpRequests((requests) -> requests
					// common
					.requestMatchers("/api/v1/auth/change-password",
									 "/api/v1/auth/old-password-match",
									 "/api/v1/users/profile",
									 "/api/v1/users/managers",
									 "/api/v1/users/students",
									 "/api/v1/subjects/selection", 
							 		 "/api/v1/settings/semesters",
							 		 "/api/v1/settings/faculties",
							 		 "/api/v1/settings/userRoles").authenticated()
					
					.requestMatchers("/api/v1/auth/**", 
									 "**/exists", 
									 "/contact",
									 "/about-us",
									 "/api/v1/files/**").anonymous()
					
					.requestMatchers("/api/v1/students/classes/**")
						.hasAnyAuthority(UserRole.MANAGER.name(),
										 UserRole.TEACHER.name(),
										 UserRole.STUDENT.name())
					
					.requestMatchers("/api/v1/projects/selection")
						.hasAnyAuthority(UserRole.TEACHER.name())
					
					.requestMatchers("api/v1/students/project",
									 "/api/v1/milestones/student",
									 "/api/v1/project-settings/types/**")
						.hasAnyAuthority(UserRole.TEACHER.name(),
										 UserRole.STUDENT.name())
					
					.requestMatchers("/api/v1/classes/managers",
									 "api/v1/classes/selection")
						.hasAnyAuthority(UserRole.MANAGER.name(),
										 UserRole.TEACHER.name())
					 
					.requestMatchers("/api/v1/users/managers/**",
							 		 "/api/v1/users/teachers/**")
						.hasAnyAuthority(UserRole.MANAGER.name(), 
									 	 UserRole.TEACHER.name())
					
					// role
					.requestMatchers("/api/v1/settings/**", 
							 		 "/api/v1/users/**",
							 		 "/api/v1/subjects/**")
						.hasAnyAuthority(UserRole.ADMIN.name())
					
					.requestMatchers("/api/v1/projects/**")
						.hasAnyAuthority(UserRole.MANAGER.name(),
										 UserRole.TEACHER.name(),
										 UserRole.STUDENT.name())
					
					.requestMatchers("/api/v1/classes/**",
									 "/api/v1/students/**")
						.hasAnyAuthority(UserRole.MANAGER.name())
					
					.requestMatchers("/api/v1/issues/**")
						.hasAnyAuthority(UserRole.TEACHER.name(),
							 		 	 UserRole.STUDENT.name())
					
					.requestMatchers("/api/v1/reports/**")
						.hasAnyAuthority(UserRole.TEACHER.name(),
								 		 UserRole.STUDENT.name())

					.requestMatchers("/api/v1/project-settings/**")
					.hasAnyAuthority(UserRole.TEACHER.name())
					.anyRequest().authenticated())
			.httpBasic(withDefaults())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling()
			.authenticationEntryPoint(authExceptionHandler)
            .accessDeniedHandler(authExceptionHandler);
		
		return http.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		configuration.applyPermitDefaultValues();
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
