package com.sande.filist.DialogueFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sande.filist.Activity.MainActivity;
import com.sande.filist.Fragments.Pending;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;

import io.realm.Realm;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class AddPendDial extends DialogFragment {

    private EditText mEditText;
    private Button mPosButton;
    private Button mNegButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dialgoue_add_item_frag,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        mEditText=(EditText)rootView.findViewById(R.id.et_daif);
        mPosButton=(Button)rootView.findViewById(R.id.btn_add_daif);
        mPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEditText.getText().toString().length() == 0) {
                    Toast.makeText(getContext(), "Title can't be empty!", Toast.LENGTH_SHORT).show();
                } else {
                    long timeMillis = System.currentTimeMillis();
                    String title = mEditText.getText().toString();
                    PendingDB mPendObj = new PendingDB(timeMillis, title);
                    Realm mRealm = Realm.getDefaultInstance();
                    if (mRealm.isInTransaction())
                        mRealm.commitTransaction();
                    mRealm.beginTransaction();
                    mRealm.copyToRealm(mPendObj);
                    mRealm.commitTransaction();
                    dismiss();
                }
            }
        });
        mNegButton=(Button)rootView.findViewById(R.id.btn_dis_daif);
        mNegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }

}
