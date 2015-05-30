package com.example.cuifei.downdemo.HttpUtils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import com.example.cuifei.downdemo.model.DownModel;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by cuifei on 15/5/30.
 */
public class DownThread extends Thread {
    private String url;
    private DownModel.Music music;
    private Handler handler;
    private boolean isStarted;



    public DownThread(String url,DownModel.Music music,Handler handler){
        this.url = url;
        this.music = music;
        this.handler = handler;

    }

    public boolean isStarted() {
        return isStarted;
    }

    public void setIsStarted(boolean isStarted) {
        this.isStarted = isStarted;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public DownModel.Music getMusic() {
        return music;
    }

    public void setMusic(DownModel.Music music) {
        this.music = music;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void run() {
        isStarted = true;
        HttpClient client = new DefaultHttpClient();

        HttpGet httpGet = new HttpGet(url);

        HttpResponse response = null;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        byte[] buffer = new byte[4*1024];

        long totalLength = 0;



        try {
            response = client.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                totalLength = response.getEntity().getContentLength();
                int sumLength = 0;
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), music.getName() + ".mp3");
                if(!file.exists()){
                    file.getParentFile().mkdir();
                }
                fileOutputStream = new FileOutputStream(file);
                inputStream = response.getEntity().getContent();
                int length = 0;
                while (( length= inputStream.read(buffer))!=-1){
                    sumLength +=length;
                    float progress = 100*sumLength/(float)totalLength;

                    Message msg  = Message.obtain();
                    msg.what = DownQueue.DOWNLOADING;
                    msg.arg1 = (int) progress;
                    handler.sendMessage(msg);
                }

                Message finishMsg = Message.obtain();
                finishMsg.what = DownQueue.DOWNFINISH;
                handler.sendMessage(finishMsg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}




















