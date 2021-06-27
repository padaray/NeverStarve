package com.NeverStarve.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
        // TODO
//		http
//		
//			.authorizeRequests()
//			.antMatchers(HttpMethod.GET,"/Backstage/**").authenticated()
//			.antMatchers(HttpMethod.GET).permitAll()
//            .antMatchers(HttpMethod.POST, "/Backstage").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .csrf().disable()
//            .formLogin()
//			.loginPage("/Member/login")
//			.loginProcessingUrl("/Member/login")
//			.successForwardUrl("/Backstage/");
    }

}
