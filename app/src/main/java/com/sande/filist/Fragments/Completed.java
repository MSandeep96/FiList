package com.sande.filist.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.sande.filist.Adapters.CompletedAdapter;
import com.sande.filist.Interfaces.SwipeableLeft;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;
import com.sande.filist.RecyclerViewDecorations.Divider;
import com.sande.filist.RecyclerViewDecorations.SwipeLeft;
import com.sande.filist.Utils.Utils;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Completed#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Completed extends Fragment implements SwipeableLeft {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View mView;
    private Context mContext;
    private CompletedAdapter mComAda;
    private Realm mRealm;
    private LinearLayout mLinearLay;
    private RealmResults<CompletedDB> resultsCom;
    private Snackbar mSnack;


    public Completed() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Completed.
     */
    // TODO: Rename and change types and number of parameters
    public static Completed newInstance(String param1, String param2) {
        Completed fragment = new Completed();
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
        mRealm = Realm.getDefaultInstance();
        resultsCom = mRealm.where(CompletedDB.class).findAll();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getContext();
        mView = getView();
        if (mView != null) {
            mLinearLay = (LinearLayout) mView.findViewById(R.id.ll_fc);
            RecyclerView mRecyclerView = (RecyclerView) mView.findViewById(R.id.rv_fc);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
            mComAda = new CompletedAdapter(mContext, resultsCom);
            mRecyclerView.setAdapter(mComAda);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.addItemDecoration(new Divider(mContext, LinearLayout.VERTICAL));
            SwipeLeft mSwiper = new SwipeLeft(this);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(mSwiper);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mSnack != null) {
            if (mSnack.isShown()) {
                mSnack.dismiss();
            }
        }
    }

    @Override
    public void swipedLeft(int position) {
        mComAda.notifyItemRemoved(position);
        if (mRealm.isInTransaction()) {
            mRealm.commitTransaction();
        }
        mRealm.beginTransaction();
        resultsCom.get(position).removeFromRealm();
        mSnack = Snackbar.make(mLinearLay, "Deleted", Snackbar.LENGTH_LONG);
        mSnack.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRealm.isInTransaction())
                    mRealm.cancelTransaction();
                mComAda.notifyDataSetChanged();
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
}
