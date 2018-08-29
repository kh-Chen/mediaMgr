package com.chenkh.media.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class CmdUtils {

    public static String getRealPath(String path){
        return runCmdReOut("cmd /c echo "+path);
    }

    public static String runCmdReErr(String cmd){
        System.out.println("runCmd: "+cmd);
        StringBuilder sb = new StringBuilder();
        int exitVal = 0;
        InputStream stderr = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            stderr = proc.getErrorStream();
            isr = new InputStreamReader(stderr, Charset.forName("GBK"));
            br = new BufferedReader(isr);
            String line = null;
            while ( (line = br.readLine()) != null) {
                sb.append(line);
            }
            exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);

        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }finally {
            closeStream(stderr, isr, br);
        }
        return sb.toString() ;
    }

    public static String runCmdReOut(String cmd){
        System.out.println("runCmd: "+cmd);
        StringBuilder sb = new StringBuilder();
        int exitVal = 0;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec(cmd);
            proc.getOutputStream().close();
            is = proc.getInputStream();
            isr = new InputStreamReader(is, Charset.forName("GBK"));
            br = new BufferedReader(isr);
            String line = null;
            while ( (line = br.readLine()) != null) {
                sb.append(line);
            }
            exitVal = proc.waitFor();
            System.out.println("Process exitValue: " + exitVal);

        }catch (InterruptedException | IOException e) {
            e.printStackTrace();
        }finally {
            closeStream(is, isr, br);
        }
        return sb.toString();
    }

    private static void closeStream(InputStream is, InputStreamReader isr, BufferedReader br) {
        try {
            if(br!=null)br.close();
            if(isr!=null)isr.close();
            if(is!=null)is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
