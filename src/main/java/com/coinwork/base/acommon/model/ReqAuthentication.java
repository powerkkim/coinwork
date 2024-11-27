package com.coinwork.base.acommon.model;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReqAuthentication {
    @NotNull
    private String email;
    @NotNull
    private String passwd;

    private String userNo;

    private String accessToken;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }


}
