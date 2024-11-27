package com.coinwork.base.board.model;

 

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class BoardItemVo {
    String no;
    String title;
    String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    Timestamp createDate;

    String createUserName;
    String createUser;
    int likeCnt;
 

}
