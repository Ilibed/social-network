package com.ilibed;

import com.ilibed.message.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

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

		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
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
                    .logout().logoutSuccessUrl("/#/login")
					.and()
					.csrf()
					.disable();
		}

		@Override
		public void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
		}
	}

	@Configuration
	@EnableWebSocket
	public class WebSocketConfig implements WebSocketConfigurer {

		@Override
		public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
			registry.addHandler(messageHandler(),
					"/message").addInterceptors(new HttpSessionHandshakeInterceptor());
		}

		@Bean
		public WebSocketHandler messageHandler() {
			return new MessageHandler();
		}

		@Bean
		public ServletServerContainerFactoryBean createWebSocketContainer() {
			ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
			container.setMaxTextMessageBufferSize(8192);
			container.setMaxBinaryMessageBufferSize(8192);
			return container;
		}

	}
}
