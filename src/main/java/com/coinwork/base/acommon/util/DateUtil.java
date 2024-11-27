package com.coinwork.base.acommon.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Slf4j
public class DateUtil {

    private final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 날짜 형식에 따른 Date To String 변환
    public String dateToString(Date date, String timeFormat){
        if(StringUtils.isEmpty(timeFormat)){
            timeFormat = TIME_FORMAT;
        }

        SimpleDateFormat sdformat = new SimpleDateFormat( timeFormat );
        log.info("date: "+date);

        String dateStr = sdformat.format(date);

        return dateStr;
    }

    // 날짜 형식에 따른 String To Date 변환
    public Date stringToDate(String dateStr, String timeFormat) throws ParseException {
        if(StringUtils.isEmpty(timeFormat)){
            timeFormat = TIME_FORMAT;
        }

        SimpleDateFormat sdformat = new SimpleDateFormat( timeFormat );
        Date date = sdformat.parse(dateStr);
        return date;
    }

    public Date dayAdd(Date originDate, int addDay){

        Calendar cal = Calendar.getInstance();
        cal.setTime(originDate);
        cal.add(Calendar.DATE, addDay);

        return cal.getTime();
    }


    public void timeZoneTest() throws ParseException {
        String day = "2015-12-14 15:21:12";

        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        Date date = df.parse(day);

        log.info("Default:"+df.format(date));

        TimeZone tz;
        tz = TimeZone.getTimeZone("Asia/Seoul");
        df.setTimeZone(tz);
        log.info(tz.getDisplayName()+":"+df.format(date));

        tz = TimeZone.getTimeZone("Asia/Shanghai");
        df.setTimeZone(tz);
        log.info(tz.getDisplayName()+":"+df.format(date));

        tz = TimeZone.getTimeZone("America/New_York");
        df.setTimeZone(tz);
        log.info(tz.getDisplayName()+":"+df.format(date));
        log.info(tz.getDisplayName()+":"+df.format(date));

        tz = TimeZone.getTimeZone("GMT");
        df.setTimeZone(tz);
        log.info(tz.getDisplayName()+":"+df.format(date));
        log.info(tz.getDisplayName()+":"+df.format(date));
    }

    public static Date localDateTimeToDate(LocalDateTime ldt){
        if( ldt == null )
            return null;

        return Date.from( ldt .atZone(ZoneId.systemDefault()).toInstant());
    }

}
