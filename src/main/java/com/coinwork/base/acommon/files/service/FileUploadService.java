package com.coinwork.base.acommon.files.service;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.files.mapper.UploadFileMapper;
import com.coinwork.base.acommon.files.vo.AttachFileVo;
import com.coinwork.base.acommon.files.util.FileAttachComponent;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import com.coinwork.base.acommon.util.JwtTokenUtil;
import com.coinwork.base.board.model.BoardItemVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class FileUploadService {

    @Autowired
    private FileAttachComponent fileAttachComponent;

    @Autowired
    private UploadFileMapper uploadFileMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Transactional(rollbackFor = Exception.class)
    public AttachFileVo fileSave(String className, String refId, MultipartFile uploadfile) throws Exception {

        AttachFileVo attachFileVo = null;
        attachFileVo = fileAttachComponent.upload(className, refId, uploadfile);

        String userNo = jwtTokenUtil.getAuthnticationUserNo().orElseThrow(()->{
            throw new BaseException("User with ID: ","", HttpStatus.BAD_REQUEST);
        });

//      DB 저장
        try {
            attachFileVo.setUserNo(userNo);
            attachFileVo.setRefType(className);
            attachFileVo.setRefId( refId );

            uploadFileMapper.save(attachFileVo);
            log.info(attachFileVo.getFileName());
        } catch (Exception e) {
//            e.printStackTrace();
            // file upload rollback 파일 제거.
            String rollbackResult = fileAttachComponent.delete(attachFileVo.getFileTargetPath());
            log.error("FILE UPLOAD ERROR FILE_NAME: {} IS ROLLBACK {}",  attachFileVo.getFileName(), rollbackResult);
            throw new BaseException( "파일 업로드중 에러 발생.", MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.INTERNAL_SERVER_ERROR );
        }
        return attachFileVo;
    }

    @Transactional(rollbackFor = Exception.class)
    public List<AttachFileVo> fileListSave(String className, String refId, List<MultipartFile> files) throws Exception {

        List<AttachFileVo> list = new ArrayList<>();
        String userNo = jwtTokenUtil.getAuthnticationUserNo().orElseThrow(()->{
            throw new BaseException("User with ID: ","", HttpStatus.BAD_REQUEST);
        });

        for( int nLoop =0 ; nLoop < files.size() ; nLoop++ ){
            MultipartFile file = files.get(nLoop);

            AttachFileVo attachFileVo = null;
            attachFileVo = fileAttachComponent.upload(className, refId, file);

//      DB 저장
            try {
                attachFileVo.setUserNo(userNo);
                attachFileVo.setRefType(className);
                attachFileVo.setRefId( refId );
                uploadFileMapper.save(attachFileVo);
            } catch (Exception e) {
                // file upload rollback 파일 제거.
                if( list.size() > 0 ) {
                    for( int nLoopJ =0 ; nLoopJ < files.size() ; nLoopJ++ ){
                        String rollbackResult = fileAttachComponent.delete(attachFileVo.getFileTargetPath());
                        log.error("FILE UPLOAD ERROR FILE_NAME: {} IS ROLLBACK {}",  attachFileVo.getFileName(), rollbackResult);
                    }
                }
                else if( null != attachFileVo ) {
                    String rollbackResult = fileAttachComponent.delete(attachFileVo.getFileTargetPath());
                    log.error("FILE UPLOAD ERROR FILE_NAME: {} IS ROLLBACK {}",  attachFileVo.getFileName(), rollbackResult);
                }

                throw new BaseException( "파일 업로드중 에러 발생.", MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.INTERNAL_SERVER_ERROR );
            }

            list.add(attachFileVo);
        }
        return list;
    }


    @Transactional(rollbackFor = Exception.class)
    public int fileListDelete(String refType, String refId, List<AttachFileVo> attachFileVos) throws Exception {
        String userNo = jwtTokenUtil.getAuthnticationUserNo().orElseThrow(()->{
            throw new BaseException("User with ID: ","", HttpStatus.BAD_REQUEST);
        });

       return uploadFileMapper.deleteList(userNo, refType, refId, attachFileVos);
    }

    @Transactional(rollbackFor = Exception.class)
    public String fileDelete(String targetPath) {
        String resultDeleteOk = fileAttachComponent.delete(targetPath);
        return resultDeleteOk;
    }

    public List<AttachFileVo> getAttachFileList(String name, String no) {
        return uploadFileMapper.findByRef(name, no);
    }
}
