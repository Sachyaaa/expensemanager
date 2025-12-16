package com.sachin.expensemanager.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger logger =
            LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String user = (auth != null && auth.isAuthenticated())
                ? auth.getName()
                : "ANONYMOUS";

        logger.info(
                "Incoming request: method={}, uri={}, user={}",
                request.getMethod(),
                request.getRequestURI(),
                user
        );

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
