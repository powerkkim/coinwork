package com.coinwork.base.boardFile.mapper;

import com.coinwork.base.acommon.model.RequestList;
import com.coinwork.base.boardFile.model.BoardFileItemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardFileMapper {

    int findAllCount( RequestList<?> requestList);
    List<BoardFileItemVo> findAll(RequestList<?> requestList);

    Optional<BoardFileItemVo> findById(@Param("id")String id);

    int save(BoardFileItemVo vo);

}
