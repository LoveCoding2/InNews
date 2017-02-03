package com.example.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import com.example.MyApplication;

/**
 * Created by kf on 2016/7/4.
 */
public class ConfigManage {
    private static final String name = "config";
    public volatile static ConfigManage configManage;
    public static ConfigManage getInstance() {
        if (configManage == null) {
            synchronized (ConfigManage.class) {
              if(configManage==null){
                  configManage=new ConfigManage();
              }
            }
        }

        return  configManage;
    }


    /**
     * 通过键和值保存数据到SharedPreferences
     *
     * @return
     */
    public void RemoveKey(String key) {
        if (key != null) {
            getMySharePreferences().edit().remove(key);
        }
    }
   public SharedPreferences getMySharePreferences(){
       return  MyApplication.getApplication().getSharedPreferences(name, Context.MODE_PRIVATE);
   }
    /**
     * 通过键和值保存数据到SharedPreferences
     *
     * @param name
     * @param value
     * @return
     */
    public boolean setString(String name, String value) {
        return getMySharePreferences() .edit().putString(name, value).commit();
    }

    /**
     * 通过键获取保存在SharedPreferences的值
     *
     * @param name
     * @return string
     */
    public String getString(String name) {
        return getMySharePreferences().getString(name, "");
    }

    /**
     * 保存int类型值到SharedPreferences
     *
     * @param name
     * @param value
     * @return
     */
    public boolean setInt(String name, int value) {
        return getMySharePreferences().edit().putInt(name, value).commit();
    }

    /**
     * 通过键获取int类型值
     *
     * @param name
     * @return int
     */
    public int getInt(String name) {
        return getMySharePreferences().getInt(name, 0);
    }

    public int getInt1(String name) {
        return getMySharePreferences().getInt(name, View.VISIBLE);
    }

    public boolean setLong(String name, long value) {
        return getMySharePreferences().edit().putLong(name, value).commit();
    }

    public long getLong(String name) {
        return getMySharePreferences().getLong(name, 0);
    }

    /**
     * 保存boolean类型值
     *
     * @param name
     * @param value
     * @return
     */
    public boolean setBool(String name, boolean value) {
        return getMySharePreferences().edit().putBoolean(name, value).commit();
    }

    /**
     * 通过键获取保存的boolean类型值，默认为true
     *
     * @param name
     * @return
     */
    public boolean getBool(String name) {
        return getMySharePreferences().getBoolean(name, false);
    }

    /**
     * 通过键获取保存的boolean类型值，默认为true
     *
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return getMySharePreferences().getBoolean(name, false);
    }


    public boolean getBoolean1(String name) {
        return getMySharePreferences().getBoolean(name, true);
    }

    /**
     * 保存boolean类型值
     *
     * @param name
     * @param value
     * @return
     */
    public boolean setBoolean(String name, boolean value) {
        return getMySharePreferences().edit().putBoolean(name, value).commit();
    }
}
