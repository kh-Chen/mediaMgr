package com.chenkh.media.controller;

import com.chenkh.media.bean.Mov;
import com.chenkh.media.bean.Page;
import com.chenkh.media.framework.RenderMapper;
import com.chenkh.media.service.MovService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Mov")
public class MovController extends RenderMapper {

    private final MovService movService;

    @Autowired
    public MovController(MovService movService) {
        this.movService = movService;
    }

    @RequestMapping("/addMov.json")
    public Object addMov(Mov mov){
        movService.addMov(mov);
        return success();
    }

    @RequestMapping("/getMovList.json")
    public Object getMovList(Page page, Mov mov){
        return movService.getMovList(page , mov);
    }

}
