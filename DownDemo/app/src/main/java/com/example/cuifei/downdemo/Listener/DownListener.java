package com.example.cuifei.downdemo.Listener;

/**
 * Created by cuifei on 15/5/30.
 */
public interface DownListener {

    void loadWriting(int pos);

    void loading(int pos,int progress);

    void loadFinish(int pos,int progress);
}
