package com.coinwork.base.acommon.model;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReqUserJoin {
    private String userNo;
    private String email;
    private String passwd;
    private String userName;
    private String nickName;
    private String profileImg;
    private String telNumber;
    private String roles;
    private int passfailCnt;
    private String passwdresetDate;
}
