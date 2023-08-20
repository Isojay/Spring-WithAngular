package com.example.demo.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.customUserDetailService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	customUserDetailService customUserDetailService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http.authorizeHttpRequests(configurer -> 
							configurer								
								.requestMatchers("/","/shop/**","/register","/images/**","/productimages/**").permitAll()
								.requestMatchers("/admin/**").hasRole("ADMIN")								
								.anyRequest().authenticated()
								
				)
			.formLogin(form -> form
					.loginPage("/login")
					.permitAll()
					.failureUrl("/login?error=true")
					.defaultSuccessUrl("/",true)
					
					.usernameParameter("email")
					.passwordParameter("password")
				)
//			.oauth2Login(OAuth -> OAuth
//					.loginPage("/login")
//					.successHandler(GoogleOAuth2SucessHandler))
			.logout(logout->
				logout
					.permitAll()
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
				);
		http.csrf().disable();
		return http.build();
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(customUserDetailService);
	}
	
	public void configure(WebSecurity web) throws Exception {
		  web
		    .ignoring()
		    .requestMatchers("/css/**","/js/**");
		}

}
