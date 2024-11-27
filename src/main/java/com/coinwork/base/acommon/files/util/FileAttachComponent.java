package com.coinwork.base.acommon.files.util;

import com.coinwork.base.acommon.constants.Const;
import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.exception.BaseException;
import com.coinwork.base.acommon.files.vo.AttachFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
public class FileAttachComponent {

    @Value("${app.file-upload.location:'/uploadFolder'}")
    private String savedefaultfolder;

    @Value("${app.file-upload.image.max-file-size: 10}")
    private int imageMaxFileSize;

    public AttachFileVo upload(String className, String refId, MultipartFile mfile) throws RuntimeException {

        // 파일 유효성 체크
        String retVc = validCheck(mfile);
        if (!retVc.equals(MessageCode.OK.toString())) {
            throw new BaseException(retVc, MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.OK);
        }

        AttachFileVo afvo = new AttachFileVo();
        String currentAppPath = Paths.get("").toAbsolutePath().getParent().toString() + savedefaultfolder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now(ZoneId.of(Const.TIME_ZONE_SEOUL));
        String dateFolderStr = now.format(formatter);

        String saveFolderFullPath = currentAppPath + "/" + dateFolderStr;
//        추가 폴더경로 추가  내부 ...

        log.info("saveFolderFullPath : {}", saveFolderFullPath);
        // 파일명 생성 저장
        String targetFile = null;
        try {
            targetFile = uploadAttachFile(mfile, saveFolderFullPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(retVc, MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.OK);
        }

        // 파일 정보 설정
        afvo.setFileName(mfile.getOriginalFilename());
        afvo.setFileExt(getExtension(mfile.getOriginalFilename()));
        afvo.setFileSize(mfile.getSize());
        afvo.setFileType(mfile.getContentType());
        afvo.setFileTargetName(targetFile);
        afvo.setFileTargetPath("/" + dateFolderStr + "/" + targetFile);
        afvo.setRefType(className);
        afvo.setRefId(refId);

        return afvo;
    }


    public AttachFileVo listUpload(String className, String refId, MultipartFile mfile) throws RuntimeException {

        // 파일 유효성 체크
        String retVc = validCheck(mfile);
        if (!retVc.equals(MessageCode.OK.toString())) {
            throw new BaseException(retVc, MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.OK);
        }

        AttachFileVo afvo = new AttachFileVo();
        String currentAppPath = Paths.get("").toAbsolutePath().getParent().toString() + savedefaultfolder;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate now = LocalDate.now(ZoneId.of(Const.TIME_ZONE_SEOUL));
        String dateFolderStr = now.format(formatter);

        String saveFolderFullPath = currentAppPath + "/" + dateFolderStr;
//        추가 폴더경로 추가  내부 ...

        log.info("saveFolderFullPath : {}", saveFolderFullPath);
        // 파일명 생성 저장
        String targetFile = null;
        try {
            targetFile = uploadAttachFile(mfile, saveFolderFullPath);
        } catch (IOException e) {
            e.printStackTrace();
            throw new BaseException(retVc, MessageCode.ERROR_FILE_PROCCESSING.toString(), HttpStatus.OK);
        }

        // 파일 정보 설정
        afvo.setFileName(mfile.getOriginalFilename());
        afvo.setFileExt(getExtension(mfile.getOriginalFilename()));
        afvo.setFileSize(mfile.getSize());
        afvo.setFileType(mfile.getContentType());
        afvo.setFileTargetName(targetFile);
        afvo.setFileTargetPath("/" + dateFolderStr + "/" + targetFile);
        afvo.setRefType(className);
        afvo.setRefId(refId);

        return afvo;
    }

    /**
     * validate 체크
     * - upload 용량체크
     * - ifvo 객체 null 체크
     *
     * @return
     * @throws Exception
     */
    public String validCheck(MultipartFile mfile) {
        long maxFileSize = imageMaxFileSize;
        maxFileSize *= Const.MB;
        if (maxFileSize < mfile.getSize()) {
            return MessageCode.ERROR_FILE_EXCEED.toString();
        }

        return MessageCode.OK.toString();
    }

    /**
     * upload된 파일을 filesystem에 저장한다.
     *
     * @param mfile
     * @param location
     * @return
     * @throws Exception
     */
    static String uploadAttachFile(MultipartFile mfile, String location) throws RuntimeException, IOException {

        if (!new File(location).exists()) {
            new File(location).mkdirs();
        }

        String targetFile = generateFileId(mfile.getOriginalFilename());
        Path path = Paths.get(location + "/" + targetFile);
        // 저장
        mfile.transferTo(path);
// FileCopy 로도 복사 가능.
//        FileCopyUtils.copy(mfile.getBytes(), f);

        return targetFile;
    }

    /**
     * upload된 파일을 filesystem에 저장한다.
     *
     * @param mfile
     * @param location
     * @return
     * @throws Exception
     */
    static String uploadStreamAttachFile(MultipartFile mfile, String location) throws RuntimeException, IOException {

        if (!new File(location).exists()) {
            new File(location).mkdirs();
        }

        String targetFile = generateFileId(mfile.getOriginalFilename());
        Path path = Paths.get(location + "/" + targetFile);
        File saveFile = path.toFile();
        // 저장
        InputStream fileStream = mfile.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(saveFile), 10);

        FileCopyUtils.copy(fileStream, bos);
        return targetFile;
    }

    /**
     * Uniq한 Id 생성
     *
     * @return
     * @throws Exception
     */
    static String generateFileId(String fileName) {
//        String uuid = UUID.randomUUID().toString();
//        String strDate = DateTimeFormatter.ofPattern("yyyy-mm-dd-HH-mm-ss").format( LocalDateTime.now(ZoneId.of(Const.TIME_ZONE_SEOUL)) );
        Long datetime = System.currentTimeMillis();
        return new StringBuilder().append(datetime).append("_").append(fileName).toString();
    }

    /**
     * 확장자를 추출한다.
     *
     * @param fileName
     * @return
     * @throws Exception
     */
    static String getExtension(String fileName) {
        int index = fileName.lastIndexOf(".");
        if (index == -1 || index == (fileName.length() - 1)) {
            return "";
        }

        String ext = fileName.substring(index + 1);

        return ext;
    }

    public String delete(String targetPath) throws RuntimeException {

        String currentAppPath = Paths.get("").toAbsolutePath().getParent().toString() + savedefaultfolder;
        log.info("currentAppPath : {}", currentAppPath);

        String targetFullPath = currentAppPath + targetPath;
        File file = new File(targetFullPath);

        if (file.exists()) {
            if (file.delete()) {
                return MessageCode.OK.toString();
            } else {
                return MessageCode.ERROR_FILE_DELETE_FAIL.toString();
            }
        }

        // File 존재 하지 않는 경우.
        return MessageCode.OK.toString();
    }

}
