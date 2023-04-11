package ar.edu.itba.paw.webapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class UnconditionalCacheFilter extends OncePerRequestFilter {
    private static final String MAX_TIME = String.valueOf(TimeUnit.DAYS.toSeconds(2));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("GET")) {
            response.setHeader("Cache-Control", String.format("public, max-age=%d, inmutable", MAX_TIME));
        }
        filterChain.doFilter(request, response);
    }
}