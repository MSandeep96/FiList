package com.sande.filist.Fragments.ViewCompletedFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionFragment extends Fragment {

    private static final String DATE = "date";

    // TODO: Rename and change types of parameters
    private long dateAdded;
    private View mView;
    private CompletedDB comObj;
    private EditText mDesc;
    private boolean isInEditable;


    public DescriptionFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DescriptionFragment newInstance(long date) {
        DescriptionFragment fragment = new DescriptionFragment();
        Bundle args = new Bundle();
        args.putLong(DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateAdded = getArguments().getLong(DATE);
        }
        comObj= Realm.getDefaultInstance().where(CompletedDB.class).equalTo("comTimeComp",dateAdded).findFirst();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_description, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mView=getView();
        if(mView!=null){
            //// TODO: 08-Apr-16 Add time completed and time added also look up location
            mDesc=(EditText) mView.findViewById(R.id.desc_tv_fd);
            mDesc.setText(comObj.comDesc);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_fragment_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_menu_tfm) {
            if (!isInEditable) {
                mDesc.setEnabled(true);
                item.setTitle("Add");
                isInEditable = true;
                if(mDesc.getText().toString().equals("No description provided.")){
                    mDesc.setText("");
                }
            }else{
                Realm.getDefaultInstance().beginTransaction();
                if(mDesc.getText().length()==0){
                    comObj.comDesc="No description provided.";
                    mDesc.setText("No description provided.");
                }else{
                    comObj.comDesc=mDesc.getText().toString();
                }
                Realm.getDefaultInstance().commitTransaction();
                mDesc.setEnabled(false);
                item.setTitle("Edit");
                isInEditable=false;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
