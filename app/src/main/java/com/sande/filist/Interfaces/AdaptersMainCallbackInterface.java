package com.sande.filist.Interfaces;

import com.sande.filist.RealmClasses.PendingDB;

/**
 * Created by Sandeep on 05-Apr-16.
 */
public interface AdaptersMainCallbackInterface {
    public void callEditDialog(PendingDB pendingObj);
    public void callDataChangedPending();
    public void callAddDetailsActivity(long dateAdded);
    public void compCallViewCompletedActivity(long dateCompleted);
}
