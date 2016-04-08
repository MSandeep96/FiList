package com.sande.filist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sande.filist.R;

import java.util.ArrayList;

/**
 * Created by Sandeep on 08-Apr-16.
 */
public class ViewCompletedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private ArrayList<String> mImgs;
    private LayoutInflater mLay;

    public ViewCompletedImageAdapter(Context context, ArrayList<String> imgs) {
        mContext=context;
        mImgs=imgs;
        mLay=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View getV=mLay.inflate(R.layout.view_completed_image_item,parent,false);
        return new ViewCompletedImageHolder(getV);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewCompletedImageHolder){
            Glide.with(mContext).load(mImgs.get(position)).centerCrop().into(((ViewCompletedImageHolder) holder).mImageView);
        }
    }

    @Override
    public int getItemCount() {
        return mImgs.size();
    }

    public static class ViewCompletedImageHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;

        public ViewCompletedImageHolder(View itemView) {
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_vcii);
        }
    }
}
