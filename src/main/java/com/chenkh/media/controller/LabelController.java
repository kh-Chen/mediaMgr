package com.chenkh.media.controller;

import com.chenkh.media.bean.Page;
import com.chenkh.media.framework.RenderMapper;
import com.chenkh.media.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/Label")
public class LabelController extends RenderMapper {


    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @RequestMapping("/getLabelList/{type}.json")
    public Object getLabelList(@PathVariable(name = "type") String type, Page page){
        return labelService.queryLabelList(type,page);
    }

    @RequestMapping("/addLabel/{type}.json")
    public Object addLabel(@PathVariable(name = "type") String type,String label_name,String describe){
        labelService.addLabel(type,label_name,describe);
        return success();
    }

    @RequestMapping("/editLabel/{type}.json")
    public Object editLabel(@PathVariable(name = "type") String type,String id,String label_name,String describe){
        labelService.editLabel(type,id,label_name,describe);
        return success();
    }

    @RequestMapping("/delLabel/{type}.json")
    public Object delLabel(@PathVariable(name = "type") String type,String ids){
        labelService.delLabel(type,ids);
        return success();
    }

    @RequestMapping("/getLabelComboData.json")
    public Object getLabelComboData(String type){
        return labelService.getLabelComboData(type);
    }
}
