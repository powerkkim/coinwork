package com.coinwork.base.acommon.constants;

public class Const {
    public static final String T_AUTHORIZATION = "Authorization";
    public static final String T_BEARER = "Bearer ";

    public static final String	OK	= "OK";
    public static final String	FAIL	= "FAIL";
    public static final String	UPDATE	= "UPDATE";
    public static final String	DELETE = "DELETE";


    /* 시간 설정 timezone  code 값 */
    public static final String TIME_ZONE_SEOUL = "Asia/Seoul";
    public static final String TIMEZONE = "Asia/Seoul";
    public static final String DATE_FORMAT = "yyyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /** 저장장치 단위 */
    public static final long KB = 1024L;
    public static final long MB = 1024L * 1024L;
    public static final long GB = MB * KB;

    public static final int PASSWORD_FAIL_MAX_COUNT = 5;
}
