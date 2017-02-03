package com.example;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.example.util.RealmHelper;
import com.example.weidu.MeActivity;
import com.example.weidu.R;
import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * Created by kf on 2016/7/4.
 */
public class MyApplication extends Application {
    public static MyApplication application;
    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        Realm.init(this);
        RealmConfiguration configuration=new RealmConfiguration.Builder()
                .name(RealmHelper.DB_NAME)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);
/*
        Config.DEBUG=true;
        UMShareAPI.get(this);*/

    }
    public static MyApplication getApplication()
    {
        return application;
    }

//    {
//        PlatformConfig.setSinaWeibo("695395608", "6a280742837d2fa88383d5596fc782dc");
//    }

}
