package pe.celucheck.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pe.celucheck.service.UserService;

@Configuration @RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;
    @Bean SecurityFilterChain security(HttpSecurity http) throws Exception {
        http.userDetailsService(userService)
            .authorizeHttpRequests(a -> a
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/favoritos/**", "/api/favorites/**").authenticated()
                .requestMatchers("/", "/catalogo", "/nosotros", "/comparar", "/login", "/registro", "/productos/**", "/click/**", "/api/products/**", "/api/chat", "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .anyRequest().authenticated())
            .formLogin(f -> f.loginPage("/login").usernameParameter("email").defaultSuccessUrl("/", true).failureUrl("/login?error").permitAll())
            .logout(l -> l.logoutSuccessUrl("/").permitAll())
            .csrf(c -> c.ignoringRequestMatchers("/api/chat"));
        return http.build();
    }
}
