package com.sande.filist.Fragments.MainActivityFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sande.filist.Adapters.InstaAdapter;
import com.sande.filist.Network.VolleySingleton;
import com.sande.filist.Pojo.InstaFeed;
import com.sande.filist.R;
import com.sande.filist.Interfaces.APIConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Inspiration#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Inspiration extends Fragment implements APIConstants {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ROTATE_INSTA ="RotateInsta";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton iVolSing;
    private RequestQueue iReqQue;
    private Context mContext;
    private ArrayList<InstaFeed> feed=new ArrayList<>();
    private InstaAdapter mInsta;
    private ProgressBar mProgressBar;
    private String nextUrl;
    private boolean loading=false;

    public Inspiration() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Inspiration.
     */
    // TODO: Rename and change types and number of parameters
    public static Inspiration newInstance(String param1, String param2) {
        Inspiration fragment = new Inspiration();
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
        mContext=getContext();
        setRetainInstance(true);
        iVolSing= VolleySingleton.getInstance();
        iReqQue=iVolSing.getRequestQueue();
    }

    private void makeRequest(String urlToInsta){
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, urlToInsta, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                mProgressBar.setVisibility(View.GONE);
                feed=parseJsonObject(response);
                mInsta.addmFeed(feed);
                loading=false;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        iReqQue.add(req);
    }

    private ArrayList<InstaFeed> parseJsonObject(JSONObject response) {
        ArrayList<InstaFeed> insFeed=new ArrayList<>();
        if(response==null || response.length()==0){
            return null;
        }
        try {
            if(response.has(KEY_DATA)) {
                JSONObject pagination=response.getJSONObject(KEY_PAGINATION);
                nextUrl=pagination.getString(KEY_NEXT_URL);
                JSONArray arrayData = response.getJSONArray(KEY_DATA);
                for (int i=0;i<arrayData.length();i++){
                    JSONObject currentImage=arrayData.getJSONObject(i);
                    JSONObject images=currentImage.getJSONObject(KEY_IMAGES);
                    JSONObject stand=images.getJSONObject(KEY_STANDARD);
                    String sturl=stand.getString(KEY_URL);
                    JSONObject caption=currentImage.getJSONObject(KEY_CAPTION);
                    String capText=caption.getString(KEY_TEXT);
                    JSONObject from=caption.getJSONObject(KEY_FROM);
                    String userName=from.getString(KEY_USERNAME);
                    String profPic=from.getString(KEY_PROF);
                    String postID=currentImage.getString(KEY_ID);
                    InstaFeed mIns=new InstaFeed(sturl,capText,userName,profPic,postID);
                    insFeed.add(mIns);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return insFeed;
    }

    int pastVisiblesItems, visibleItemCount, totalItemCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_inspiration, container, false);
        mProgressBar=(ProgressBar)mView.findViewById(R.id.pb_fi);
        final RecyclerView mRecyclerView=(RecyclerView)mView.findViewById(R.id.rv_in);
        mInsta=new InstaAdapter(mContext);
        final LinearLayoutManager mLineMan=new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(mLineMan);
        mRecyclerView.setAdapter(mInsta);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if(dy>0){
                    //scrolled down
                    visibleItemCount=mLineMan.getChildCount();
                    totalItemCount=mLineMan.getItemCount();
                    pastVisiblesItems=mLineMan.findFirstVisibleItemPosition();
                    if((visibleItemCount+pastVisiblesItems)>=totalItemCount){
                        if(!loading) {
                            makeRequest(nextUrl);
                            loading = true;
                        }
                    }
                }
            }
        });
        if(savedInstanceState!=null){
            mProgressBar.setVisibility(View.GONE);
            feed=savedInstanceState.getParcelableArrayList(ROTATE_INSTA);
            mInsta.setmFeed(feed);
        }else{
            makeRequest(getReqUrl());
        }
        return mView;
    }

    // TODO: 10-Apr-16 make recyclerview without layout for images

    private String getReqUrl(){
        //// TODO: 09-Apr-16 Maybe spanner with other tags or user specifies keyword in settings fragment
        String url=URL_INSTA.replace("{}","bucketlist");//tag to find in instagram
        url+=ACCESS_TOKEN;
        return url;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ROTATE_INSTA,feed);
    }
}
