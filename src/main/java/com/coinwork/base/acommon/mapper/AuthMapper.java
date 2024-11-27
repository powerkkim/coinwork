package com.coinwork.base.acommon.mapper;

import com.coinwork.base.acommon.model.ReqUserJoin;
import com.coinwork.base.acommon.security.vo.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthMapper {

    UserVo findById(@Param("email")String email);


    UserVo isJoinCheck(@Param("user") ReqUserJoin joinUser);

    void saveUser(@Param("user") ReqUserJoin joinUser);

}