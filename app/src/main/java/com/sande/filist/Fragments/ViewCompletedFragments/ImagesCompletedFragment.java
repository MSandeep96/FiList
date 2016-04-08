package com.sande.filist.Fragments.ViewCompletedFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
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
            RecyclerView mRecycler=(RecyclerView)mView.findViewById(R.id.rv_fic);
            mRecycler.setLayoutManager(new StaggeredGridLayoutManager(StaggeredGridLayoutManager.VERTICAL,2));
            ViewCompletedImageAdapter mAdapter=new ViewCompletedImageAdapter(mContext,mComObj.getImageStrings());
            mRecycler.setAdapter(mAdapter);
        }
    }
}
