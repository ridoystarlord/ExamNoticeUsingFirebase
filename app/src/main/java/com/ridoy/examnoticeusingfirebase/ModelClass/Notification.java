package com.ridoy.examnoticeusingfirebase.ModelClass;

import android.app.Application;

import com.onesignal.OneSignal;

public class Notification extends Application {
    private static final String ONESIGNAL_APP_ID = "0d327b29-078c-4ca6-9cc3-50859941ae5f";
    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
    }
}
