package org.example.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    private static final String[] PUBLIC_MATCHERS = {
            "/webjars/*",
            "/css/*",
            "/js/*",
            "/images/*",
            "/",
            "/user/*",
            "/error/*/*",
            "/console/*",
            "/signup"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/user/", "/account/", "/home").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(PUBLIC_MATCHERS)
                .permitAll().anyRequest().authenticated();
        http
                .csrf().disable().cors().disable()
                .formLogin()
                .failureUrl("/index?error")
                .loginPage("/index")
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/index?logout")
                .permitAll();
        return http.build();
    }

}
