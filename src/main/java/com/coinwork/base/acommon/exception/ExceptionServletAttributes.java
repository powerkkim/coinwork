package com.coinwork.base.acommon.exception;

import com.coinwork.base.acommon.constants.MessageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.WebRequest;

import javax.lang.model.type.ErrorType;
import java.util.Map;

/**
 * 기본 BaseErrorController가 처리중 기본 메시지 처리 수정.
 * SYSTEM Error
 */
@Slf4j
@Component
public class ExceptionServletAttributes extends DefaultErrorAttributes  {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
//        Integer status = (Integer) webRequest.getAttribute(RequestDispatcher.ERROR_STATUS_CODE, RequestAttributes.SCOPE_REQUEST);

        log.info("#### 1 Servlet : "  );

        Map<String, Object> result = super.getErrorAttributes(webRequest,  options);

        log.error("exception {}", result.toString() );
        result.remove("trace");

        if( StringUtils.hasLength( (String)result.get("message") ) ) {
            int status = (int)result.get("status");
            String message;

            // 권한없음.
            if ( status == HttpStatus.FORBIDDEN.value() ) {
                message = MessageCode.ERROR_PAGE_403.getMessage();
            } // 로그인 필요.
            else if ( status == HttpStatus.UNAUTHORIZED.value() ) {
                message = MessageCode.ERROR_PAGE_401.getMessage();
            } // 404 페이지 없음.
            else if( status == HttpStatus.NOT_FOUND.value() ){
                message = MessageCode.ERROR_PAGE_404.getMessage();
            } // 400 Bad Request 잘못된 요청.
            else if( status == HttpStatus.BAD_REQUEST.value() ){
                message = MessageCode.ERROR_PAGE_400.getMessage();
            }
            else {
                message = MessageCode.ERROR_PAGE_500.getMessage();
            }
            result.put("message", message);
        }

        return result;
    }
}