package com.springfirst.solutions.gym.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(authorize -> {
                    authorize
                            .antMatchers("/", "/img/**", "/login").permitAll()
                            .antMatchers("/trainers/list").permitAll()
                            .antMatchers("/trainers/*/show").permitAll();
                } )
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().and()
                .httpBasic();
    }


    @Override
    @Bean
    protected UserDetailsService userDetailsService() {

        UserDetails userAdmin = User
                .withDefaultPasswordEncoder()
                .username("admin")
                .password("pa55w0rd")
                .roles("ADMIN")
                .build();

        UserDetails userMember = User
                .withDefaultPasswordEncoder()
                .username("userMember")
                .password("pa55w0rd")
                .roles("MEMBER")
                .build();
        UserDetails userTrainer = User
                .withDefaultPasswordEncoder()
                .username("userTrainer")
                .password("pa55w0rd")
                .roles("TRAINER")
                .build();

        return new InMemoryUserDetailsManager(userMember,userAdmin,userTrainer);
    }
}
