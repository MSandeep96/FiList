package com.sande.filist.Fragments.MainActivityFragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sande.filist.Network.VolleySingleton;
import com.sande.filist.R;
import com.sande.filist.Utils.APIConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private VolleySingleton iVolSing;
    private RequestQueue iReqQue;
    private Context mContext;

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
        iVolSing= VolleySingleton.getInstance();
        iReqQue=iVolSing.getRequestQueue();
        JsonObjectRequest req=new JsonObjectRequest(Request.Method.GET, getReqUrl(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                parseJsonObject(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        iReqQue.add(req);
    }

    private void parseJsonObject(JSONObject response) {
        StringBuilder data=new StringBuilder();
        if(response==null || response.length()==0){
            return;
        }
        try {
            if(response.has(KEY_DATA)) {
                JSONArray arrayData = response.getJSONArray(KEY_DATA);
                for (int i=0;i<arrayData.length();i++){
                    JSONObject currentImage=arrayData.getJSONObject(i);
                    String filter=currentImage.getString("filter");
                    data.append(filter+"\n");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Toast.makeText(mContext,data.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inspiration, container, false);
    }

    private String getReqUrl(){
        //// TODO: 09-Apr-16 Maybe spanner with other tags
        String url=URL_INSTA.replace("{}","bucketlist");//tag to find in instagram
        url+=ACCESS_TOKEN;
        return url;
    }

}
