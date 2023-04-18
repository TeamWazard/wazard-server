package shop.wazard.util.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import shop.wazard.util.response.ErrorMessage;
import shop.wazard.util.response.StatusEnum;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private static final String ATK_HEADER = "ACCESS-TOKEN";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String jwt = resolveToken(request);
        try {
            if (jwt != null && jwtProvider.validateToken(jwt) == JwtProvider.JwtCode.ACCESS) {
                Authentication authentication = jwtProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("set Authentication to security context for '{}', uri: {}", authentication.getName(), request.getRequestURI());
            }
        } catch (JwtException e) {
            jwtExceptionHandler(response, "토큰이 만료되었습니다.");
        }
        filterChain.doFilter(request, response);
    }

    // Jwt 예외처리
    public void jwtExceptionHandler(HttpServletResponse response, String message) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        try {
            String result = new ObjectMapper()
                    .writeValueAsString(
                            ErrorMessage.builder()
                                    .statusEnum(StatusEnum.EXPIRED_TOKEN)
                                    .errorMessage(message)
                                    .build()
                    );
            response.getWriter().write(result);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    private String resolveToken(HttpServletRequest request) {
        return request.getHeader(ATK_HEADER);
    }

}
