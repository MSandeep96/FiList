package com.sande.filist.RealmClasses;

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
    private String task;

    public PendingDB(){}

    public PendingDB(long dateAdded, String task) {
        this.dateAdded = dateAdded;
        this.task = task;
    }

    public long getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(long dateAdded) {
        this.dateAdded = dateAdded;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDateFormatted(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy  HH:mm");
        Date resultdate = new Date(dateAdded);
        return sdf.format(resultdate);
    }
}
