package com.sande.filist;

import android.app.Application;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class RealmInitApp extends Application {
    private static RealmInitApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
        RealmConfiguration mRC=new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(mRC);
    }

    public static Context getAppContext(){
        return mInstance.getApplicationContext();
    }
}
