package com.coinwork.base.acommon.exception;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.model.ResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * RestController 유입이후 business 로직 처리중 User 지정  Exception만을 처리 해야 Exception 처리가 꼬이지 않음.
 * USER Exception
 */
@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class ExceptionGlobalContollerHandler { 

	@ExceptionHandler(value = BaseException.class)
	public ResponseEntity<ResponseDto<ErrorRes>> handleBaseException(HttpServletRequest request, BaseException e) {
		ErrorRes errorRes = ErrorRes.builder()
							.timestamp( new Date().toString() )
							.status( e.getHttpStatus().value() )
							.error( e.getMessagecode() )
							.message( e.getMessage() )
							.path(request.getRequestURI())
							.build();

		ResponseDto<ErrorRes> res = new ResponseDto<ErrorRes>();
		res.setResultFail(errorRes).setMessage(e.getMessage());

		log.info("handleBaseException");
		return ResponseEntity.ok( res );
	}
}