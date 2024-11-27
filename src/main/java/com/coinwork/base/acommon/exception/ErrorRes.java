package com.coinwork.base.acommon.exception;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErrorRes {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    // timestamp
    // status
    // error
    // message
    // trace
    // path

    private ErrorRes(String timestamp, int status, String error, String message, String path) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public static ErrorResBuilder builder() {
        return new ErrorResBuilder();
    }


    public static class ErrorResBuilder {
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

        ErrorResBuilder() {
        }

        public ErrorResBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResBuilder status(int status) {
            this.status = status;
            return this;
        }

        public ErrorResBuilder error(String error) {
            this.error = error;
            return this;
        }

        public ErrorResBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResBuilder path(String path) {
            this.path = path;
            return this;
        }


        public ErrorRes build() {
            return new ErrorRes(timestamp, status, error, message, path);
        }

        public String toString() {
            return "ErrorRes.ErrorResBuilder(timestamp=" + this.timestamp + ", status=" + this.status + ", error=" + this.error + ", message=" + this.message + ", path=" + this.path  + ")";
        }
    }
}
