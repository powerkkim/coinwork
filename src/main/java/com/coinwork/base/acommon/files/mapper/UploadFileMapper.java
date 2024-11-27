package com.coinwork.base.acommon.files.mapper;

import com.coinwork.base.acommon.files.vo.AttachFileVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UploadFileMapper {

    int save(AttachFileVo fileVo) throws Exception;

    List<AttachFileVo> findByRef(String refType, String refId);

    int deleteList(String userNo, String refType, String refId, @Param("list")List<AttachFileVo> attachFileVos);
}
