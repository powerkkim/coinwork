package com.coinwork.base.acommon.exception;


import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class BaseException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	private HttpStatus httpStatus = HttpStatus.OK;
	private String messagecode = "";

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, HttpStatus httpStatus ) {
		super(message);
		this.httpStatus = httpStatus;
	}

	public BaseException(String message, String messageCode, HttpStatus httpStatus ) {
		super(message);
		this.messagecode = messageCode;
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
}