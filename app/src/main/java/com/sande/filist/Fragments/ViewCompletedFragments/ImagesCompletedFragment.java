package com.sande.filist.Fragments.ViewCompletedFragments;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.sande.filist.Adapters.ViewCompletedImageAdapter;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;

import io.realm.Realm;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImagesCompletedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImagesCompletedFragment extends Fragment {
    private static final String ARG_DATE = "dateAdded";
    private long dateAdded;
    private CompletedDB mComObj;
    private Context mContext;
    private View mView;
    private ViewCompletedImageAdapter mAdapter;


    public ImagesCompletedFragment() {
        // Required empty public constructor
    }

    public static ImagesCompletedFragment newInstance(long date) {
        ImagesCompletedFragment fragment = new ImagesCompletedFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_DATE,date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dateAdded = getArguments().getLong(ARG_DATE);
        }
        mComObj= Realm.getDefaultInstance().where(CompletedDB.class).equalTo("comTimeComp",dateAdded).findFirst();
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_images_completed, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getContext();
        mView=getView();
        if(mView!=null){
            final RecyclerView mRecycler=(RecyclerView)mView.findViewById(R.id.rv_fic);
            mRecycler.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter=new ViewCompletedImageAdapter(mContext,mComObj.getImageStrings());
            mRecycler.setAdapter(mAdapter);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.images_completed_frag_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.selc_id){
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStop() {
        super.onStop();
        mAdapter.setSelectable(false);
    }
}
