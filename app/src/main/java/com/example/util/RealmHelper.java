package com.example.util;

import android.content.Context;

import com.example.bean.CollectBean;
import com.example.bean.CurrentNews;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by Administrator on 2017/1/5.
 */

public class RealmHelper {
    public static final String DB_NAME = "mycollect.realm";
    private Realm mRealm;


    public RealmHelper(Context context) {
        mRealm = Realm.getDefaultInstance();
    }

    /**
     * add （增）
     */
    public void addCollcet(final CurrentNews collectBean) {
        mRealm.beginTransaction();
        mRealm.copyToRealm(collectBean);
        mRealm.commitTransaction();

    }

    /**
     * delete （删）
     */
    public void deleteCollect(String id) {
        CurrentNews collectBean = mRealm.where(CurrentNews.class).equalTo("id", id).findFirst();
        mRealm.beginTransaction();
        collectBean.deleteFromRealm();
        mRealm.commitTransaction();

    }

    /**
     * query （查询所有）
     */
    public List<CurrentNews> queryAllCollect()
    {
        RealmResults<CurrentNews> collectBeanRealmResults = mRealm.where(CurrentNews.class).findAll();
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        //降序排列
        collectBeanRealmResults=collectBeanRealmResults.sort("id", Sort.DESCENDING);
//        //降序排列
//        dogs=dogs.sort("id", Sort.DESCENDING);
        return mRealm.copyFromRealm(collectBeanRealmResults);
    }

    /**
     * query （根据Id（主键）查）
     */
    public CurrentNews queryCollectById(String id)
    {
       CurrentNews collectBean= mRealm.where(CurrentNews.class).equalTo("id", id).findFirst();
        return collectBean;
    }


    public boolean isCollectExist(String id)
    {
        CurrentNews collectBean=mRealm.where(CurrentNews.class).equalTo("id",id).findFirst();
        if (collectBean==null){
            return false;
        }else {
            return  true;
        }
    }

    public Realm getRealm(){

        return mRealm;
    }

    public void close(){
        if (mRealm!=null){
            mRealm.close();
        }
    }
}
