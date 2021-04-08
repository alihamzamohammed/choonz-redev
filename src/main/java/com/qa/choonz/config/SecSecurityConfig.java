package com.qa.choonz.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecSecurityConfig extends WebSecurityConfigurerAdapter {

    String[] publicResources  =  {
        "/css/**",
        "/img/**",
        "/js/**",
    };

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(publicResources).permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/index*").permitAll()
                .antMatchers("/albums*").permitAll()
                .antMatchers("/artists*").permitAll()
                .antMatchers("/genres*").permitAll()
                .antMatchers("/playlists*").permitAll()
                .antMatchers("/tracks*").permitAll()
                .antMatchers("/index/read*").permitAll()
                .antMatchers("/albums/read*").permitAll()
                .antMatchers("/artists/read*").permitAll()
                .antMatchers("/genres/read*").permitAll()
                .antMatchers("/playlists/read*").permitAll()
                .antMatchers("/tracks/read*").permitAll()
                .antMatchers("/album*").hasRole("USER")
                .antMatchers("/artist*").hasRole("USER")
                .antMatchers("/genre*").hasRole("USER")
                .antMatchers("/playlist*").hasRole("USER")
                .antMatchers("/track*").hasRole("USER")
                .antMatchers("/album/create*").hasRole("USER")
                .antMatchers("/artist/create*").hasRole("USER")
                .antMatchers("/genre/create*").hasRole("USER")
                .antMatchers("/playlist/create*").hasRole("USER")
                .antMatchers("/track/create*").hasRole("USER")
                .antMatchers("/album/update*").hasRole("USER")
                .antMatchers("/artist/update*").hasRole("USER")
                .antMatchers("/genre/update*").hasRole("USER")
                .antMatchers("/playlist/update*").hasRole("USER")
                .antMatchers("/track/update*").hasRole("USER")
                .antMatchers("/album/delete*").hasRole("USER")
                .antMatchers("/artist/delete*").hasRole("USER")
                .antMatchers("/genre/delete*").hasRole("USER")
                .antMatchers("/playlist/delete*").hasRole("USER")
                .antMatchers("/track/delete*").hasRole("USER")
                .antMatchers("/*").permitAll()
                .antMatchers("*").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/index.html")
                .failureUrl("/login.html?error=true")/* .failureHandler(authenticationFailureHandler()) */
                .and().logout()
                .logoutUrl("/perform_logout")
                .logoutSuccessUrl("/login.html?logout=true")
                .deleteCookies("JSESSIONID");
                /*.and().httpBasic()
                .authenticationEntryPoint(authenticationEntryPoint);*/
                /* .logoutSuccessHandler(logoutSuccessHandler()) */

        //         http.addFilterAfter(new CustomFilter(),
        //   BasicAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}