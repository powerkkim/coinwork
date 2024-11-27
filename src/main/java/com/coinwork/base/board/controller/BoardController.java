package com.coinwork.base.board.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.model.ResponseDto;
import com.coinwork.base.board.model.BoardItemVo;
import com.coinwork.base.board.service.BoardService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
public class BoardController {
    @Autowired
    private BoardService boardService;

    /**
     * C  : 게시판 Item 생성.
     * U  :  게시판 item 하나을 업데이트 한다. ( BoardItemVo.no 가 있는경우. )
     * @return
     */
    @PostMapping(value = {"/board"} )
    public ResponseEntity<?> createItem(@RequestBody BoardItemVo dto) {

        if( boardService.createBoardItem(dto) == 0 ) {
            throw new BaseException("저장이 되지 않았습니다.", HttpStatus.BAD_REQUEST);
        }

        ResponseDto<String> res = new ResponseDto<>();
        res.setResultOK("").setMessage("저장이 완료되었습니다.");
        return ResponseEntity.ok( res );
    }

    /**
     * R  :  게시판 목록을 가져온다.
     *
     * @return
     */
    @GetMapping(value = {"/board"} )
    public ResponseEntity<?> boardlist(@RequestBody(required = false) BoardItemVo searchItem, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<BoardItemVo> dtoPages =  boardService.getBoardList(searchItem, pageable);

        ResponseDto<Page<BoardItemVo>> res = new ResponseDto<>();
        res.setResultOK(dtoPages);
        return ResponseEntity.ok( res );
    }

    /**
     * R  :  게시판 item 하나을 가져온다.
     * @return
     */
    @GetMapping(value = {"/board/{no}"} )
    public ResponseEntity<?> boardItem(@PathVariable String no) {
        Optional<BoardItemVo> resBoardOpt = boardService.getBoardItem(no);
        BoardItemVo resBoard = resBoardOpt.orElseThrow(() -> new BaseException("게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));

        ResponseDto<BoardItemVo> res = new ResponseDto<>();
        res.setResultOK(resBoard);
        return ResponseEntity.ok( res );
    }

    /**
     * U  :  게시판 item 하나을 업데이트 한다.
     * @return
     */
//    @PutMapping(value = "/board/{id}")
//    public  ResponseEntity<?> boardItemUpdate() {
//        BoardItemVo resBoard = BoardItemVo.builder().no("1").title("게시글 제목 1").createDate("2024-01-01").createUser("작성자1").createUser("U01").likeCnt(2).build();
//
//        ResponseDto<BoardItemVo> res = new ResponseDto<>();
//        res.setResultOK(resBoard);
//        return ResponseEntity.ok( res );
//    }

    /**
     * D  :  게시판 item 하나을 삭제한다.
     * @return
     */
//    @DeleteMapping(value = "/board/{id}")
//    public  ResponseEntity<?> boardItemDelete() {
//        BoardItemVo resBoard = BoardItemVo.builder()
//                .no("1")
//                .title("게시글 제목 1")
//                .content("dkdkdkddkdkdkdk")
//                .createDate("2024-01-01")
//                .createUser("작성자1")
//                .createUser("U01")
//                .likeCnt(2)
//                .build();
//
//        ResponseDto<BoardItemVo> res = new ResponseDto<>();
//        res.setResultOK(resBoard);
//        return ResponseEntity.ok( res );
//    }

 

}

