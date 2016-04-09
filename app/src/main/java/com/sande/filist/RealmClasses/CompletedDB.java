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
    public String imageURIs;
    public String firstImage;
    public long dateAdded;
    public String task;


    public CompletedDB() {
    }

    public CompletedDB(String comTitle, String comDesc, long comTimeComp, long dateAdded, String task) {
        this.comTitle = comTitle;
        this.comDesc = comDesc;
        this.comTimeComp = comTimeComp;
        this.dateAdded = dateAdded;
        this.task = task;
    }

    public void setImageUris(ArrayList<Uri> imgs) {
        firstImage = imgs.get(0).toString();
        String imgUris = "";
        for (Uri x : imgs) {
            imgUris += x.toString() + "*";
        }
        imageURIs=imgUris;
    }

    public ArrayList<String> getImageStrings() {
        if(imageURIs!=null) {
            String[] imgPaths = imageURIs.split("[*]");
            return new ArrayList<String>(Arrays.asList(imgPaths));
        }
        return null;
    }

    public String getComDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        Date resultdate = new Date(comTimeComp);
        return sdf.format(resultdate);
    }

    public String getPendDateFormatted() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        Date resultdate = new Date(dateAdded);
        return sdf.format(resultdate);

    }

}
