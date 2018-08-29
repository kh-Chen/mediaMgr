package com.chenkh.media.service;

import com.chenkh.media.FFmpeg.FFmpegTask;
import com.chenkh.media.bean.Page;
import com.chenkh.media.bean.PlayactorBean;
import com.chenkh.media.FFmpeg.FFmpegTaskThread;
import com.chenkh.media.mapper.PlayactorMapper;
import com.chenkh.media.utils.MapUtils;
import com.chenkh.media.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;
import org.springframework.util.FileCopyUtils;

import javax.annotation.Resource;
import java.io.*;
import java.util.*;

@Service
public class PlayactorService {
    @Resource
    private PlayactorMapper playactorMapper;

    public Object queryLabelList(Page page) {
        PageHelper.startPage(page.getShowpage(),page.getPageSize());
        List<Map<String,Object>> list = playactorMapper.customQuery(null);
        PageInfo p = new PageInfo<>(list);

        for(Map<String,Object> map : list){
            String face_rate = MapUtils.getString(map,"face_rate","0");
            map.put("face_rate",Float.parseFloat(face_rate));
            String figure_rate = MapUtils.getString(map,"figure_rate","0");
            map.put("figure_rate",Float.parseFloat(figure_rate));
            String vagina_rate = MapUtils.getString(map,"vagina_rate","0");
            map.put("vagina_rate",Float.parseFloat(vagina_rate));
            String breast_rate = MapUtils.getString(map,"breast_rate","0");
            map.put("breast_rate",Float.parseFloat(breast_rate));
        }

        Map<String,Object> re = new HashMap<>();
        re.put("data",list);
        re.put("total",p.getTotal());
        return re;
    }

    public void addPlayactor(PlayactorBean bean) throws IOException {
        byte[] b = null;
        if(!StringUtils.isEmpty(bean.getImg())){
            File f = new File(bean.getImg());
            InputStream is = new FileInputStream(f);
            b = FileCopyUtils.copyToByteArray(is);
        }

        String uuid = UUID.randomUUID().toString();

        Map<String,Object> param = new HashMap<>();
        param.put("id",uuid);
        param.put("name",bean.getName());
        param.put("img",b);
        param.put("type",bean.getType());
        param.put("point",bean.getPoint());
        param.put("face_rate",bean.getFace_rate());
        param.put("figure_rate",bean.getFigure_rate());
        param.put("vagina_rate",bean.getVagina_rate());
        param.put("breast_rate",bean.getBreast_rate());

        playactorMapper.insert(param);

    }

    public void editPlayactor(PlayactorBean bean) throws IOException {

        Map<String,Object> param = new HashMap<>();
        byte[] b = null;
        if(!StringUtils.isEmpty(bean.getImg())){
            File f = new File(bean.getImg());
            InputStream is = new FileInputStream(f);
            b = FileCopyUtils.copyToByteArray(is);
            param.put("img",b);
        }


        param.put("id",bean.getId());
        param.put("name",bean.getName());
        param.put("type",bean.getType());
        param.put("point",bean.getPoint());
        param.put("face_rate",bean.getFace_rate());
        param.put("figure_rate",bean.getFigure_rate());
        param.put("vagina_rate",bean.getVagina_rate());
        param.put("breast_rate",bean.getBreast_rate());

        playactorMapper.update(param);
    }

    public void delPlayactor(String id) {
        playactorMapper.deleteByid(id);
    }

    public void delTempData(){
        File f = new File(ClassUtils.getDefaultClassLoader().getResource("").getPath(),"temp");
        if(!f.exists()){
            return ;
        }
        File[] fs =  f.listFiles();
        for(File a : fs){
            if (!a.isDirectory()){
                a.delete();
            }
        }
    }

    public byte[] getImg(String id) {
        Map<String , Object> map = playactorMapper.getImg(id);
       return map.get("img") == null ? new byte[]{} : (byte[])map.get("img");
    }

}
