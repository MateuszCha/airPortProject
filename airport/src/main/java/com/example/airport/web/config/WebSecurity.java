package com.example.airport.web.config;

import com.example.airport.persistance.service.account.MyUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurity extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsServiceImpl userDetailsService;
    private final LoginAuthenticationEntryPoint entryPoint;

    @Autowired
    public WebSecurity(MyUserDetailsServiceImpl userDetailsService, LoginAuthenticationEntryPoint entryPoint) {
        this.userDetailsService = userDetailsService;
        this.entryPoint = entryPoint;
    }

    @Bean
    public PasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(12);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests().mvcMatchers("/h2").permitAll().and().authorizeRequests().antMatchers("/h2/**").permitAll()
              .and()
              .authorizeRequests().antMatchers("/client","/client/**").hasAnyAuthority("ROLE_ADMIN","ROLE_DEALER")
              .and()
              .authorizeRequests().antMatchers(HttpMethod.GET,"/seat","/seat/**").hasAnyAuthority("ROLE_ADMIN","ROLE_DEALER")
              .and()
              .authorizeRequests().antMatchers("/seat","/seat/**").hasRole("ADMIN")
              .and()
              .authorizeRequests().antMatchers(HttpMethod.GET,"/plane","/plane/**").hasAnyAuthority("ROLE_ADMIN","ROLE_DEALER")
               .and()
              .authorizeRequests().antMatchers("/plane","/plane/**").hasRole("ADMIN")
              .and()
              .authorizeRequests().antMatchers("/booked","/booked/**").hasAnyAuthority("ROLE_ADMIN","ROLE_DEALER")
              .and()
              .authorizeRequests().antMatchers("/schedule", "/schedule/**").hasAnyAuthority("ROLE_ADMIN","ROLE_SCHEDULER")
              .and()
           //   .requiresChannel().antMatchers("/client","/client/**","/plane","/plane/**").requiresSecure()
           //   .and()
              .authorizeRequests().antMatchers("/").permitAll();
       http.httpBasic().authenticationEntryPoint(entryPoint);
       http.formLogin().disable();
       http.logout().permitAll();



       http.csrf().disable();
       http.headers().frameOptions().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    private DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(getEncoder());
        return authenticationProvider;
    }
}
