package com.example.lab12.Config;

import com.example.lab12.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ConfigSecurity {
    private final MyUserDetailsService myUserDetailsService;
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoAuthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/user/register","/api/v1/user/update/{id}").permitAll()
                .requestMatchers("/api/v1/user/get","/api/v1/user/delete/{id}","/api/v1/blog/get-all").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/blog/get-my","/api/v1/blog/update/{id}","/api/v1/blog/add","/api/v1/blog/delete/{id}","/api/v1/blog/get-blog-id/{id}","/api/v1/blog/get-blog-title/{title}").hasAuthority("USER")
                .anyRequest().authenticated()
                .and().logout().logoutUrl("/api/v1/user/logout").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).and().httpBasic();

        return http.build();
    }
}
