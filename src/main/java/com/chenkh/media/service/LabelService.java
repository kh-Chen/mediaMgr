package com.chenkh.media.service;

import com.chenkh.media.bean.Page;
import com.chenkh.media.mapper.LabelMapper;
import com.chenkh.media.utils.MapUtils;
import com.chenkh.media.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class LabelService {
    @Resource
    private LabelMapper labelMapper;

    public Map<String,Object> queryLabelList(String type,Page page){
        Map<String,Object> param = new HashMap<>();
        param.put("type",type);
        PageHelper.startPage(page.getShowpage(),page.getPageSize());
        PageHelper.orderBy("li.last_change_time desc");
        List<Map<String,Object>> list = labelMapper.customQuery(param);
        PageInfo p = new PageInfo<>(list);

        for(Map<String,Object> map : list){
            long time = MapUtils.getlongValue(map,"last_change_time",0);
            map.put("last_change_time", time == 0 ? "" : TimeUtils.long2string(time));
        }

        Map<String,Object> re = new HashMap<>();
        re.put("data",list);
        re.put("total",p.getTotal());
        return re;
    }

    public void addLabel(String type,String label_name,String describe){
        String uuid = UUID.randomUUID().toString();
        long time = System.currentTimeMillis();
        Map<String,Object> param = new HashMap<>();
        param.put("id",uuid);
        param.put("label_name",label_name);
        param.put("describe",describe);
        param.put("last_change_time",time/1000);
        param.put("type",type);
        labelMapper.insert(param);
    }

    public void editLabel(String type,String id, String label_name, String describe) {
        long time = System.currentTimeMillis();

        Map<String,Object> param = new HashMap<>();
        param.put("id",id);
        param.put("label_name",label_name);
        param.put("describe",describe);
        param.put("last_change_time",time/1000);
        labelMapper.update(param);
    }

    public void delLabel(String type,String ids) {
        labelMapper.deleteByid(ids.split(","));
    }

    public Object getLabelComboData(String type) {
        Map<String,Object> param = new HashMap<>();
        param.put("type",type);
        return labelMapper.queryAllForCombo(param);
    }
}
