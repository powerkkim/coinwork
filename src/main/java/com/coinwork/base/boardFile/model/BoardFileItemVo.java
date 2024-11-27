package com.coinwork.base.boardFile.model;


import com.coinwork.base.acommon.files.vo.AttachFileVo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class BoardFileItemVo {
    String no;
    String title;
    String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    Timestamp createDate;

    String createUserName;
    String createUser;

    int likeCnt;

    List<AttachFileVo> attachFiles;
}
