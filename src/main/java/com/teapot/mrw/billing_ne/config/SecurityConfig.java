package com.teapot.mrw.billing_ne.config;

import com.teapot.mrw.billing_ne.service.ClientDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ClientDetailsService clientDetailsService;

    @Autowired
    public SecurityConfig(ClientDetailsService clientDetailsService) {
        this.clientDetailsService = clientDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/auth/login", "/auth/registration", "/", "/error").permitAll()
            .anyRequest().hasAnyRole("USER", "ADMIN")
            .and()
            .formLogin().loginPage("/auth/login")
            .loginProcessingUrl("/process_login")
            .defaultSuccessUrl("/lk/", true)
            .failureUrl("/auth/login?error")
            .and()
            .logout()
            .logoutUrl("/logout")
            .logoutSuccessUrl("/auth/login");
    }

    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(clientDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
        return new BCryptPasswordEncoder();
    }
}
