package my.steam.authgate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow all requests without login
            )
            .formLogin(form -> form.disable()) // Removes the default login page
            .httpBasic(basic -> basic.disable()) // Removes the browser popup
            .csrf(csrf -> csrf.disable()); // Often needed for APIs to avoid 403s
            
        return http.build();
    }
}
