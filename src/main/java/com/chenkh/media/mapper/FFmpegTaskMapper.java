package com.chenkh.media.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface FFmpegTaskMapper {

    List<Map<String,Object>> getAllTask();
    void addTask(Map map);
    Map getNextTask();
    void updateTask(Map map);
    void delTask(String id);
}
