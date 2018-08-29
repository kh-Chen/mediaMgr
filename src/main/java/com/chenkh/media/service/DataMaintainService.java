package com.chenkh.media.service;

import com.chenkh.media.FFmpeg.FFmpegTask;
import com.chenkh.media.FFmpeg.FFmpegTaskThread;
import com.chenkh.media.bean.Mov;
import com.chenkh.media.mapper.DataMaintainMapper;
import com.chenkh.media.utils.BeanUtils;
import com.chenkh.media.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

@Service
public class DataMaintainService {

    @Resource
    private DataMaintainMapper dataMaintainMapper;

    public void test(String tip) {


    }
}
