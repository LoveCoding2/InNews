package com.example.util;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Administrator on 2017/1/3.
 */

public class OkManager {
    private OkHttpClient okHttpClient;
    private static OkManager okManager;
    private OkManager()
    {
        okHttpClient=new OkHttpClient();
    }

    public static OkManager getInstance()
    {
        if (okManager==null)
        {
            synchronized (OkManager.class)
            {
                if (okManager==null)
                {
                    okManager=new OkManager();
                }
            }
        }
        return  okManager;
    }

    public Call getCall(String url) {
        Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        return call;
    }



}
