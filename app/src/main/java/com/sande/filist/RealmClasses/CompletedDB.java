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
    public String imagesString;
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
        StringBuilder mStrBui=new StringBuilder();
        for (Uri x : imgs) {
            mStrBui.append(x.toString());
            mStrBui.append("*");
        }
        imagesString =mStrBui.toString();
    }

    public void setImageStrings(ArrayList<String> imgs) {
        firstImage = imgs.get(0);
        StringBuilder mStrBui=new StringBuilder();
        for (String x : imgs) {
            mStrBui.append(x);
            mStrBui.append("*");
        }
        imagesString =mStrBui.toString();
    }

    public ArrayList<String> getImageStrings() {
        if(imagesString !=null) {
            String[] imgPaths = imagesString.split("[*]");
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
