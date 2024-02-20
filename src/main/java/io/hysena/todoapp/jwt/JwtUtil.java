package io.hysena.todoapp.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j(topic = "JwtUtil")
public class JwtUtil {
    // Header Key 값
    public static final String AUTHORIZATION_HEADER = "Authorization";

    // Token Identifier
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret.key}") //Base64 encode 한 SecretKey
    private String secretKey;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    private Key key;

    @PostConstruct
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)){
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token); // 유효하지 않으면 여기서 예외 발생
            return true;
        }catch(SecurityException | MalformedJwtException e){
            log.error(e.getMessage());
        } catch (ExpiredJwtException e){
            log.error(e.getMessage());
        } catch (UnsupportedJwtException e){
            log.error(e.getMessage());
        } catch (IllegalArgumentException e){
            log.error(e.getMessage());
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
    public String createToken(String username) {
        Date date = new Date();

        // 토큰 만료시간 60분
        long TOKEN_TIME = 60 * 60 * 1000;
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username)
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME))
                        .setIssuedAt(date)
                        .signWith(key, signatureAlgorithm)
                        .compact();
    }
}
