package com.coinwork.base.acommon.security;

import com.coinwork.base.acommon.util.JwtTokenUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
public class ApiTokenFilter extends OncePerRequestFilter {

    @Value("${api-key:''}")
    public String API_KEY;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ApiTokenFilter() {
        log.info("ApiTokenFilter Create");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

            // 기본키가 없는 경우.
            String apiKey = request.getHeader("X-API-KEY");
            if ( !StringUtils.hasLength( apiKey ) || !API_KEY.equals( apiKey ) ) {
                log.info("X-API-KEY NOT FOUND");
                response.sendError(HttpStatus.UNAUTHORIZED.value());
                return;
            }

            String token = jwtTokenUtil.resolveToken(request);

            // 토큰 refresh 요청의 경우  Cookie의 토큰으로 validate 체크 하여 Controller 단으로 진입 가능.
            if( StringUtils.hasLength(token) && request.getRequestURI().equals("/auth/tokenRefresh") ) {
                if( null != request.getCookies() && request.getCookies().length > 0) {
                    token = Arrays.stream(request.getCookies()).filter( c-> c.getName().equals("refreshToken") ).map( c -> c.getValue() ).findFirst().orElse("");
                }
            }

            // Exception이 상위 Filter로 전달되지 않도록 주의. Exception을 받아서 올리면 500 에러 발생함.
            if (jwtTokenUtil.validateToken(token)) {
                Authentication authentication = jwtTokenUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("TOKEN PASS");
            }

            filterChain.doFilter(request, response);
    }

}
