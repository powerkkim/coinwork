package com.coinwork.base.board.service;


import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.model.RequestList;
import com.coinwork.base.acommon.security.service.UserDetailsServiceImpl;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import com.coinwork.base.board.mapper.BoardMapper;
import com.coinwork.base.board.model.BoardItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public int createBoardItem(BoardItemVo vo) {
        CustomUserDetails userDetails = userDetailsService.loadUserByUsernameFromAuthentication(  ) ;

        if( StringUtils.hasLength(vo.getNo()) ) {
            BoardItemVo reqBoard = boardMapper.findById(vo.getNo()).orElseThrow(() -> new BaseException("게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
            if(  reqBoard.getCreateUser().equals( userDetails.getUser().getUserNo() ) == false ) {
                throw new BaseException("작성자와 다른 사용자 입니다.", HttpStatus.BAD_REQUEST);
            }
        }

        vo.setCreateUser( userDetails.getUser().getUserNo() );
        vo.setCreateUserName( userDetails.getUser().getNickName() );
        int cnt = boardMapper.save(vo);

        return cnt;
    }
 
    public Page<BoardItemVo> getBoardList(BoardItemVo searchItem, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder()
                .data(searchItem)
                .pageable(pageable)
                .build();

        int totalCount = boardMapper.findAllCount(requestList);
        List<BoardItemVo> list = boardMapper.findAll(requestList);

        return new PageImpl<>(
                list,
                pageable,
                totalCount);
    }

    public Optional<BoardItemVo> getBoardItem(String id) {
       return boardMapper.findById(id);
    }


}
