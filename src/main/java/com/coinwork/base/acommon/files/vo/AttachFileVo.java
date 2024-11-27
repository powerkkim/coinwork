package com.coinwork.base.acommon.files.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class AttachFileVo {

    /** 첨부파일의 uniq id  */
    private long id = -1L;

    /* 삭제 여부 */
    private String deleteYn;

    /* 유저 No */
    private String userNo;

    /* 원본  파일 이름*/
    private String fileName;

    /* 파일 확장자 */
    private String fileExt;

    /* 파일 사이즈 */
    private long fileSize;

    /* mime type */
    @JsonIgnore
    private String fileType;

    /* 저장위치 + target파일 이름 */
    @JsonIgnore
    private String fileTargetPath;

    /* target 파일 이름 : uuid + date + 원본파일이름 */
    @JsonIgnore
    private String fileTargetName;

    /* 첨부파일을 어디에서 올린것인지? PAGE, board ...*/
    private String refType;

    /* 첨부파일을 올린형태의 참조 id, PAGE no, board no...*/
    private String refId;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}