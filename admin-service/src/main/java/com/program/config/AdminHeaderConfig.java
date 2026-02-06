package com.program.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminHeaderConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                // The API Gateway (AdminOnlyFilter) should have already checked this,
                // but we double-check here for defense-in-depth.
                String role = request.getHeader("X-User-Role");
                
                if (role == null || !"ADMIN".equalsIgnoreCase(role)) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied: Admin role required");
                    return false;
                }
                return true;
            }
        }).addPathPatterns("/admin/**"); // Secure all admin routes
    }
}
