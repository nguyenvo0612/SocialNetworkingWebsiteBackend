package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .cors(Customizer.withDefaults()) // Kích hoạt CORS
                .authorizeHttpRequests(author -> author
                        .requestMatchers("/", "/login", "/error", "/login/oauth2/code/google").permitAll() // Cho phép các endpoint công khai
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các endpoint còn lại
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:4200/user", true) // Chuyển hướng sau khi login thành công
//                        .failureUrl("http://localhost:4200/login") // Chuyển hướng khi login thất bại
                );
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Cho phép yêu cầu từ frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức HTTP được phép
        configuration.setAllowedHeaders(List.of("*")); // Cho phép mọi headers
        configuration.setAllowCredentials(true); // Cho phép gửi credentials (cookies, authentication headers)

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng CORS cho tất cả các endpoint
        return source;
    }
}

