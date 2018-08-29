package com.chenkh.media.framework;

import java.util.HashMap;
import java.util.Map;

public class RenderMapper {
    public Map<String,Object> success(Object o){
        Map<String,Object> map = new HashMap<>();
        map.put("status","succ");
        map.put("data",o);
        return map;
    }
    public Map<String,Object> success(){
        Map<String,Object> map = new HashMap<>();
        map.put("status","succ");
        map.put("data","");
        return map;
    }
}
