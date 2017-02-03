package com.example.util;

import android.content.Context;
import android.content.Intent;

import io.vov.vitamio.utils.StringUtils;

/**
 * Created by Administrator on 2017/1/2.
 */

public class ShareUtil {
    private static ShareUtil shareUtil;

    private ShareUtil()
    {

    }

    public static ShareUtil getInstance()
    {
        if (null==shareUtil)
        {
            synchronized (ShareUtil.class)
            {
                if(shareUtil==null)
                {
                    shareUtil=new ShareUtil();
                }
            }
        }
        return  shareUtil;
    }

}
