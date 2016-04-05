package com.sande.filist;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class RealmInitApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RealmConfiguration mRC=new RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(mRC);
    }
}
