package com.coinwork.base.board.mapper;

import com.coinwork.base.board.model.BoardItemVo;
import com.coinwork.base.acommon.model.RequestList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {

    int findAllCount( RequestList<?> requestList);
    List<BoardItemVo> findAll( RequestList<?> requestList);

    Optional<BoardItemVo> findById(@Param("id")String id);

    int save( BoardItemVo vo);
}