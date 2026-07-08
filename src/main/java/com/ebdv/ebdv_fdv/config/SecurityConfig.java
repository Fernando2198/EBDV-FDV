package com.ebdv.ebdv_fdv.config;

import com.ebdv.ebdv_fdv.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Value("${app.security.admin.user}")
    private String admin1User;

    @Value("${app.security.admin.pass}")
    private String admin1Pass;

    @Value("${app.security.maestro.user}")
    private String admin2User;

    @Value("${app.security.maestro.pass}")
    private String admin2Pass;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                        //  Rutas exclusivas del ADMINISTRADOR
                        .requestMatchers("/alumnos/nuevo", "/alumnos/editar/**", "/alumnos/eliminar/**").hasRole("ADMIN")
                        .requestMatchers("/usuarios/**", "/configuracion/**").hasRole("ADMIN")

                        //  Rutas compartidas entre ADMIN y MAESTRO
                        .requestMatchers("/api/asistencia/**").hasAnyRole("ADMIN", "MAESTRO")
                .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                ).headers(headers -> headers
                        .cacheControl(cache->cache.disable()));

        return http.build();
    }

}
