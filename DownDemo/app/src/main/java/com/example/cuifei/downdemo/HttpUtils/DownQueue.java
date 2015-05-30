package com.example.cuifei.downdemo.HttpUtils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.cuifei.downdemo.Listener.DownListener;
import com.example.cuifei.downdemo.model.DownModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by cuifei on 15/5/30.
 */
public class DownQueue {
    public final static int DOWNBEGIN = 1;
    public final static int DOWNLOADING = 2;
    public final static int DOWNFINISH = 3;

    private DownListener mListener;

    private static DownQueue mDownQueue;

    public static DownQueue getIntance(){
        if(mDownQueue == null){
            mDownQueue = new DownQueue();
        }

        return mDownQueue;
    }



    private List<DownThread> downThreadList = new ArrayList<DownThread>();

    private Lock lock = new ReentrantLock();
    private int count = 0;
    private int finishCount = 0;
    private List<Integer> positionList = new ArrayList<Integer>();
    private Context mContext;

    public DownListener getmListener() {
        return mListener;
    }

    public void setmListener(DownListener mListener) {
        this.mListener = mListener;
    }

    public void setmContext(Context context){
        this.mContext = context;
    }

    public void addDownThread(String url,DownModel.Music music,int position){
        lock.lock();
        positionList.add(position);
        DownThread downThread = new DownThread(url,music,handler);
        music.setDownStatu(DOWNBEGIN);
        mListener.loadWriting(position);
        downThreadList.add(downThread);
        count++;
        lock.unlock();
        Message msg = Message.obtain();
        msg.what = DOWNBEGIN;
        handler.sendMessage(msg);

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what){
                case DOWNBEGIN:

                    if(downThreadList.size()<=count&&downThreadList.size()>finishCount){
                        lock.lock();
                        if(!downThreadList.get(finishCount).isStarted()){
                            downThreadList.get(finishCount).start();
                        }

                        if(downThreadList.get(finishCount).getMusic().getDownStatu() == DOWNBEGIN){
                            downThreadList.get(finishCount).getMusic().setDownStatu(DOWNLOADING);
                        }

                        lock.unlock();
                    }

                    break;
                case DOWNFINISH:
                    mListener.loadFinish(positionList.get(finishCount),100);

                    if(downThreadList.size()>finishCount){
                        lock.lock();

                        finishCount++;
                        Message message = Message.obtain();
                        message.what = DOWNBEGIN;
                       sendMessage(message);

                        lock.unlock();
                    }


                    break;
                case DOWNLOADING:
//                    Toast.makeText(mContext,""+msg.arg1,0).show();
                    mListener.loading(positionList.get(finishCount), msg.arg1);

                    break;
            }

        }
    };



}
