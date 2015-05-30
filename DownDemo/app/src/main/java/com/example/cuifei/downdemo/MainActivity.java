package com.example.cuifei.downdemo;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cuifei.downdemo.HttpUtils.HttpUtil;
import com.example.cuifei.downdemo.HttpUtils.JSonHelper;
import com.example.cuifei.downdemo.model.DownModel;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import org.apache.http.Header;


public class MainActivity extends Activity {

    private ListView downListView;
    private ListViewAdapter adapter;
    private String url = "http://121.199.8.247/wish/trunk/interface.php?m=Music&a=getMusic&pageNo=1&pageSize=100";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downListView = (ListView)findViewById(R.id.listView);
        adapter = new ListViewAdapter(this,downListView);
        downListView.setAdapter(adapter);
        getData(url);
    }

    public void getData(String url){
        RequestHandle requestHandle = HttpUtil.get(url, new BaseJsonHttpResponseHandler<DownModel>() {
            @Override
            public void onSuccess(int i, Header[] headers, String s, DownModel downModel) {
                Toast.makeText(MainActivity.this,"=="+downModel.getMusic().size(),1).show();
                if(downModel!=null){
                    adapter.setData(downModel);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, Throwable throwable, String s, DownModel downModel) {
                Toast.makeText(MainActivity.this,"获取失败"+throwable.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            protected DownModel parseResponse(String s, boolean b) throws Throwable {
                System.out.println("=====s==="+s);
                try {
                    return JSonHelper.DeserializeJsonToObject(DownModel.class,s);
                }catch (Exception e){
                    e.printStackTrace();
                    return null;
                }

            }
        });
    }

}
