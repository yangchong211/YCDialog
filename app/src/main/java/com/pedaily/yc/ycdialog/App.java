package com.pedaily.yc.ycdialog;

import android.app.Application;

import com.pedaily.yc.ycdialoglib.toast.ToastUtils;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}
