package com.sande.filist.Interfaces;

import com.sande.filist.RealmClasses.PendingDB;

/**
 * Created by Sandeep on 05-Apr-16.
 */
public interface PendingMainCallbackInterface {
    public void callETD(PendingDB pendingObj);
    public void callBackETD();
    public void callAddDetails(long dateAdded);
}
