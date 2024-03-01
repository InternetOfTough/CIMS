package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().antMatchers("/").permitAll();
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/", "/login", "/signup").permitAll()
                        .requestMatchers("/home", "/cctv/**", "/dashboard", "/ws/**").authenticated()
                        .requestMatchers("/memberList", "/delete", "/promote").hasRole("ADMIN")
                )
                .formLogin((formIn) -> formIn
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll()
                )
                .logout((formOut) -> formOut
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .permitAll()
                );
        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("password")
//                .roles("ADMIN", "USER")
//                .build();
//        return new InMemoryUserDetailsManager(user, admin);
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
