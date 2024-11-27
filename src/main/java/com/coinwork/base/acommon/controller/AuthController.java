package com.coinwork.base.acommon.controller;


import com.coinwork.base.acommon.constants.Const;
import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.mapper.CommonMapper;
import com.coinwork.base.acommon.model.*;
import com.coinwork.base.acommon.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequestMapping("/auth")
@RestController
public class AuthController {

    private final AuthService authService;
    private final CommonMapper commonMapper;


    @Autowired
    public AuthController(AuthService authService, CommonMapper commonMapper) {
        this.authService = authService;
        this.commonMapper = commonMapper;
    }

    @PostMapping("/signUp")
    public ResponseEntity<GenericResponseBody> join(@RequestBody ReqUserJoin joinUser) {

        authService.userJoin(joinUser);

        return ApiResponse.ok();
    }

/*
    @PostMapping(value="/token")
    @ResponseBody
    public ResponseEntity<?> token( @Valid @RequestBody ReqAuthentication reqAuthentication, HttpServletResponse response )  {

        String useremail = reqAuthentication.getEmail();
        String password = reqAuthentication.getPasswd();

        ResAuthenticationInfo data = authService.login(response, useremail, password);
        log.info( "/responseData : {}", data.toString() );

        ResponseDto<ResAuthenticationInfo> res = new ResponseDto<>();
        res.setResultOK(data);
        return ResponseEntity.ok(res);
    }
*/

    @PostMapping(value="/token")
    @ResponseBody
    public ResponseEntity<GenericResponseBody> token(@Valid @RequestBody ReqAuthentication reqAuthentication, HttpServletResponse response )  {

        String useremail = reqAuthentication.getEmail();
        String password = reqAuthentication.getPasswd();

        ResAuthenticationInfo data = authService.login(response, useremail, password);
        log.info( "/responseData : {}", data.toString() );

        return ApiResponse.ok("로그인되었습니다.",data);
    }

    @PostMapping(value="/tokenRefresh")
    @ResponseBody
    public ResponseEntity<GenericResponseBody> tokenRefresh( @RequestHeader Map<String, String> headerMap, @CookieValue("refreshToken") String refreshToken, HttpServletResponse response )  {

        String headerValue = headerMap.get( Const.T_AUTHORIZATION.toLowerCase() );

        if( !StringUtils.hasLength(headerValue) || !headerValue.startsWith( Const.T_BEARER ) || !StringUtils.hasLength(refreshToken) ) {
            // 에러.
            throw new BaseException("리프레시토큰생성 실패", HttpStatus.UNAUTHORIZED);
        }

        ResAuthenticationInfo data = authService.tokenRefresh(response, refreshToken);
        return ApiResponse.okBody(data);
    }

    @PostMapping(value="/logout")
    @ResponseBody
    public ResponseEntity<GenericResponseBody> logout( @RequestBody ReqAuthentication reqAuthentication, HttpServletResponse response  )  {

        // Token제거.
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);

        return ApiResponse.okBody( (String)authService.logout( reqAuthentication.getUserNo()) );
    }

    @PostMapping("/user")
    public ResponseEntity<GenericResponseBody> user( @RequestBody Map<String, String> userNoMap ) {
        ResUserVo data = authService.userFromToken((String) userNoMap.get("userNo"));
        return ApiResponse.okBody(data);
    }

    @GetMapping("/mybatisCheck")
    public ResponseEntity<?> mybatisCheck() {

        log.info("########## HELLO TEST ");
        String st = commonMapper.selectTest();
        log.error("$$$$$$$$$$$$ {}" , st);

        return ApiResponse.okBody(st);
    }
}
