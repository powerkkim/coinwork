package com.coinwork.base.acommon.security.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@NoArgsConstructor
@Setter
@Getter
public class UserVo {
    private long id = -1L;
    private String userNo = "";
    private String email = "";
    private String passwd = "";
    private String userName = "";
    private String nickName = "";
    private String profileImg = "/static/images/profile_default.png";
    private String telNumber = "";
    private String roles = "";
    private int passfailCnt = 0;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private LocalDateTime passwdresetDate;
}
