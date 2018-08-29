package com.chenkh.media.utils;

import java.util.Map;

public class MapUtils {

    public static String getString(Map<String,Object> map,String key,String defaultval){
        Object o = map.get(key);
        try {
            String s = String.valueOf(o);
            if(s == null){
                return defaultval;
            }
            return s;
        }catch (Exception e){
            e.printStackTrace();
            return defaultval;
        }
    }


    public static long getlongValue(Map<String,Object> map,String key,long defaultval){
        Object o = map.get(key);
        try {
            Long l = Long.valueOf(String.valueOf(o));
            if(l == null){
                return defaultval;
            }
            return l.longValue();
        }catch (Exception e){
            e.printStackTrace();
            return defaultval;
        }
    }

    public static int getintValue(Map<String,Object> map,String key,int defaultval){
        Object o = map.get(key);
        try {
            Integer i = Integer.valueOf(String.valueOf(o));
            if(i == null){
                return defaultval;
            }
            return i.intValue();
        }catch (Exception e){
            e.printStackTrace();
            return defaultval;
        }
    }

    public static float getfloatValue(Map<String,Object> map,String key,float defaultval){
        Object o = map.get(key);
        try {
            Float f = Float.valueOf(String.valueOf(o));
            if(f == null){
                return defaultval;
            }
            return f.floatValue();
        }catch (Exception e){
            e.printStackTrace();
            return defaultval;
        }
    }
}
