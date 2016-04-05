package com.sande.filist.RealmClasses;

import com.sande.filist.Fragments.Pending;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class PendingDB extends RealmObject {
    @PrimaryKey
    private long dateAdded;
    @Required
    private String title;

    public PendingDB(){}

    public PendingDB(long dateAdded, String title) {
        this.dateAdded = dateAdded;
        this.title = title;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateFormatted(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        Date resultdate = new Date(dateAdded);
        return sdf.format(resultdate);
    }
}
