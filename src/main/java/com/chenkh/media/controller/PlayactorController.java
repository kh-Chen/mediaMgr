package com.chenkh.media.controller;

import com.chenkh.media.bean.Page;
import com.chenkh.media.bean.PlayactorBean;
import com.chenkh.media.framework.RenderMapper;
import com.chenkh.media.service.PlayactorService;
import com.chenkh.media.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/Playactor")
//@PropertySource(value = "classpath:/config/my.properties", ignoreResourceNotFound = true)
public class PlayactorController extends RenderMapper {

    private String path = ClassUtils.getDefaultClassLoader().getResource("").getPath();

    private final PlayactorService playactorService;

    @Autowired
    public PlayactorController(PlayactorService playactorService) {
        this.playactorService = playactorService;
    }

    @RequestMapping("/getPlayactorList.json")
    public Object getPlayactorList(Page page){
        return playactorService.queryLabelList(page);
    }

    @RequestMapping("/addPlayactor.json")
    public Object addPlayactor(PlayactorBean bean) throws IOException {
        String filename = bean.getImg();
        if(!StringUtils.isEmpty(filename)){
            String filepath = new File(path,"temp"+File.separatorChar+filename).getAbsolutePath();
            bean.setImg(filepath);
        }

        playactorService.addPlayactor(bean);
        playactorService.delTempData();
        return success();
    }

    @RequestMapping("/editPlayactor.json")
    public Object editPlayactor(PlayactorBean bean) throws IOException {
        String filename = bean.getImg();
        if(!StringUtils.isEmpty(filename)){
            String filepath = new File(path,"temp"+File.separatorChar+filename).getAbsolutePath();
            bean.setImg(filepath);
        }

        playactorService.editPlayactor(bean);
        playactorService.delTempData();
        return success();
    }

    @RequestMapping("/delPlayactor.json")
    public Object delPlayactor(String id) {
        playactorService.delPlayactor(id);
        return success();
    }

    @RequestMapping("/getImg.json")
    public Object getImg(HttpServletResponse response, String id){

        byte[] bb = playactorService.getImg(id);
        InputStream inputStream = new ByteArrayInputStream(bb);
        OutputStream os = null;
        try {
            response.setContentType("image/jpeg");
            response.addHeader("Content-Type", "text/html; charset=utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + id );
            os = response.getOutputStream();

            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            // 这里主要关闭。
            try {
                if( os != null ) os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/getTempIcon.json")
    public Object getTempIcon(HttpServletResponse response, String filename){

        OutputStream os = null;
        InputStream inputStream = null;
        try {
            response.setContentType("image/jpeg");
            response.addHeader("Content-Type", "text/html; charset=utf-8");
            filename = new String(filename.getBytes("gbk"), "iso8859-1");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename );
            os = response.getOutputStream();
            inputStream = new FileInputStream( new File(path,"temp"+File.separatorChar+filename) );

            byte[] b = new byte[2048];
            int length;
            while ((length = inputStream.read(b)) > 0) {
                os.write(b, 0, length);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            // 这里主要关闭。
            try {
                if( os != null ) os.close();
                if( inputStream != null )inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping("/saveIcon.json")
    public Object saveIcon(MultipartFile file) throws IOException {
        String uuid = UUID.randomUUID().toString();
        String filename = file.getOriginalFilename();
        String filetype = filename.substring(filename.lastIndexOf("."));

        File f = new File(path,"temp");
        if(!f.exists()){
            f.mkdirs();
        }
        f = new File(f,uuid+filetype);
        file.transferTo(f);
        return success(uuid+filetype);
    }

}
