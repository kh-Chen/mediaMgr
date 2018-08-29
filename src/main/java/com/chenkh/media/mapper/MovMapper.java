package com.chenkh.media.mapper;

import com.chenkh.media.bean.Mov;

import java.util.List;
import java.util.Map;

public interface MovMapper {

    void insert(Map map);
    void updataThumbnail(Map map);
    List<Map<String,Object>> customQuery(Mov mov);

}
