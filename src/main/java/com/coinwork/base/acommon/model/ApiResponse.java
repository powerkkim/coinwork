package com.coinwork.base.acommon.model;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.ErrorRes;
import lombok.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class ApiResponse {

    public static ResponseEntity<GenericResponseBody> ok() {
        return ok(MessageCode.OK.getMessage());
    }

    public static ResponseEntity<GenericResponseBody> ok(String message ) {
        return ok(message, null);
    }

    public static ResponseEntity<GenericResponseBody> ok(String message, Object data) {
        return ResponseEntity.ok( getOkBody( message, data) );
    }

    public static ResponseEntity<GenericResponseBody> okBody(Object data) {
        return ResponseEntity.ok( getOkBody( MessageCode.OK.getMessage(), data) );
    }

    // Utils
    public static GenericResponseBody getOkBody( String successMessage, Object data) {
        GenericResponseBody body = GenericResponseBody.builder()
                .result( MessageCode.OK.toString() )
                .message( successMessage )
                .data( data )
                .timestamp(LocalDateTime.now())
                .build();

        return body;
    }

    public static ResponseEntity<GenericResponseBody> fail( MessageCode code ) {
     /*   ErrorRes errorRes = ErrorRes.builder()
                .timestamp( new Date().toString() )
                .status( e.getHttpStatus().value() )
                .error( e.getMessagecode() )
                .message( e.getMessage() )
                .path(request.getRequestURI())
                .build();*/

        GenericResponseBody body = GenericResponseBody.builder()
                .result( code.toString() )
                .message( code.getMessage() )
                .data( null )
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok( body );
    }

    // Utils
//    public static GenericResponseBody getErrorBody( String successMessage, Object data) {
//        GenericResponseBody body = GenericResponseBody.builder()
//                .result( MessageCode.NOK.toString() )
//                .message( successMessage )
//                .data( data )
//                .timestamp(LocalDateTime.now())
//                .build();
//
//        return body;
//    }

}


