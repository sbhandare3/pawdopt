package com.sbhandare.pawdopt.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .headers()
                    .frameOptions()
                    .disable()
                    .and()
                .authorizeRequests()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .antMatchers("/user/register").permitAll()
                    .antMatchers("/user").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/user/").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/organization**/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/**").authenticated();

    }
}
