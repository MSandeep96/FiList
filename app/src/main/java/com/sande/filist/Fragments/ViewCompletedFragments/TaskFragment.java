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
import android.widget.Toast;

import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TaskFragment extends Fragment {
    private static final String ARG_DATE = "param1";
    private CompletedDB comObj;
    private long dateAdded;
    private View mView;
    private EditText mTitle;
    private EditText mTask;
    private boolean isInEditable = false;


    public TaskFragment() {
        // Required empty public constructor
    }

    public static TaskFragment newInstance(long date) {
        TaskFragment fragment = new TaskFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateAdded = getArguments().getLong(ARG_DATE);
        }
        comObj = Realm.getDefaultInstance().where(CompletedDB.class).equalTo("comTimeComp", dateAdded).findFirst();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mView = getView();
        if (mView != null) {
            mTitle = (EditText) mView.findViewById(R.id.title_tv_ftask);
            mTitle.setText(comObj.comTitle);
            mTask = (EditText) mView.findViewById(R.id.task_tv_ftask);
            mTask.setText(comObj.task);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.task_fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.edit_menu_tfm) {
            if (!isInEditable) {
                mTitle.setEnabled(true);
                mTask.setEnabled(true);
                item.setTitle("Add");
                isInEditable = true;
            }else{
                if(mTask.getText().length()==0 && mTitle.getText().length()==0){
                    Toast.makeText(getContext(), "Neither can be empty", Toast.LENGTH_SHORT).show();
                }else if(mTask.getText().length()==0){
                    Toast.makeText(getContext(), "Task cant be empty", Toast.LENGTH_SHORT).show();
                }else if(mTitle.getText().length()==0){
                    Toast.makeText(getContext(),"Title can't be empty",Toast.LENGTH_SHORT).show();
                }else {
                    Realm.getDefaultInstance().beginTransaction();
                    comObj.comTitle=mTitle.getText().toString();
                    comObj.task=mTask.getText().toString();
                    Realm.getDefaultInstance().commitTransaction();
                    mTitle.setEnabled(false);
                    mTask.setEnabled(false);
                    item.setTitle("Edit");
                    isInEditable = false;
                }
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
