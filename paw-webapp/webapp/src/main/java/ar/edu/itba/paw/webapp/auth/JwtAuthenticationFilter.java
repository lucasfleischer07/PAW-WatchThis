//package ar.edu.itba.paw.webapp.auth;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.ws.rs.core.HttpHeaders;
//import java.io.IOException;
//
//@Component
//public class JwtAuthenticationFilter extends OncePerRequestFilter {
//
//    @Autowired
//    private JwtUtil jwtTokenUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (header == null || !header.startsWith("Bearer ")) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        //Get JwtToken and UserDetails
//        final String token = header.split(" ")[1].trim();
//        UserDetails userDetails = jwtTokenUtil.parseToken(token);
//
//        // Validate userDetails
//        if (userDetails == null || !userDetails.isEnabled() || !userDetails.isAccountNonLocked() || SecurityContextHolder.getContext().getAuthentication() != null) {
//            chain.doFilter(request, response);
//            return;
//        }
//
//        // Create authentication and set it on the spring security context
//        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                userDetails.getUsername(),
//                userDetails.getPassword(),
//                userDetails.getAuthorities()
//        );
//        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        response.setHeader(HttpHeaders.AUTHORIZATION, header);
//        chain.doFilter(request, response);
//    }
//}