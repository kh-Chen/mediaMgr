package com.chenkh.media.controller;

import com.chenkh.media.bean.Mov;
import com.chenkh.media.framework.RenderMapper;
import com.chenkh.media.service.DataMaintainService;
import com.chenkh.media.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/DataMaintain")
public class DataMaintainController extends RenderMapper {

    private final DataMaintainService dataMaintainService;

    @Autowired
    public DataMaintainController(DataMaintainService dataMaintainService) {
        this.dataMaintainService = dataMaintainService;
    }

    public enum FileType{
        folder(Arrays.asList(new String[]{""})),
        video(Arrays.asList(new String[]{"avi","mp4","m3u8","rmvb","mkv","mov","mpg"})),
        picture(Arrays.asList(new String[]{"jpg","jpeg","png"})),
        document(Arrays.asList(new String[]{"txt","xml"})),
        other(Arrays.asList(new String[]{""}));

        List<String> types = null;
        FileType (List<String> type){
            types = type;
        }

        public static FileType setType(String s){
            for(FileType f : FileType.values()){
                if(f.types.contains(s)){
                    return f;
                }
            }
            return other;
        }
    }

    @RequestMapping("/getFileSystemMenu")
    public Object getFileSystemMenu(String fatherPath){
        List<Map<String,Object>> list = new ArrayList<>();
        File[] fs ;
        if(StringUtils.isEmpty(fatherPath)){
            fs = File.listRoots();
        }else{
            fs = new File(fatherPath).listFiles();
        }
        Map<String,Object> map ;
        for(File f : fs){
            map = new HashMap<>();
            map.put("title",StringUtils.isEmpty(f.getName()) ? f.getAbsolutePath() : f.getName());
            map.put("absolutePath",f.getAbsolutePath());

            if(f.isDirectory()){
                map.put("children", Arrays.asList());
                map.put("expand",false);
                map.put("loading",false);
                map.put("type",FileType.folder.name());
            }else{
//                String[] s = f.getName().split("\\.");
                int index = f.getName().lastIndexOf(".");
                String type = index == -1 ? "" : f.getName().substring(index+1);
                if("".equals(type)){
                    map.put("type",FileType.other.name());
                }else{
                    map.put("type",FileType.setType(type).name());
                }
            }

            list.add(map);
        }
        return list;
    }

}
