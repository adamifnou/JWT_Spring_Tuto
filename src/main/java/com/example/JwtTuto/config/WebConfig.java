package com.example.JwtTuto.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class WebConfig {

//    @Bean
//    public CorsFilter corsFilter() {
//        CorsConfiguration config = new CorsConfiguration();
//
//        // Allow specific origin (your Angular app)
//        config.addAllowedOrigin("http://localhost:4200");
//
//        // Allow credentials (if needed, e.g., for cookies or authentication)
//        config.setAllowCredentials(true);
//
//        // Allow all headers (you can specify more granular control if needed)
//        config.addAllowedHeader("*");
//
//        // Allow specific HTTP methods (e.g., GET, POST, PUT, DELETE, etc.)
//        config.addAllowedMethod("*");
//
//        // Optional: Set the max age for preflight requests cache
//        config.setMaxAge(3600L); // Cache the preflight response for 1 hour
//
//        // Apply this CORS configuration to all paths
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", config);
//
//        return new CorsFilter(source);
//    }
@Bean
public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOrigins("http://localhost:4200")
                    .allowedMethods("*")
                    .allowedHeaders("*")
                    .allowCredentials(true)
                    .maxAge(3600);
        }
    };
}
}