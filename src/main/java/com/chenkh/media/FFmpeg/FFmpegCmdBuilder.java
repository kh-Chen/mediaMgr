package com.chenkh.media.FFmpeg;

import com.chenkh.media.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FFmpegCmdBuilder {

    private String inputFilePath = "";
    private String outputFilePath = "";
    private String realOutputFilePath = "";
    private String outputThumbnailPath = "";
    private Map<String,String> params;
    private int controlCpu = 2;
    private String startTime;

    public FFmpegCmdBuilder(){
        params = new HashMap<>();
    }

    public FFmpegCmdBuilder(String inputFilePath,String outputFilePath){
        this.inputFilePath = inputFilePath;
        this.outputFilePath = outputFilePath;
        params = new HashMap<>();
    }

    public String getInputFilePath() {
        return inputFilePath;
    }

    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public String getOutputFilePath() {
        return outputFilePath;
    }

    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public int getControlCpu() {
        return controlCpu;
    }

    public void setControlCpu(int controlCpu) {
        this.controlCpu = controlCpu;
    }

    public String getOutputThumbnailPath() {
        return outputThumbnailPath;
    }

    public void setOutputThumbnailPath(String outputThumbnailPath) {
        this.outputThumbnailPath = outputThumbnailPath;
    }

    public String getRealOutputFilePath() {
        return realOutputFilePath;
    }

    public void setRealOutputFilePath(String realOutputFilePath) {
        this.realOutputFilePath = realOutputFilePath;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void addParams(String paramkey, String val){
        this.params.put(paramkey,val);
    }

    public String getInfoCmd(){
        return "ffmpeg -i \""+this.inputFilePath+"\"";
    }

    public String getOutFileThumbnailCmd(String time){

        if(StringUtils.isEmpty(this.outputThumbnailPath)){
            this.outputThumbnailPath = this.realOutputFilePath.substring(0,this.realOutputFilePath.lastIndexOf("\\"))+"\\temp.jpg";
        }
        return "ffmpeg -ss " + (StringUtils.isEmpty(time) ? "00:01:00" : time) + " -i "+this.realOutputFilePath+" -f image2 -vf scale=360:-1 "+this.outputThumbnailPath;
    }

    public String getCmd() /*throws FileNotFoundException*/ {
        if(this.inputFilePath.equals("") || this.outputFilePath.equals("")){
//            throw new FileNotFoundException();
        }
        StringBuffer sb = new StringBuffer();
        sb.append("ffmpeg ");
        if(!StringUtils.isEmpty(this.startTime)){
            sb.append(" -ss ").append(this.startTime);
        }
        sb.append(" -i \"").append(inputFilePath).append("\"");
        Set<String> keys = this.params.keySet();
        for(String key : keys){
            sb.append(" ").append(key).append(" ").append(params.get(key)).append(" ");
        }
        sb.append("-threads ").append(controlCpu).append(" \"");
        sb.append(this.realOutputFilePath).append("\"");
        return sb.toString();
    }
}
