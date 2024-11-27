package com.coinwork.base.acommon.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ade) throws IOException, ServletException {
        // TODO Auto-generated method stub
        log.info("Exceiption : {}",ade);
        log.info("LocalizedMessage : {}",ade.getLocalizedMessage());
        log.info("Message : {}",ade.getMessage());
        log.info("StackTrace : {}", ade.getStackTrace().toString());

//        response.setStatus( HttpStatus.FORBIDDEN.value() );
//        request.setAttribute("errMsg", "접근권한 없는 사용자 입니다.");
//        request.getRequestDispatcher("/error/403").forward(request, response);

        response.sendError(HttpStatus.FORBIDDEN.value());
    }
}
