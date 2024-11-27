package com.coinwork.base.boardFile.controller;


import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.model.ResponseDto;
import com.coinwork.base.board.model.BoardItemVo;
import com.coinwork.base.board.service.BoardService;
import com.coinwork.base.boardFile.model.BoardFileItemVo;
import com.coinwork.base.boardFile.service.BoardFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
public class BoardFileController {

    @Autowired
    private BoardFileService boardService;

    /**
     * C  : 게시판 Item 생성.
     * U  :  게시판 item 하나을 업데이트 한다. ( BoardItemVo.no 가 있는경우. )
     * @return
     */
    @PostMapping(value = "/boardFile", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> handleJsonAndMultipartInput(@RequestPart(value="data", required=false) BoardFileItemVo dto, @RequestPart(value="files", required=false) List<MultipartFile> files) {

        log.info("####file saved ");
        if( boardService.createBoardItemWithFile(dto, files) == 0 ) {
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
    @GetMapping(value = {"/boardFile"} )
    public ResponseEntity<?> boardlist(@RequestBody(required = false) BoardFileItemVo searchItem, @PageableDefault(page = 0, size = 10) Pageable pageable) {
        Page<BoardFileItemVo> dtoPages =  boardService.getBoardList(searchItem, pageable);

        ResponseDto<Page<BoardFileItemVo>> res = new ResponseDto<>();
        res.setResultOK(dtoPages);
        return ResponseEntity.ok( res );
    }

    /**
     * R  :  게시판 item 하나을 가져온다.
     * @return
     */
    @GetMapping(value = {"/boardFile/{no}"} )
    public ResponseEntity<?> boardItem(@PathVariable String no) {
        BoardFileItemVo resBoard = boardService.getBoardItem(no);

        ResponseDto<BoardFileItemVo> res = new ResponseDto<>();
        res.setResultOK(resBoard);
        return ResponseEntity.ok( res );
    }


}
