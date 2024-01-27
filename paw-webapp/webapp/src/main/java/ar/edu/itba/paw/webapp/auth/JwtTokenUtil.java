package ar.edu.itba.paw.webapp.auth;

import ar.edu.itba.paw.models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * https://github.com/jwtk/jjwt
 * https://github.com/Yoh0xFF/java-spring-security-example
 */
@Component
public class JwtTokenUtil {

    @Autowired
    PawUserDetailsService userDetailsService;

    private static final int ACCESS_EXPIRY_TIME = 24 * 60 * 60 * 1000; //1 day (in millis)
    private static final int REFRESH_EXPIRY_TIME = 7 * 24 * 60 * 60 * 1000; //1 week (in millis)
    private static final String ACCESS_TOKEN_TYPE = "access-token";
    private static final String REFRESH_TOKEN_TYPE = "refresh-token";

    private final Key jwtKey;

    public JwtTokenUtil(Resource jwtKeyResource) throws IOException {
        this.jwtKey = Keys.hmacShaKeyFor(
                FileCopyUtils.copyToString(new InputStreamReader(jwtKeyResource.getInputStream()))
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    /**
     * jws: Json Web Signature (https://datatracker.ietf.org/doc/html/rfc7515)
     */
    public PawUserDetails parseToken(String jws) {
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(jws).getBody();

            if (new Date(System.currentTimeMillis()).after(claims.getExpiration())) {
                return null;
            }

            final String username = claims.getSubject();
            return userDetailsService.loadUserByUsername(username);
        } catch (Exception e) {
            return null;
        }
    }

    public String getTokenType(String jws){
        try {
            final Claims claims = Jwts.parserBuilder().setSigningKey(jwtKey).build().parseClaimsJws(jws).getBody();
            Object type = claims.get("type");
            return (String)type;
        } catch (Exception e) {
            return null;
        }
    }

    public String createToken(User user) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getEmail());
        claims.put("authorization", user.getRole());
        claims.put("name",user.getUserName());
        claims.put("id",user.getId());
        claims.put("type", ACCESS_TOKEN_TYPE);
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRY_TIME))
                .signWith(jwtKey)
                .compact();
    }

    public String createRefreshToken(User user) {
        Claims claims = Jwts.claims();

        claims.setSubject(user.getEmail());
        claims.put("authorization", user.getRole());
        claims.put("name",user.getUserName());
        claims.put("id",user.getId());
        claims.put("type", REFRESH_TOKEN_TYPE);
        return "Bearer " + Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRY_TIME))
                .signWith(jwtKey)
                .compact();
    }
}
