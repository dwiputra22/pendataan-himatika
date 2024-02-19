package com.pendataan.peserta.security;

import com.pendataan.peserta.config.PasswordEncoder;
import com.pendataan.peserta.config.SingleSessionRegistry;
import com.pendataan.peserta.config.auth.AnggotaService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AnggotaService anggotaService;
    private final PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Always create a session
                    .maximumSessions(1) // Allow only one session per user
                    .maxSessionsPreventsLogin(false) // Allow new login while the user is already logged in
                    .sessionRegistry(sessionRegistry()).and()
                .and()
                .authorizeRequests()
                    .antMatchers("/himatika/**").permitAll()
                    .antMatchers("/**").permitAll()
                .anyRequest()
                .authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
        provider.setUserDetailsService(anggotaService);
        return provider;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SingleSessionRegistry();
    }
}
