package com.coinwork.base.acommon.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Collection;

@ToString
@Getter
@Setter
public class ResAuthenticationInfo {

    private String accessToken;
    private ResUserVo user;

    public ResAuthenticationInfo( String accessToken, ResUserVo user ) {
        this.user = user;
        this.accessToken = accessToken;
    }

    public static ResAuthenticationInfoBuilder builder() {
        return new ResAuthenticationInfoBuilder();
    }


    public static class ResAuthenticationInfoBuilder {
        private String accessToken;
        private ResUserVo user;

        ResAuthenticationInfoBuilder() {
        }

        public ResAuthenticationInfoBuilder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public ResAuthenticationInfoBuilder user(ResUserVo user) {
            this.user = user;
            return this;
        }

        public ResAuthenticationInfo build() {
            return new ResAuthenticationInfo(accessToken, user);
        }

        public String toString() {
            return "ResAuthenticationInfo.ResAuthenticationInfoBuilder(accessToken=" + this.accessToken + ", user" + user.toString() + ")";
        }
    }
}
