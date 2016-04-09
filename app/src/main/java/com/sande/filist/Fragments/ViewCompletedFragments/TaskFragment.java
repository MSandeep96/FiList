package com.sande.filist.Fragments.ViewCompletedFragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        comObj= Realm.getDefaultInstance().where(CompletedDB.class).equalTo("comTimeComp",dateAdded).findFirst();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mView=getView();
        if (mView != null) {
            TextView mTitle=(TextView) mView.findViewById(R.id.title_tv_ftask);
            mTitle.setText(comObj.comTitle);
            TextView mTask=(TextView)mView.findViewById(R.id.task_tv_ftask);
            mTask.setText(comObj.task);
        }
    }
}
