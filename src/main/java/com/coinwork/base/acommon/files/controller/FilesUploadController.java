package com.coinwork.base.acommon.files.controller;

import com.coinwork.base.acommon.files.service.FileUploadService;
import com.coinwork.base.acommon.files.vo.AttachFileVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Controller
@RequestMapping("/files")
public class FilesUploadController {


    @Autowired
    FileUploadService fileUploadService;

    @GetMapping("/uploadfile")
    public String uploadfile() {
        return "pages/files/uploadfile";
    }


    @PostMapping("/uploadfile")
    public String uploadForm(Model model, @RequestParam("uploadfile_inpage") MultipartFile uploadfile) throws Exception {

        AttachFileVo afv = fileUploadService.fileSave("COMMON", "",uploadfile);
        String imageUrlPath = "/api/files/upload/displayFile";
        model.addAttribute("uploadedImagePath", imageUrlPath + afv.getFileTargetPath());

        return "pages/files/uploadfile";
    }

    @GetMapping("/uploadfileProgress")
    public String uploadfileProgress() {
        return "pages/files/uploadfileProgress";
    }


//    @PostMapping("/uploadfile")
//    public String uploadForm(MultipartFile file) {
//        String fileName = file.getOriginalFilename();
//        File target = new File(uploadPath, fileName);
//
//
//        //경로 생성
//        if ( ! new File(uploadPath).exists()) {
//            new File(uploadPath).mkdirs();
//        }
//        //파일 복사
//        try {
//            FileCopyUtils.copy(file.getBytes(), target);
//            mv.addObject("file", file);
//        } catch(Exception e) {
//            e.printStackTrace();
//            mv.addObject("file", "error");
//        }
//        //View 위치 설정
//        mv.setViewName("post/test_upload.basic");
//        return mv;
//
//        return "pages/files/uploadfile";
//    }
}
