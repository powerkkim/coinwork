package com.coinwork.base.acommon.service;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.mapper.AuthMapper;
import com.coinwork.base.acommon.model.ReqUserJoin;
import com.coinwork.base.acommon.model.ResAuthenticationInfo;
import com.coinwork.base.acommon.model.ResUserVo;
import com.coinwork.base.acommon.security.service.UserDetailsServiceImpl;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import com.coinwork.base.acommon.util.JwtTokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private JwtTokenUtil tokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public ResAuthenticationInfo login(HttpServletResponse response, String useremail, String password) {

        CustomUserDetails userDetails = userDetailsService.loadUserByUsername( useremail ) ;
        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            // Error 처리 .  // 비밀번호 오류.
            log.info("user info invalid");
            throw new BaseException( MessageCode.ERROR_LOGIN_001.getMessage() );
        }

        ResUserVo user = new ResUserVo() ;
        user.setUserNo( userDetails.getUser().getUserNo() );
        user.setNickName( userDetails.getUser().getNickName() );
        user.setRoles( userDetails.getUser().getRoles() );
        user.setProfileImg( userDetails.getUser().getProfileImg() );

        String accessToken = tokenUtil.genToken( userDetails );

        ResAuthenticationInfo data = ResAuthenticationInfo.builder()
                                            .accessToken(accessToken)
                                            .user(user)
                                            .build();

        String refreshToken = tokenUtil.genRefreshToken( userDetails );

        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
////         30초간 저장
        cookie.setMaxAge(30*60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

//        ResponseCookie cookie = ResponseCookie.from("accessToken", accessToken)
//                .path("/")
//                .sameSite("None")
//                .httpOnly(false)
//                .secure(true)
//                .maxAge(30)
//                .build();
//        response.addHeader("Set-Cookie", cookie.toString());

        return data;
    }

    public void userJoin(ReqUserJoin joinUser) {
        joinUser.setPasswd(passwordEncoder.encode(joinUser.getPasswd()));

        if( authMapper.isJoinCheck(joinUser) != null ) {
            throw new BaseException( MessageCode.ERROR_LOGIN_USET_EXIST.getMessage() );
        }
        else {
            authMapper.saveUser(joinUser);
        }
    }

    public String logout(String userNo) {
        // 서버 로그아웃 처리 .
        // 로그아웃 정보 저장.
        return "Success";
    }

    public void user(ReqUserJoin joinUser) {

    }

    public ResUserVo userFromToken(String userNo ) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsernameFromAuthentication( ) ;

        if( !userNo.equals(userDetails.getUser().getUserNo() ) ) {
            throw new BaseException(HttpStatus.UNAUTHORIZED.toString(), HttpStatus.UNAUTHORIZED);
        }
        ResUserVo user = new ResUserVo() ;
        user.setUserNo( userDetails.getUser().getUserNo() );
        user.setNickName( userDetails.getUser().getNickName() );
        user.setRoles( userDetails.getUser().getRoles() );
        user.setProfileImg( userDetails.getUser().getProfileImg() );

        return user;
    }

    public ResAuthenticationInfo tokenRefresh(HttpServletResponse response, String refresh) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsernameFromAuthentication(  ) ;

        ResUserVo user = new ResUserVo() ;
        user.setUserNo( userDetails.getUser().getUserNo() );
        user.setNickName( userDetails.getUser().getNickName() );
        user.setRoles( userDetails.getUser().getRoles() );
        user.setProfileImg( userDetails.getUser().getProfileImg() );

        String accessToken = tokenUtil.genToken( userDetails );

        ResAuthenticationInfo data = ResAuthenticationInfo.builder()
                .accessToken(accessToken)
                .user(user)
                .build();

        String refreshToken = tokenUtil.genRefreshToken( userDetails );

        Cookie cookie = new Cookie("refreshToken",refreshToken);
        cookie.setDomain("localhost");
        cookie.setPath("/");
        cookie.setAttribute("SameSite", "None");
////         30초간 저장
        cookie.setMaxAge(30*60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return data;
    }
}
