package point.API_Gateway.config;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import point.API_Gateway.service.JwtService;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GatewayFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtService jwtService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            logger.warn("Authorization header is missing or doesn't start with Bearer.");
            return chain.filter(exchange);
        }

        String jwt = authHeader.substring(7);
        logger.info("Extracted JWT: {}", jwt);

        try {
            if (jwtService.isTokenValid(jwt)) {
                String username = jwtService.extractUsername(jwt);
                logger.info("User {} authenticated, proceeding with the request", username);

                // Добавляем информацию о пользователе в заголовки
                exchange.getRequest().mutate()
                        .header("X-Authenticated-User", username)
                        .build();
            } else {
                logger.info("Token is invalid");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

        } catch (Exception e) {
            logger.error("Error occurred during token validation: {}", e.getMessage(), e);
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }
}
