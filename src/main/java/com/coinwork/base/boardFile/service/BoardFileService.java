package com.coinwork.base.boardFile.service;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.files.service.FileUploadService;
import com.coinwork.base.acommon.files.vo.AttachFileVo;
import com.coinwork.base.acommon.security.service.UserDetailsServiceImpl;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import com.coinwork.base.board.model.BoardItemVo;
import com.coinwork.base.acommon.model.RequestList;
import com.coinwork.base.boardFile.mapper.BoardFileMapper;
import com.coinwork.base.boardFile.model.BoardFileItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BoardFileService {

    @Autowired
    private BoardFileMapper boardMapper;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private FileUploadService fileUploadService;

    public int createBoardItem(BoardFileItemVo vo) {

        CustomUserDetails userDetails = userDetailsService.loadUserByUsernameFromAuthentication(  ) ;
        vo.setCreateUser( userDetails.getUser().getUserNo() );
        vo.setCreateUserName( userDetails.getUser().getNickName() );
        int cnt = boardMapper.save(vo);

        return cnt;
    }

    public int createBoardItemWithFile(BoardFileItemVo vo, List<MultipartFile> files) {

        int cnt = 0;
        List<AttachFileVo> attachFilelist = null;
        try {
            CustomUserDetails userDetails = userDetailsService.loadUserByUsernameFromAuthentication(  ) ;
            vo.setCreateUser( userDetails.getUser().getUserNo() );
            vo.setCreateUserName( userDetails.getUser().getNickName() );
            cnt = boardMapper.save(vo);

            log.info(vo.toString());

            List<AttachFileVo> attachFiles = vo.getAttachFiles().stream().filter( d -> d.getDeleteYn().equals("Y") ).toList();

            // file Delete
            if( null != attachFiles && attachFiles.size() > 0 ) {
                int nResultCount = fileUploadService.fileListDelete(BoardItemVo.class.getName(), vo.getNo(), attachFiles);
                log.info(String.valueOf(nResultCount));
            }

            if( null != files && !files.isEmpty() ) {
                attachFilelist = fileUploadService.fileListSave(BoardItemVo.class.getName(), vo.getNo(), files);
            }
            // File data Board Mapping
        } catch (Exception e) {
            e.printStackTrace();
            log.error("####  Exception File ");
            if( attachFilelist.size() > 0 ) {
                for( int nLoopJ =0 ; nLoopJ < attachFilelist.size() ; nLoopJ++ ){
                    AttachFileVo attachFileVo = attachFilelist.get(nLoopJ);
                    String rollbackResult = fileUploadService.fileDelete(attachFileVo.getFileTargetPath());
                    log.error("FILE UPLOAD ERROR FILE_NAME: {} IS ROLLBACK {}",  attachFileVo.getFileName(), rollbackResult);
                }
            }
            throw new BaseException( "파일 업로드중 에러 발생.", MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return cnt;
    }

    public Page<BoardFileItemVo> getBoardList(BoardFileItemVo searchItem, Pageable pageable) {
        RequestList<?> requestList = RequestList.builder()
                .data(searchItem)
                .pageable(pageable)
                .build();

        int totalCount = boardMapper.findAllCount(requestList);
        List<BoardFileItemVo> list = boardMapper.findAll(requestList);

        return new PageImpl<>(
                list,
                pageable,
                totalCount);
    }

    public BoardFileItemVo getBoardItem(String id) {
        BoardFileItemVo boardItemVo = boardMapper.findById(id).orElseThrow(() -> new BaseException("게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST));
        boardItemVo.setAttachFiles( fileUploadService.getAttachFileList(BoardItemVo.class.getName(), boardItemVo.getNo()) );

        return boardItemVo;
    }
}
