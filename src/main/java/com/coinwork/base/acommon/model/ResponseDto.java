package com.coinwork.base.acommon.model;


import com.coinwork.base.acommon.constants.MessageCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseDto<T> {
    private String result = "";  // result code
    private String message = "";      //
    private T data;

    public ResponseDto() {
        this.result = MessageCode.OK.toString();
        this.message = MessageCode.OK.getMessage();
        this.data = null;
    }

    public ResponseDto( String result, String message, T data ) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public ResponseDto( MessageCode messageType ) {
        this(messageType, null);
    }

    public ResponseDto( MessageCode messageType, T data ) {
        this(messageType.name(), messageType.getMessage(), data );
    }

    public ResponseDto<T> setResultOK(T data) {
        this.data = data;
        return this;
    }

    public ResponseDto<T> setResultFail(T data) {
        this.result = MessageCode.NOK.toString();
        this.message = MessageCode.NOK.getMessage();
        this.data = data;
        return this;
    }

    public static <T> ResponseDtoBuilder<T> builder() {
        return new ResponseDtoBuilder<T>();
    }

    public static class ResponseDtoBuilder<T> {
        private String result;
        private String message;
        private T data;

        ResponseDtoBuilder() {
        }

        public ResponseDtoBuilder<T> result(String result) {
            this.result = result;
            return this;
        }

        public ResponseDtoBuilder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseDtoBuilder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseDto<T> build() {
            return new ResponseDto<T>(result, message, data);
        }

        public String toString() {
            return "ResponseDto.ResponseDtoBuilder(result=" + this.result + ", message=" + this.message + ", data=" + this.data + ")";
        }
    }
}
