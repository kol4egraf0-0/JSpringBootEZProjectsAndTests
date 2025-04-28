package com.thehecklers.aircraftpositions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    @Bean
    UserDetailsService auth(){
        UserDetails ya = User.builder()
                .username("ya")
                .password(passwordEncoder.encode("ppasword"))
                .roles("USER")
                .build();

        UserDetails ti = User.builder()
                .username("ti")
                .password(passwordEncoder.encode("jpasword"))
                .roles("USER", "ADMIN")
                .build();

        System.out.println("ya:    "+ya.getPassword());//для теста а так хуйня никогда не надо
        System.out.println("ti:    "+ti.getPassword());


        return new InMemoryUserDetailsManager(ya, ti);
    }
}
