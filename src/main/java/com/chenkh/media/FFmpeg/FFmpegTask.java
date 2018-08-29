package com.chenkh.media.FFmpeg;

import com.chenkh.media.framework.SpringContextUtils;
import com.chenkh.media.mapper.FFmpegTaskMapper;
import com.chenkh.media.mapper.MovMapper;
import com.chenkh.media.utils.CmdUtils;
import com.chenkh.media.utils.MapUtils;
import org.apache.oro.text.regex.*;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FFmpegTask {

    private String taskid;
    private FFmpegCmdBuilder builder;
    private String descript;
    private String inputFile;
    private String outputDir;
    private String realOutputDir;
    private String thumbnail;
    private FFmpegTaskMapper fFmpegTaskMapper;
    private MovMapper movMapper;

    public FFmpegTask(String taskid,String input, String output){
        this.taskid = taskid;
        this.builder = new FFmpegCmdBuilder();
        this.inputFile = input;
        this.outputDir = output;
        this.realOutputDir = CmdUtils.getRealPath(output);
        this.fFmpegTaskMapper = SpringContextUtils.getBean(FFmpegTaskMapper.class);
        this.movMapper = SpringContextUtils.getBean(MovMapper.class);
    }

    /**
     * 初始化FFmpegCmdBuilder， 数据库中插入任务，获取输入视频详细信息并返回
     * @return <br/>
     * codetype：编码格式；<br/>
     * file_path：输出文件路径；<br/>
     * duration：视频时长；<br/>
     * bit_rate：码率；<br/>
     * resolution：分辨率；<br/>
     * fps：fps
     */
    public Map<String,Object> commit(){
        Map<String,Object> param = new HashMap<>();
        param.put("id",this.taskid);
        param.put("time",System.currentTimeMillis()/1000);
        param.put("inputPath",this.inputFile);
        builder.setInputFilePath(this.inputFile);
        String outfile = this.inputFile.substring(this.inputFile.lastIndexOf("\\"),this.inputFile.lastIndexOf("."))+".mp4";
        String path = checkFile(this.outputDir+outfile);
        builder.setOutputFilePath(path);
        builder.setRealOutputFilePath(CmdUtils.getRealPath(path));
        param.put("outputPath",this.outputDir+outfile);

        Map<String,Object> map = getInfo(CmdUtils.runCmdReErr(builder.getInfoCmd()));
        map.put("file_path",this.outputDir+outfile);
        boolean copy = true;
        if(!MapUtils.getString(map,"codetype","").contains("h264")){
            builder.addParams("-c:v","libx264");
            copy = false;
        }
        if(MapUtils.getintValue(map,"bit_rate",0)>1500){//-b:v 2000K -maxrate 2500k
            builder.addParams("-b:v","1500K");
            builder.addParams("-maxrate","1800K");
            copy = false;
        }
        String resolution = MapUtils.getString(map,"resolution","x");
        int pxx = Integer.valueOf(resolution.substring(0,resolution.indexOf("x")));

        if(pxx>1280){//-vf scale=1280:-1
            builder.addParams("-vf","scale=1280:-1");
            copy = false;
        }
        if(copy){
            builder.addParams("-vcodec","copy");
            builder.addParams("-acodec","copy");
        }
        System.out.println(builder.getCmd());
        param.put("cmd",builder.getCmd());
        param.put("descript",this.descript);
        fFmpegTaskMapper.addTask(param);
        return map;
    }

    private String checkFile(String path){
        if(new File(path).exists()){
            return path.substring(0,path.lastIndexOf("."))+"(1)"+path.substring(path.lastIndexOf("."));
        }else{
            return path;
        }
    }

    private Map<String,Object> getInfo(String info){
        Map<String,Object> map = new HashMap<>();

        PatternCompiler compiler = new Perl5Compiler();
        try {
            String regexDuration = "Duration: (.*?), start: (.*?), bitrate: (\\d*) kb\\/s";
            String regexVideo = "Video: (.*?), (.*?), (.*?), (.*?), (.*?), (.*?), (.*?)[,\\s]";
            String regexAudio = "Audio: (.*?), (\\d*) Hz";

            Pattern patternDuration = compiler.compile(regexDuration, Perl5Compiler.CASE_INSENSITIVE_MASK);
            PatternMatcher matcherDuration = new Perl5Matcher();
            if (matcherDuration.contains(info, patternDuration)) {
                MatchResult re = matcherDuration.getMatch();
                map.put("duration",re.group(1));
                map.put("bit_rate",re.group(3)+"kb/s");
                System.out.println("视频时长\t" + re.group(1));
                System.out.println("码率\t" + re.group(3)+"kb/s");
            }

            Pattern patternVideo = compiler.compile(regexVideo,Perl5Compiler.CASE_INSENSITIVE_MASK);
            PatternMatcher matcherVideo = new Perl5Matcher();

            if(matcherVideo.contains(info, patternVideo)){
                MatchResult re = matcherVideo.getMatch();
                map.put("codetype",re.group(1));
                System.out.println("编码格式\t" +re.group(1));
                if(re.group(2).contains("(")){
                    map.put("resolution",re.group(4));
                    map.put("fps",re.group(6));
                    System.out.println("视频格式\t" +re.group(2)+", "+re.group(3));
                    System.out.println("分辨率\t" +re.group(4));
                    System.out.println("fps\t" +re.group(6));
                }else{
                    map.put("resolution",re.group(3));
                    map.put("fps",re.group(5));
                    System.out.println("视频格式\t" +re.group(2));
                    System.out.println("分辨率\t" +re.group(3));
                    System.out.println("fps\t" +re.group(5));
                }

             }
            Pattern patternAudio = compiler.compile(regexAudio,Perl5Compiler.CASE_INSENSITIVE_MASK);
            PatternMatcher matcherAudio = new Perl5Matcher();
            if(matcherAudio.contains(info, patternAudio)){
                MatchResult re = matcherAudio.getMatch();
                System.out.println("音频编码\t" +re.group(1));
                System.out.println("音频采样频率\t" +re.group(2));
            }
        } catch (MalformedPatternException e) {
            e.printStackTrace();
        }
        return map;
    }

    private String getFileType(String file){
        return file.substring(file.lastIndexOf(".")+1);
    }

    public void doIt(){
        System.out.println("FFmpegTask:doIt...");
        System.out.println("FFmpegTask: update task status...");

        Map<String,Object> map = new HashMap<>();
        map.put("id",this.taskid);
        map.put("start_time",System.currentTimeMillis()/1000);
        map.put("status",1);
        fFmpegTaskMapper.updateTask(map);

        File f = new File(this.realOutputDir);
        if(!f.exists()){
            f.mkdirs();
        }
        String cmd = this.builder.getCmd();
        System.out.println("FFmpegTask: run cmd: "+cmd);
        CmdUtils.runCmdReErr(this.builder.getCmd());
        System.out.println("FFmpegTask: run cmd end ");

        try {
            byte[] b = getThumbnail();
            long file_size = new File(this.builder.getRealOutputFilePath()).length();
            map.clear();
            map.put("id",this.taskid);
            map.put("file_size",file_size);
            map.put("thumbnail",b);
            movMapper.updataThumbnail(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        map.clear();
        map.put("id",this.taskid);
        map.put("end_time",System.currentTimeMillis()/1000);
        map.put("status",2);
        fFmpegTaskMapper.updateTask(map);
        System.out.println("FFmpegTask: end ");
    }

    public byte[] getThumbnail() throws IOException {
        String cmd = builder.getOutFileThumbnailCmd(this.thumbnail);
        CmdUtils.runCmdReErr(cmd);
        File f = new File(builder.getOutputThumbnailPath());
        InputStream is = new FileInputStream(f);
        byte[] b = FileCopyUtils.copyToByteArray(is);
        f.delete();
        return b;
    }

    public String getTaskid() {
        return taskid;
    }

    public void setTaskid(String taskid) {
        this.taskid = taskid;
    }

    public FFmpegCmdBuilder getBuilder() {
        return builder;
    }

    public void setBuilder(FFmpegCmdBuilder builder) {
        this.builder = builder;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
