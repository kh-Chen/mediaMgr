package com.chenkh.media.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String long2string(long time){
      String format = "yyyy-MM-dd HH:mm:ss";
      SimpleDateFormat sdf = new SimpleDateFormat(format);
      return sdf.format(new Date(time*1000));
    }
}
