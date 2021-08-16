package com.springfirst.solutions.gym.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


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
                .httpBasic()
                .and()
                .csrf().disable();
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{bcrypt}$2a$10$e.THGU/v3BUS3lWDI65mjutNx995VzRuJB804QF.1eEQSh8m3iQpG") //admin
                .roles("ADMIN")
                .and()
                .withUser("userMember")
                .password("{bcrypt}$2a$10$WZWeOwLPFMI04JF.OuyCkeFfgwrIb35KHYeX3sBZTusDx5Q8tF5HS") //userMember
                .roles("MEMBER")
                .and()
                .withUser("userTrainer")
                .password("{bcrypt}$2a$10$bqEaZLu5TXGCcK/X4R8H1./o9DsnjVdpivjXhFnoEmEujNplB12qK") //pa55w0rd
                .roles("TRAINER");
    }

}
