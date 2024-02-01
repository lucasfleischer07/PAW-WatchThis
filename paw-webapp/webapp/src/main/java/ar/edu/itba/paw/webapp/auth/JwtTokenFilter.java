package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;

/**
 * https://github.com/jwtk/jjwt
 * https://github.com/Yoh0xFF/java-spring-security-example
 */
@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserService userService;

    private static final String ACCESS_TOKEN_TYPE = "access-token";
    private static final String REFRESH_TOKEN_TYPE = "refresh-token";
    private static final String REFRESH_TOKEN = "X-Refresh-Token";
    private static final String ACCESS_TOKEN = "Access-Token";



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        //Get JwtToken and UserDetails
        final String token = header.split(" ")[1].trim();
        PawUserDetails userDetails = jwtTokenUtil.parseToken(token);

        // Validate userDetails
        if (userDetails == null || !userDetails.isEnabled() || !userDetails.isAccountNonLocked() || SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        // Create authentication and set it on the spring security context
        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        String tokenType = jwtTokenUtil.getTokenType(token);
        if(tokenType.equals(REFRESH_TOKEN_TYPE)){
            userService.findByEmail(userDetails.getUsername()).ifPresent(user -> {
                response.setHeader(ACCESS_TOKEN, jwtTokenUtil.createToken(user));
                response.setHeader(REFRESH_TOKEN, jwtTokenUtil.createRefreshToken(user));
            });
        }else if(!tokenType.equals(ACCESS_TOKEN_TYPE)){
            chain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}