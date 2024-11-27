package com.coinwork.base.acommon.files.controller.api;

import com.coinwork.base.acommon.constants.MessageCode;
import com.coinwork.base.acommon.files.service.FileUploadService;
import com.coinwork.base.acommon.files.vo.AttachFileVo;
import com.coinwork.base.acommon.model.ApiResponse;
import com.coinwork.base.acommon.model.GenericResponseBody;
import com.coinwork.base.acommon.model.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/files")
public class ApiFileUploadController {

    @Autowired
    FileUploadService fileUploadService;

    @PostMapping("/upload")
    public ResponseEntity<GenericResponseBody> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) throws Exception {

        AttachFileVo afv = fileUploadService.fileSave("COMMON", "",uploadfile);
        return ApiResponse.ok();
    }

    @PostMapping("/upload2")
    public ResponseEntity<GenericResponseBody> uploadFile2(@RequestParam("uploadfile") MultipartFile uploadfile) throws Exception {

        AttachFileVo afv = fileUploadService.fileSave("COMMON", "",uploadfile);
        return ApiResponse.ok(MessageCode.OK.getMessage(), afv);
    }

    @RequestMapping("/upload/displayFile/{folder}/{fileName}")
    public ResponseEntity<byte[]> displayFile(@PathVariable("folder") String folder, @PathVariable("fileName") String fileName) throws Exception {

        ResponseEntity<byte[]> result = null;

        String currentAppPath = Paths.get("").toAbsolutePath().getParent().toString();
        String saveFolderFullPath = currentAppPath + "/uploadFolder";
        String fileFullPath = saveFolderFullPath + "/" + folder + "/" + fileName;
        File fi = new File(fileFullPath);
        try {
            HttpHeaders header = new HttpHeaders();

            Resource rs = new UrlResource(fi.toURI());
            String mimeType = URLConnection.guessContentTypeFromName(fi.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            header.setContentType(MediaType.parseMediaType(mimeType));
//            header.setCacheControl(CacheControl.maxAge(3600, TimeUnit.SECONDS) );
            header.setCacheControl("no-cache");
            header.setContentLength(rs.contentLength());
            header.setContentDisposition(ContentDisposition.builder("attachment").filename(fileName).build());

//            BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
            InputStream imageStream = new FileInputStream(fileFullPath);
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(imageStream), header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }
}
