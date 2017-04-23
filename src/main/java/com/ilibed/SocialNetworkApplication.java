package com.ilibed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@SpringBootApplication
public class SocialNetworkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkApplication.class, args);
	}

	@Configuration
	@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
	public static class WebSecurityConfig extends WebSecurityConfigurerAdapter {

		private final UserDetailsService userDetailsService;
		private final AuthenticationSuccessHandler authenticationSuccessHandler;
		private final AuthenticationFailureHandler authenticationFailureHandler;

		@Autowired
		public WebSecurityConfig(UserDetailsService userDetailsService,
								 AuthenticationSuccessHandler authenticationSuccessHandler,
								 AuthenticationFailureHandler authenticationFailureHandler) {

			this.userDetailsService = userDetailsService;
			this.authenticationSuccessHandler = authenticationSuccessHandler;
			this.authenticationFailureHandler = authenticationFailureHandler;
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests()
					.antMatchers("/api/users/**")
					.hasRole("ADMIN")
					.antMatchers("/**")
					.permitAll()
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.successHandler(authenticationSuccessHandler)
					.failureHandler(authenticationFailureHandler)
					.and()
					.csrf()
					.disable();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService);
		}
	}
}
