package com.qa.choonz.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    String[] protectedWebpages = { "/user", "/album", "/artist", "/genre", "/playlist", "/track" };
    String[] protectedCreateEndpoints = { "/album/create", "/artist/create", "/genre/create", "/playlist/create",
            "/track/create" };
    String[] protectedUpdateEndpoints = { "/album/update", "/artist/update", "/genre/update", "/playlist/update",
            "/track/update" };
    String[] protectedDeleteEndpoints = { "/album/delete", "/artist/delete", "/genre/delete", "/playlist/delete",
            "/track/delete" };

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {

        http.csrf().disable().headers().disable().anonymous().and().authorizeRequests()
                // Secured pages
                .mvcMatchers(protectedWebpages).hasAuthority("USER").mvcMatchers(protectedCreateEndpoints)
                .hasAuthority("USER").mvcMatchers(protectedUpdateEndpoints).hasAuthority("USER")
                .mvcMatchers(protectedDeleteEndpoints).hasAuthority("USER")
                // Allow all pages that aren't secured
                .antMatchers("/*", "/**", "*", "**").permitAll()

                .anyRequest().authenticated().and().formLogin().loginPage("/login.html")
                .loginProcessingUrl("/perform_login").defaultSuccessUrl("/index.html", true)
                .failureUrl("/login.html?error=true").and().logout().logoutUrl("/perform_logout")
                .logoutSuccessUrl("/login.html?logout=true").deleteCookies("JSESSIONID");
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

}