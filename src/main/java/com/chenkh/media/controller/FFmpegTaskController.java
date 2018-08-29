package com.chenkh.media.controller;

import com.chenkh.media.bean.Page;
import com.chenkh.media.framework.RenderMapper;
import com.chenkh.media.service.FFmpegTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/FFmpegTask")
public class FFmpegTaskController extends RenderMapper {

    private final FFmpegTaskService fFmpegTaskService;

    @Autowired
    public FFmpegTaskController(FFmpegTaskService fFmpegTaskService) {
        this.fFmpegTaskService = fFmpegTaskService;
    }

    @RequestMapping("/getList.json")
    public Object getList(Page page){
        return fFmpegTaskService.getList(page);
    }

    @RequestMapping("/delTask.json")
    public Object delTask(String id,String status){
        fFmpegTaskService.delTask(id,status);
        return success();
    }
}
