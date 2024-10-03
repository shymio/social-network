package point.API_Gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class GatewayConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("register", r -> r.path("/api/auth/signup") // Открытый маршрут для регистрации
                        .uri("http://localhost:8081")) // Указываем адрес User Service
                .route("login", r -> r.path("/api/auth/login") // Открытый маршрут для авторизации
                        .uri("http://localhost:8081")) // Указываем адрес User Service
                .route("user-service", r -> r.path("/api/users/**")
                        .filters(f -> f.filter(jwtAuthenticationFilter)) // Применяем JWT фильтр
                        .uri("http://localhost:8081"))
                .build();
    }
}

