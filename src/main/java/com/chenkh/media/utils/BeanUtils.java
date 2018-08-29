package com.chenkh.media.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

public class BeanUtils {

    public static Map<String, Object> toMap(Object obj) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (obj == null) {
            return map;
        }
        Field fields[] = getFieldWithParaent(obj);
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String fieldName = field.getName();
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getMethodName = "get" + firstLetter + fieldName.substring(1);
            try {
                Method getMethod = obj.getClass().getMethod(getMethodName, new Class[]{});
                if (getMethod != null) {
                    Object fieldValue = getMethod.invoke(obj, new Object[]{});
                    if (fieldValue != null) {
                        map.put(fieldName, fieldValue);
                    }
                }
            } catch (Exception e) {
            }
        }
        return map;
    }

    private static Field[] getFieldWithParaent(Object obj){
        if(obj == null) {
            return new Field[0];
        }
        List<Field> fieldList = new ArrayList<>() ;
        Class tempClass = obj.getClass();
        while (tempClass != null && !tempClass.equals(Object.class)) {//当父类为null的时候说明到达了最上层的父类(Object类).
            fieldList.addAll(Arrays.asList(tempClass.getDeclaredFields()));
            tempClass = tempClass.getSuperclass(); //得到父类,然后赋给自己
        }
        return fieldList.toArray(new Field[fieldList.size()]);

    }
}
