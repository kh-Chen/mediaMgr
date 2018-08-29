package com.chenkh.media.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LabelMapper {
    List<Map<String,Object>> customQuery(Map map);
    List<Map<String,String>> queryAllForCombo(Map map);
    void insert(Map map);
    void update(Map map);
    void deleteByid(String[] ids);
}
