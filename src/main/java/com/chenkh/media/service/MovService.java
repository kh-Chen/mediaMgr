package com.chenkh.media.service;

import com.chenkh.media.FFmpeg.FFmpegTask;
import com.chenkh.media.FFmpeg.FFmpegTaskThread;
import com.chenkh.media.bean.Mov;
import com.chenkh.media.bean.Page;
import com.chenkh.media.mapper.MovMapper;
import com.chenkh.media.utils.BeanUtils;
import com.chenkh.media.utils.MapUtils;
import com.chenkh.media.utils.StringUtils;
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
public class MovService {

    @Resource
    private MovMapper movMapper;

    public void addMov(Mov mov){
        String id = UUID.randomUUID().toString();
        mov.setId(id);
        Map<String,Object> map = BeanUtils.toMap(mov);

        String filepath = mov.getFile_temppath();
        FFmpegTaskThread fFmpegTaskThread = FFmpegTaskThread.getInstance();
        if(fFmpegTaskThread.getState() == Thread.State.NEW){
            System.out.println("start thread...");
            fFmpegTaskThread.start();
        }
        FFmpegTask t = new FFmpegTask(id,filepath,"%mypath%\\store");
        t.setThumbnail(mov.getThumbnail());

        if(!StringUtils.isEmpty(mov.getStartTime())){
            t.getBuilder().setStartTime(mov.getStartTime());
        }

        Map<String,Object> infomap = t.commit();
        map.putAll(infomap);

        movMapper.insert(map);

        System.out.println("add task...");
        fFmpegTaskThread.addTask(t);

    }

    public Object getMovList(Page page, Mov mov) {
        PageHelper.startPage(page.getShowpage(),page.getPageSize());
        PageHelper.orderBy("");
        List<Map<String,Object>> list = movMapper.customQuery(mov);
        PageInfo p = new PageInfo<>(list);

//        for(Map<String,Object> map : list){
//            long time = MapUtils.getlongValue(map,"last_change_time",0);
//            map.put("last_change_time", time == 0 ? "" : TimeUtils.long2string(time));
//        }

        Map<String,Object> re = new HashMap<>();
        re.put("data",list);
        re.put("total",p.getTotal());
        return re;
    }
}
