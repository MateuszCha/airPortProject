package com.example.airport.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// this is configuration class to testing h2
@Configuration
public class Security extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests().mvcMatchers("/h2").permitAll().and().authorizeRequests().antMatchers("/h2/**").permitAll();

       http.csrf().disable();
       http.headers().frameOptions().disable();
    }

}
