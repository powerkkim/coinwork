package com.coinwork.base.acommon.model;

import com.coinwork.base.acommon.constants.Const;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class GenericResponseBody {
    private String result = "";  // result code
    private String message = "";      //
    private Object data;

    // Timestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Const.DATETIME_FORMAT)
    private LocalDateTime timestamp;
}