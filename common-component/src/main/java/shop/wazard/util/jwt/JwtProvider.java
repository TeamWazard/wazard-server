package shop.wazard.util.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtProvider implements InitializingBean {

    private final UserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final String SECRET_KEY;
    private final Long ATK_LIVE;
    private Key atkKey;

    public JwtProvider(
            UserDetailsService userDetailsService,
            ObjectMapper objectMapper,
            @Value("${spring.jwt.atkKey}") String secret_key,
            @Value("${spring.jwt.live.atk}") Long atk_live) {
        this.userDetailsService = userDetailsService;
        this.objectMapper = objectMapper;
        this.SECRET_KEY = secret_key;
        this.ATK_LIVE = atk_live;
    }

    public static enum JwtCode {
        DENIED,
        ACCESS,
        EXPIRED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String encodedAtkKey = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        atkKey = Keys.hmacShaKeyFor(encodedAtkKey.getBytes());
    }

    public Authentication getAuthentication(String token) {
        Claims claims =
                Jwts.parserBuilder().setSigningKey(atkKey).build().parseClaimsJws(token).getBody();
        UserDetails userDetails = userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(
                userDetails, token, userDetails.getAuthorities());
    }

    public JwtCode validateToken(String token) throws RuntimeException {
        try {
            Jwts.parserBuilder().setSigningKey(atkKey).build().parseClaimsJws(token);
            return JwtCode.ACCESS;
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("토큰이 만료되었습니다.");
        } catch (JwtException | IllegalArgumentException e) {
            log.info("jwtException : {}", e);
        }

        return JwtCode.DENIED;
    }

    public String createAccessToken(Authentication authentication, Long accountId) {
        Date now = new Date();
        Date validity = new Date(now.getTime() + ATK_LIVE);
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim("accountId", accountId)
                .setIssuedAt(now) // 발행시간
                .setExpiration(validity) // 만료
                .signWith(atkKey, SignatureAlgorithm.HS256) // 암호화
                .compact();
    }

    public Long getAccountId(HttpServletRequest request) throws JwtException {
        String token = request.getHeader("ACCESS-TOKEN");
        Jws<Claims> claims =
                Jwts.parserBuilder().setSigningKey(atkKey).build().parseClaimsJws(token);
        return claims.getBody().get("accountId", Long.class); // jwt 에서 userIdx를 추출합니다.
    }
}
