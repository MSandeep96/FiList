package com.sande.filist.RealmClasses;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class CompletedDB extends RealmObject {
    public String comTitle;
    public String comDesc;
    public long comTimeComp;
    public PendingDB pendObj;
    public String imageURIs;

    public CompletedDB(String comTitle, String comDesc, PendingDB pendObj, long comTimeComp) {
        this.comTitle = comTitle;
        this.comDesc = comDesc;
        this.pendObj = pendObj;
        this.comTimeComp = comTimeComp;
    }

    public CompletedDB() {
    }

    public String getComDateFormatted(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        Date resultdate = new Date(comTimeComp);
        return sdf.format(resultdate);
    }

    public String getPendDateFormatted(){
        return pendObj.getDateFormatted();
    }

}
