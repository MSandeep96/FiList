package com.sande.filist.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sande.filist.Adapters.PendingAdapter;
import com.sande.filist.Interfaces.SwipeableLeft;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;
import com.sande.filist.RecyclerViewDecorations.Divider;
import com.sande.filist.RecyclerViewDecorations.SwipeLeft;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pending#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pending extends Fragment implements SwipeableLeft {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context mContext;
    private View mView;
    private PendingAdapter mAdapter;
    private RealmResults<PendingDB> results;
    private RealmChangeListener mChanList=new RealmChangeListener() {
        @Override
        public void onChange() {
            if(mAdapter!=null){
                mAdapter.update(results);
            }
        }
    };
    private LinearLayout mLinear;
    private Snackbar mSnack;


    public Pending() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Pending.
     */
    // TODO: Rename and change types and number of parameters
    public static Pending newInstance(String param1, String param2) {
        Pending fragment = new Pending();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Realm mRealm=Realm.getDefaultInstance();
        results=mRealm.where(PendingDB.class).findAll();
        Log.i("allh", "onCreate");
    }

    @Override
    public void onStart() {
        super.onStart();
        results.addChangeListener(mChanList);
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mSnack!=null) {
            if (mSnack.isShown()) {
                mSnack.dismiss();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pending, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext=getContext();
        mView=getView();
        if(mView!=null){
            mLinear=(LinearLayout)mView.findViewById(R.id.ll_fp);
            RecyclerView mRecyclerView=(RecyclerView)mView.findViewById(R.id.rv_fp);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mAdapter=new PendingAdapter(mContext,results);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.addItemDecoration(new Divider(mContext, LinearLayout.VERTICAL));
            SwipeLeft mSwipeListener=new SwipeLeft(this);
            ItemTouchHelper mHelper=new ItemTouchHelper(mSwipeListener);
            mHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    @Override
    public void swipedLeft(int position) {
        mAdapter.notifyItemRemoved(position);
        final Realm mRealm=Realm.getDefaultInstance();
        if(mRealm.isInTransaction()){
            mRealm.commitTransaction();
        }
        mRealm.beginTransaction();
        results.get(position).removeFromRealm();
        mSnack=Snackbar.make(mLinear, "Deleted", Snackbar.LENGTH_LONG);
        mSnack.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mRealm.isInTransaction())
                    mRealm.cancelTransaction();
                mAdapter.notifyDataSetChanged();
            }
        });
        mSnack.setCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                if (event != DISMISS_EVENT_ACTION) {
                    if (mRealm.isInTransaction())
                        mRealm.commitTransaction();
                }
            }
        });
        mSnack.show();
    }

    public void callNotifyUpd(){
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("allh", "onDestroy");
        results.removeChangeListener(mChanList);
    }
}
