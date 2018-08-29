package com.chenkh.media.FFmpeg;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

@Component
public class FFmpegTaskThread extends Thread {

    private static FFmpegTaskThread me = null;
    private HashMap map = null;
    private ArrayBlockingQueue queue;

    private FFmpegTaskThread (){
        this.queue = new ArrayBlockingQueue(10);
        this.map = new HashMap();
    }

    public static FFmpegTaskThread getInstance(){
        if(me == null ){
            me =  new FFmpegTaskThread();
        }
        return me;
    }

    public void addTask(FFmpegTask task){
        String taskid = task.getTaskid();
//        task.commit();
        map.put(taskid,task);
        queue.offer(task);
    }

    public void removeTask(String taskid) throws Exception{

        Object o = map.get(taskid);
        if(o == null){
            throw new Exception("task not found. taskid:"+taskid);
        }
        queue.remove(o);
        map.remove(taskid);
    }

    @Override
    public void run() {
        super.run();
        while(true){
            try {
                FFmpegTask task = (FFmpegTask) queue.take(); // 如果阻塞队列中没有东西就会等待直到有东西拿到手。
                System.out.println("get one task,run it...");
                map.remove(task.getTaskid());
                task.doIt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private void runCmd(String cmd){
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            InputStream stderr = proc.getErrorStream();
            InputStreamReader isr = new InputStreamReader(stderr);
            BufferedReader br = new BufferedReader(isr);
            String line = null;

            while ( (line = br.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);
        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }
}
