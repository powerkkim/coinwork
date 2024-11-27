package com.coinwork.base.acommon.config;


import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
public class CustomCORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO 생성시 처리
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;


        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Max-Age", "3600");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers","Origin,Accept,X-Requested-With,Content-Type,Access-Control-Request-Method,Access-Control-Request-Headers,Authorization,X-API-KEY,withCredentials");
        ((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Credentials", "true");
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        log.info("CustomCORSFilter ===== > |PATH|{}" , request.getRequestURI() );
        if (request.getMethod().equals("OPTIONS")) {
            log.info("CustomCORSFilter <===== OPTION |PATH|{}" , request.getRequestURI() );
            resp.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
        log.info("CustomCORSFilter <===== |PATH|{}" , request.getRequestURI() );
    }

    @Override
    public void destroy() {
        // TODO 해제시 처리.
    }
}