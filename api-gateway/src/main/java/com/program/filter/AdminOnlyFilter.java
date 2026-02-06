package com.program.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AdminOnlyFilter extends AbstractGatewayFilterFactory<Object> {

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            // This header is set by the JwtAuthenticationFilter which MUST run before this
            String role = exchange.getRequest().getHeaders().getFirst("X-User-Role");

            if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
                log.warn("Access denied: User with role {} tried to access Admin endpoint", role);
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            }

            return chain.filter(exchange);
        };
    }
}
