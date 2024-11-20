package backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
                        .requestMatchers("/", "/login", "/error", "/login/oauth2/code/google","/logout").permitAll() // Cho phép các endpoint công khai
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các endpoint còn lại
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("http://localhost:4200/user", true) // Chuyển hướng sau khi login thành công
//                        .failureUrl("http://localhost:4200/login") // Chuyển hướng khi login thất bại,

                ).logout(logout -> logout
                        .logoutUrl("http://localhost:4200/logout")
                       .logoutSuccessUrl("http://localhost:4200/login"))
                       .logout(logout -> logout
                       .invalidateHttpSession(true) // Xóa phiên
                       .clearAuthentication(true)
                       .deleteCookies("JSESSIONID")); // Xóa cookie.
                       
        return http.build();
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200")); // Cho phép yêu cầu từ frontend
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Các phương thức HTTP được phép
        configuration.setAllowedHeaders(List.of("*")); // Cho phép tất cả các header
        configuration.setAllowCredentials(true); // Cho phép gửi thông tin xác thực (cookies, headers xác thực)

        // Đảm bảo rằng CORS được áp dụng cho tất cả các endpoint
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Áp dụng CORS cho tất cả các endpoint
        return source;
    }



}

