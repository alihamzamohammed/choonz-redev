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

    String[] publicResources = { "/css/**", "/img/**", "/js/**", };

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().headers().disable().authorizeRequests().antMatchers(publicResources).permitAll()
                .antMatchers("/h2*", "/h2**").permitAll().antMatchers("/login*").permitAll().antMatchers("/signup*")
                .permitAll().antMatchers("/check*").permitAll().antMatchers("/index*").permitAll()
                .antMatchers("/albums*").permitAll().antMatchers("/artists*").permitAll().antMatchers("/genres*")
                .permitAll().antMatchers("/playlists*").permitAll().antMatchers("/tracks*").permitAll()
                .antMatchers("/index/read*").permitAll().antMatchers("/albums/read*").permitAll()
                .antMatchers("/artists/read*").permitAll().antMatchers("/genres/read*").permitAll()
                .antMatchers("/playlists/read*").permitAll().antMatchers("/tracks/read*").permitAll()
                .antMatchers("/album*").hasAuthority("USER").antMatchers("/artist*").hasAuthority("USER")
                .antMatchers("/genre*").hasAuthority("USER").antMatchers("/playlist*").hasAuthority("USER")
                .antMatchers("/track*").hasAuthority("USER").antMatchers("/album/create*").hasAuthority("USER")
                .antMatchers("/artist/create*").hasAuthority("USER").antMatchers("/genre/create*").hasAuthority("USER")
                .antMatchers("/playlist/create*").hasAuthority("USER").antMatchers("/track/create*")
                .hasAuthority("USER").antMatchers("/album/update*").hasAuthority("USER").antMatchers("/artist/update*")
                .hasAuthority("USER").antMatchers("/genre/update*").hasAuthority("USER")
                .antMatchers("/playlist/update*").hasAuthority("USER").antMatchers("/track/update*")
                .hasAuthority("USER").antMatchers("/album/delete*").hasAuthority("USER").antMatchers("/artist/delete*")
                .hasAuthority("USER").antMatchers("/genre/delete*").hasAuthority("USER")
                .antMatchers("/playlist/delete*").hasAuthority("USER").antMatchers("/track/delete*")
                .hasAuthority("USER").antMatchers("/*").permitAll().antMatchers("*").permitAll().anyRequest()
                .authenticated().and().formLogin().loginPage("/login.html").loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/index.html").failureUrl("/login.html?error=true").and().logout()
                .logoutUrl("/perform_logout").logoutSuccessUrl("/login.html?logout=true").deleteCookies("JSESSIONID");
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