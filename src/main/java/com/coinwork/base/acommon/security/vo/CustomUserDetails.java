package com.coinwork.base.acommon.security.vo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.io.Serial;
import java.time.LocalDateTime;
import java.util.*;

@Setter
@Getter
public class CustomUserDetails implements UserDetails, CredentialsContainer {
    @Serial
    private static final long serialVersionUID = 1000L;

    private Collection<? extends GrantedAuthority> authorities;

    private UserVo user; // User정보

    // SocialUserProfile
    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(UserVo user ) {
        this.user = user;
        this.authorities = getGrantedAuthorities( user.getRoles() ) ;
    }

    // OAuth 로그인
    public CustomUserDetails(UserVo user, Map<String, Object> attributes) {
        this.user = user;
        this.attributes = attributes;
        this.authorities = getGrantedAuthorities( user.getRoles() )  ;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.user.getEmail();
    }

    @Override
    public String getPassword() {
        return this.user.getPasswd();
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {

//        if( user.getPassfailCnt() >= Constants.PASSWORD_FAIL_MAX_COUNT ) {
//            return false;
//        }
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public void eraseCredentials() {
//        this.password = null;
    }

    public LocalDateTime getLastPasswordResetDate() {
        return this.user.getPasswdresetDate();
    }

    public String getUserNo() {
        return this.user.getUserNo();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof CustomUserDetails) {
            return this.user.getEmail().equals(((CustomUserDetails) obj).user.getEmail());
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.user.getEmail().hashCode();
    }

    private List<GrantedAuthority> getGrantedAuthorities(String roles) {
        List<GrantedAuthority> authorities;
        List<String> roleList = null;

        boolean hasRole = false;
        if(  StringUtils.hasText(roles) ) {
            roleList = Arrays.asList(roles.split(","));
            if ( roleList.size() > 0 ) {
                hasRole = true;
            }
        }

        if( hasRole ) {
            authorities = new ArrayList<>( roleList.size() );
            for (String role : roleList) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        }
        else {
            authorities = AuthorityUtils.NO_AUTHORITIES;
        }
        return authorities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(this.user.getEmail()).append(", ");
        sb.append("Password=[PROTECTED], ");
//        sb.append("Enabled=").append(this.enabled).append(", ");
//        sb.append("AccountNonExpired=").append(this.accountNonExpired).append(", ");
//        sb.append("credentialsNonExpired=").append(this.credentialsNonExpired).append(", ");
//        sb.append("AccountNonLocked=").append(this.accountNonLocked).append(", ");
        sb.append("Granted Authorities=").append(this.authorities).append("]");
        return sb.toString();
    }
}
