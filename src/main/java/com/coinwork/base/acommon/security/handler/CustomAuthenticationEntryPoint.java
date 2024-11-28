package com.coinwork.base.acommon.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)throws IOException, ServletException {
        // 에러 페이지에 대한 확장자를 현재 호출한 확장자와 마추어준다.
        log.info("CustomAuthenticationEntryPoint ## 1");
        log.info(" CustomAuthenticationEntryPoint Exceiption : {}",authException);
        log.info("CustomAuthenticationEntryPoint #### 2");
        log.info("CustomAuthenticationEntryPoint LocalizedMessage : {}",authException.getLocalizedMessage());
        log.info("CustomAuthenticationEntryPoint #### 3");
        log.info("CustomAuthenticationEntryPoint Message : {}",authException.getMessage());

        log.info("CustomAuthenticationEntryPoint #### 4");
        // authException.getStackTrace();
//        log.info("CustomAuthenticationEntryPoint StackTrace : {}", authException.getStackTrace().toString());

//        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
//        request.setAttribute("errMsg", "로그인이 필요 합니다.");
//        request.getRequestDispatcher("/error/error401").forward(request, response);
        response.sendError(HttpStatus.UNAUTHORIZED.value());
    }

}
