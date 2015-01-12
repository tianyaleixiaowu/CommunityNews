package com.tianyalei.communitynews.application;

import com.baidu.frontia.FrontiaApplication;

/**
 * Created by wuwf on 2015/1/12.
 */
public class MyApplication extends FrontiaApplication {

    private static MyApplication context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

    }

    public static MyApplication getInstance() {
        return context;
    }

}
