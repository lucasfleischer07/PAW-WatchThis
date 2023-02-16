//package ar.edu.itba.paw.webapp.auth;
//
//import ar.edu.itba.paw.models.User;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.util.FileCopyUtils;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.nio.charset.StandardCharsets;
//import java.security.Key;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    private final Key secret;
//    private static final int EXPIRY_TIME = 7 * 24 * 60 * 60 * 1000; //1 week
//
//    @Autowired
//    PawUserDetailsService userService;
//
//    public JwtUtil(Resource secretResource) throws IOException {
//        secret = Keys.hmacShaKeyFor(
//                FileCopyUtils.copyToString(new InputStreamReader(secretResource.getInputStream()))
//                        .getBytes(StandardCharsets.UTF_8)
//        );
//    }
//    public String createToken(User user) {
//        Claims claims = Jwts.claims();
//
//        claims.setSubject(user.getUserName());
//        claims.put("authorization", user.getRole());
//        return "Bearer " + Jwts.builder()
//                .setClaims(claims)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + EXPIRY_TIME))
//                .signWith(secret)
//                .compact();
//    }
//
//    public UserDetails parseToken(String jws) {
//        try {
//            final Claims claims = Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(jws).getBody();
//
//            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
//                return null;
//            }
//
//            final String username = claims.getSubject();
//
//            return userService.loadUserByUsername(username);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}