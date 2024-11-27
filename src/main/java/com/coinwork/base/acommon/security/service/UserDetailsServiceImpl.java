package com.coinwork.base.acommon.security.service;


import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.mapper.AuthMapper;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import com.coinwork.base.acommon.security.vo.UserVo;
import com.coinwork.base.acommon.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    AuthMapper userMapper;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

//    public enum UserRoles {ROLE_USER, ROLE_ADMIN, ROLE_MASTER}
//
//    public static final SimpleGrantedAuthority ROLE_ADMIN_AUTH = new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.toString());
//    public static final SimpleGrantedAuthority ROLE_ANONYMOUS_AUTH = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

    @Override
    public CustomUserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {

        log.info( "loadUserByUsername {}" , userId );

        UserVo user = userMapper.findById(userId);

        CustomUserDetails userDetails = Optional.ofNullable( user )
                .map((data)->{
                    log.info( "User 정보 :{}" , user.toString() );
                    return new CustomUserDetails( user );
                })
                .orElseThrow(() -> {
                            log.info("USER NOT FOUND");
                          return  new UsernameNotFoundException("UserNotFound");
                    }
                );

        return userDetails;
    }

    public CustomUserDetails loadUserByUsernameFromAuthentication() throws UsernameNotFoundException {

        String userEmail = jwtTokenUtil.getAuthnticationUserEmail().orElseThrow(()->{
            throw new BaseException("User with ID: ","", HttpStatus.BAD_REQUEST);
        });

        UserVo user = userMapper.findById(userEmail);

        CustomUserDetails userDetails = Optional.ofNullable( user )
                .map((data)->{
                    log.info( "User 정보 :{}" , user.toString() );
                    return new CustomUserDetails( user );
                })
                .orElseThrow(() -> {
                            log.info("USER NOT FOUND");
                            return  new UsernameNotFoundException("UserNotFound");
                        }
                );

        return userDetails;
    }

//    @Transactional
//    public int updateUserLoginFailCnt(String userId) {
//        userMapper.uptLoginFailCnt(userId);
//        return userMapper.getLoginFailCnt(userId);
//    }

//    @Transactional
//    public int updateUserLoginReset(String userId) {
//        return userMapper.uptLoginReset(userId);
//    }
}
