package shop.wazard.util.aop;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import shop.wazard.util.jwt.JwtProvider;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
@AllArgsConstructor
class AspectConfig {

    private final JwtProvider jwtProvider;

    @Before("@annotation(shop.wazard.util.aop.Certification) && args(accountId,..)")
    public void certificateWithTokenAndAccountId(Long accountId) throws IllegalStateException {
        log.info("============== accountID 회원 본인인증 요청 ==============");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        if (!accountId.equals(jwtProvider.getAccountId(request))) {
            log.info("============== 본인인증 실패 ==============");
            throw new IllegalArgumentException("본인인증 실패");
        }
    }

}
