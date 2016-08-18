package com.example.xxx.idcarddemo;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

// 请在AndroidManifest.xml中application标签下android:name中指定该类
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ApiStoreSDK.init(this, "4e7f08652ce7476a0c23becace9d3147");//自己申请的apikey
    }
}
