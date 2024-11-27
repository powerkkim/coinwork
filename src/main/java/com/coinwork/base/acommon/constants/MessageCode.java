package com.coinwork.base.acommon.constants;

public enum MessageCode {

    // common Error
    OK("OK")
    , NOK( "FAIL" )
    , ERROR_TYPE_000( "알수 없는 오류가 발생하였습니다.")
    , ERROR_TYPE_001( "파라미터가 유효하지 않습니다.")
    , ERROR_TYPE_002( "강제에러 발생 .")

    , ERROR_LOGIN_001( "로그인 정보가 올바르지 않습니다.")
    , ERROR_LOGIN_USER_NOTFOUND( "가입자 정보를 찾을 수 없습니다.")
    , ERROR_LOGIN_USET_EXIST( "이미 가입된 정보가 있습니다.")


    , ERROR_PAGE_400( "400 잘못된 요청입니다." )
    , ERROR_PAGE_401( "401 로그인이 필요합니다.")
    , ERROR_PAGE_403( "403 접근 권한이 없습니다.")
    , ERROR_PAGE_404( "404 요청하신 페이지를 찾을 수 없습니다.")
    , ERROR_PAGE_500( "500 서버 처리중 오류가 발생하였습니다." )

    // User Error

    // DB Error
    ,ERROR_DB_DELETE_FAIL( "항목삭제 실패 하였습니다." )
    // File Error

    // FTP 에러
    ,ERROR_FILE_EXCEED( "파일용량이 초과 되었습니다." )
    ,ERROR_FILE_PROCCESSING( "파일처리가 실패 하였습니다." )
    ,ERROR_FILE_DELETE_FAIL( "파일삭제가 실패 하였습니다." )


    // 개시판 Error

    ;

    private String message;

    MessageCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
