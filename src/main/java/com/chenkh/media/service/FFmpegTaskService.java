package com.chenkh.media.service;

import com.chenkh.media.FFmpeg.FFmpegTaskThread;
import com.chenkh.media.bean.Page;
import com.chenkh.media.mapper.FFmpegTaskMapper;
import com.chenkh.media.utils.MapUtils;
import com.chenkh.media.utils.TimeUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FFmpegTaskService {

    @Resource
    private FFmpegTaskMapper fFmpegTaskMapper;

    public Object getList(Page page) {
        PageHelper.startPage(page.getShowpage(),page.getPageSize());
        PageHelper.orderBy("time desc");
        List<Map<String,Object>> list = fFmpegTaskMapper.getAllTask();
        PageInfo p = new PageInfo<>(list);

        for(Map<String,Object> map : list){
            long time = MapUtils.getlongValue(map,"time",0);
            map.put("time", time == 0 ? "" : TimeUtils.long2string(time));
            long start_time = MapUtils.getlongValue(map,"start_time",0);
            map.put("start_time", start_time == 0 ? "" : TimeUtils.long2string(start_time));
            long end_time = MapUtils.getlongValue(map,"end_time",0);
            map.put("end_time", end_time == 0 ? "" : TimeUtils.long2string(end_time));
        }

        Map<String,Object> re = new HashMap<>();
        re.put("data",list);
        re.put("total",p.getTotal());
        return re;
    }

    public void delTask(String id,String status) {
        if("0".equals(status)){
            FFmpegTaskThread fFmpegTaskThread = FFmpegTaskThread.getInstance();
            try {
                fFmpegTaskThread.removeTask(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fFmpegTaskMapper.delTask(id);
    }
}
