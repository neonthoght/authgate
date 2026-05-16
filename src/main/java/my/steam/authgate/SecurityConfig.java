package my.steam.authgate;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Bean; 
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
     
        /*
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // Only users with ROLE_ADMIN
                .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN") // Both roles allowed
                .requestMatchers("/public/**").permitAll() // No login required
                .anyRequest().authenticated() // Everything else needs a login
            ); // Enables default login page
        */

      http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Allow everything
            )
            .csrf(csrf -> csrf.disable())  // Often needed when disabling auth
            .formLogin(form -> form.disable()) // Disable default login page
            .httpBasic(basic -> basic.disable()); // Disable basic auth prompt
        
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
