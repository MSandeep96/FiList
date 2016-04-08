package com.sande.filist.RealmClasses;

import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class CompletedDB extends RealmObject {
    @Required
    public String comTitle;
    public String comDesc;
    @PrimaryKey
    public long comTimeComp;
    public PendingDB pendObj;
    public String imageURIs;
    public String firstImage;

    public CompletedDB(String comTitle, String comDesc, PendingDB pendObj, long comTimeComp) {
        this.comTitle = comTitle;
        this.comDesc = comDesc;
        this.pendObj = pendObj;
        this.comTimeComp = comTimeComp;
    }

    public CompletedDB() {
    }

    public void setImageUris(ArrayList<Uri> imgs){
        firstImage=imgs.get(0).toString();
        String imgUris="";
        for(Uri x:imgs){
            imgUris+=x.toString()+"*";
        }
    }

    public ArrayList<String> getImageUris(){
        ArrayList<String> imgs=new ArrayList<>();
        String[] imgPaths=imageURIs.split("[*]");
        return (ArrayList<String>)Arrays.asList(imgPaths);
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
