package com.example.cuifei.downdemo.HttpUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.RequestParams;

public class HttpUtil {

	private static AsyncHttpClient client = new AsyncHttpClient();    //ʵ��������
	
    static
    {
        client.setTimeout(11000);   //�������ӳ�ʱ����������ã�Ĭ��Ϊ10s
    }
    
    public static RequestHandle get(String urlString, AsyncHttpResponseHandler res)    //��һ������url��ȡһ��string����
    {
    	RequestHandle requestHandle = client.get(urlString, res);
    	return requestHandle;
    }
    
    public static RequestHandle get(String urlString, RequestParams params,AsyncHttpResponseHandler res)   //url���������
    {
    	RequestHandle requestHandle = client.get(urlString, params,res);
    	return requestHandle;
    }
    
    public static RequestHandle get(String urlString, JsonHttpResponseHandler res)   //������������ȡjson�����������
    {
        RequestHandle requestHandle = client.get(urlString, res);
        return requestHandle;
    }
    
    public static RequestHandle get(String urlString, RequestParams params,JsonHttpResponseHandler res)   //����������ȡjson�����������
    {
    	RequestHandle requestHandle = client.get(urlString, params,res);
    	return requestHandle;
    }
    
    public static RequestHandle get(String uString, BinaryHttpResponseHandler bHandler)   //��������ʹ�ã��᷵��byte����
    {
    	RequestHandle requestHandle = client.get(uString, bHandler);
    	return requestHandle;
    }
    
    public static RequestHandle post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
       
    	RequestHandle requestHandle = client.post(url, params, responseHandler);
    	return requestHandle;
    }
    
    public static AsyncHttpClient getClient()
    {
        return client;
    }
}
