package com.coinwork.base.hello.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.model.ReqAuthentication;
import com.coinwork.base.acommon.model.ResAuthenticationInfo;
import com.coinwork.base.acommon.model.ResponseDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class HelloController {

    @RequestMapping("/hello")
    public ResponseEntity<?> hello(HttpServletResponse response) {

        log.info("hello OK");

        Cookie cookie = new Cookie("tttt","ttttttt");
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
        // 30초간 저장
//        cookie.setMaxAge(30*60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
//        response.addHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok("OK");
    }

    /**
     * NullPointException : 500 Error : ExceptionServletAttributes
     * @param reqAuthentication
     * @return
     */
    @PostMapping(value="/exceptiontest")
    public ResponseEntity<?> token(@RequestBody ReqAuthentication reqAuthentication ) {

        log.info( "/login" );
        String useremail = reqAuthentication.getEmail();
        String password = reqAuthentication.getPasswd();

        ResAuthenticationInfo data = null;
//        try {
//            data = authService.login(useremail, password);
//        } catch (Exception e) {
//            e.printStackTrace();
//            throw new BaseException(ErrorType.ERROR_300);
//        }

        log.info( "/responseData : {}", data.toString() );

        ResponseDto res = ResponseDto.builder()
                                .result(MessageCode.OK.name())
                                .message(MessageCode.OK.getMessage())
                                .data(data)
                                .build();

        return ResponseEntity.ok(res);
    }

    /**
     * Exceptin BaseException 발생. APP Exception 수동 발생. : handleBaseException
     * @return
     */
    @PostMapping(value="/testBaseException")
    public ResponseEntity<?> test ( )  {
        log.info( "log test" );
        if( 1 == 1 ) {
            throw new BaseException(MessageCode.ERROR_PAGE_500.getMessage());
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/hello2")
    public ResponseEntity<?> hello2() {
        return ResponseEntity.ok("hello2 ADMIN권한만 접근.");
    }


    @GetMapping("/hello3")
    public ResponseEntity<?> hello3() {
        return ResponseEntity.ok("hello3 USER만 접근 가능");
    }

}
