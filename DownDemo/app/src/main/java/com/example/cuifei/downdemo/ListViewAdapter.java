package com.example.cuifei.downdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.cuifei.downdemo.HttpUtils.DownQueue;
import com.example.cuifei.downdemo.Listener.DownListener;
import com.example.cuifei.downdemo.model.DownModel;

import java.util.List;

/**
 * Created by cuifei on 15/5/30.
 */
public class ListViewAdapter extends BaseAdapter {

    private Context mContext;
    private DownModel modelList;
    private DownQueue queue;

    private ListView mListView;

    private DownListener mDownListener = new DownListener() {
        @Override
        public void loadWriting(int pos) {
            modelList.getMusic().get(pos).setDownStatu(DownQueue.DOWNBEGIN);
            notifyDataSetChanged();
        }

        @Override
        public void loading(int pos, int progress) {
            modelList.getMusic().get(pos).setProgress(progress);
            modelList.getMusic().get(pos).setDownStatu(DownQueue.DOWNLOADING);
            updataView(pos,progress);
        }

        @Override
        public void loadFinish(int pos, int progress) {
            modelList.getMusic().get(pos).setDownStatu(DownQueue.DOWNFINISH);
            modelList.getMusic().get(pos).setProgress(100);
            notifyDataSetChanged();
        }
    };

    public void updataView(int pos, int progress) {
        int visiablePosition = mListView.getFirstVisiblePosition();
        View view = mListView.getChildAt(pos - visiablePosition);
        if (view != null) {
            ProgressBar pb = (ProgressBar) view.findViewById(R.id.down_pb);
            Button but = (Button) view.findViewById(R.id.down_but);
            pb.setProgress(progress);
            but.setText(progress + "%");
        }

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button but = (Button) v;
            int pos = (int) but.getTag();
            DownModel.Music music = modelList.getMusic().get(pos);
            queue.addDownThread(music.getMusic(), music,pos);
            queue.setmContext(mContext);

        }
    };

    public ListViewAdapter(Context context,ListView listView){
        this.mContext = context;
        this.mListView = listView;
        queue = DownQueue.getIntance();
        queue.setmListener(mDownListener);
    }

    public void setData(DownModel models){
        this.modelList = models;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        if(modelList!=null&&modelList.getMusic()!=null){
            return modelList.getMusic().size();

        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return modelList.getMusic().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        Button downBut;
        ProgressBar downPb;
        TextView tvName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item,null,false);
            vh = new ViewHolder();
            vh.downBut = (Button)convertView.findViewById(R.id.down_but);
            vh.downPb = (ProgressBar)convertView.findViewById(R.id.down_pb);
            vh.tvName = (TextView)convertView.findViewById(R.id.name);
            convertView.setTag(vh);

        }
        vh = (ViewHolder)convertView.getTag();
        vh.tvName.setText(modelList.getMusic().get(position).getName());
        vh.downBut.setTag(position);
        vh.downBut.setOnClickListener(listener);
        vh.downPb.setProgress(modelList.getMusic().get(position).getProgress());
        switch (modelList.getMusic().get(position).getDownStatu()){
            case DownQueue.DOWNBEGIN:
                vh.downBut.setText("writing");
                break;
            case DownQueue.DOWNLOADING:
                vh.downBut.setText(modelList.getMusic().get(position).getProgress()+"%");
                break;
            case DownQueue.DOWNFINISH:
                vh.downBut.setText("finish");
                break;
            default:
                vh.downBut.setText("down");
                break;
        }
        return convertView;
    }
}





















