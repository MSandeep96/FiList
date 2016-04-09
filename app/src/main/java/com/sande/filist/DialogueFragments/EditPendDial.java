package com.sande.filist.DialogueFragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.sande.filist.Interfaces.AdaptersMainCallbackInterface;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;

import io.realm.Realm;

/**
 * Created by Sandeep on 05-Apr-16.
 */
public class EditPendDial extends DialogFragment {

    private EditText mEditText;
    private Button mPosButton;
    private Button mNegButton;
    private long mTimeAdded;
    private String mTitle;
    private AdaptersMainCallbackInterface mActiInterfaced;


    public static EditPendDial getInstance(PendingDB obj){
        EditPendDial epd=new EditPendDial();
        Bundle args=new Bundle();
        args.putLong("longTime",obj.dateAdded);//pun
        args.putString("title",obj.task);
        epd.setArguments(args);
        return epd;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mTimeAdded=getArguments().getLong("longTime");
        mTitle=getArguments().getString("title");
        mActiInterfaced=(AdaptersMainCallbackInterface)getContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.dialogue_edit_item_frag,container,false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        mEditText=(EditText)rootView.findViewById(R.id.et_deif);
        mEditText.setText(mTitle);
        mEditText.setSelection(mTitle.length());
        mEditText.addTextChangedListener(new TextWatcher() {
            boolean onClickSet=false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!(s.toString().equals(mTitle)||s.toString().length()==0)){
                    if(onClickSet){

                    }else{
                        mPosButton.setClickable(true);
                        mPosButton.setTextColor(ContextCompat.getColor(getContext(),R.color.colorAccent));
                        onClickSet=true;
                    }
                }else{
                    if(onClickSet){
                        mPosButton.setClickable(false);
                        mPosButton.setTextColor(ContextCompat.getColor(getContext(), R.color.colorDisabled));
                        onClickSet=false;
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mPosButton=(Button)rootView.findViewById(R.id.btn_add_deif);
        mPosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mEditText.getText().toString();
                Realm mRealm=Realm.getDefaultInstance();
                PendingDB mPendObj=mRealm.where(PendingDB.class).equalTo("dateAdded",mTimeAdded).findFirst();
                if (mRealm.isInTransaction())
                    mRealm.commitTransaction();
                mRealm.beginTransaction();
                mPendObj.setTask(title);
                mRealm.commitTransaction();
                mActiInterfaced.callDataChangedPending();
                dismiss();
            }
        });
        mNegButton=(Button)rootView.findViewById(R.id.btn_dis_deif);
        mNegButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return rootView;
    }
}
