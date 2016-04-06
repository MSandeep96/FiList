package com.sande.filist.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sande.filist.R;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Sandeep on 06-Apr-16.
 */
public class ADAImageAdapter extends RecyclerView.Adapter<ADAImageAdapter.ImageAdaViewHolder>{

    private ArrayList<Uri> mImages=new ArrayList<>();
    private Context mContext;
    LayoutInflater mLI;
    public ADAImageAdapter(Context context) {
        mContext=context;
        mLI=LayoutInflater.from(context);
    }

    public void update(ArrayList<Uri> newImages){
        mImages.addAll(newImages);
    }

    public ArrayList<Uri> getFinalImages(){
        return mImages;
    }

    @Override
    public ImageAdaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item=mLI.inflate(R.layout.ada_image_layout,parent,false);
        return new ImageAdaViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ImageAdaViewHolder holder, int position) {
        File mImFile=new File(mImages.get(position).getPath());
        if(mImFile!=null) {
            Glide.with(mContext).load(mImFile).centerCrop().into(holder.mImaView);
        }else{
            //placeholder
        }
    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public static class ImageAdaViewHolder extends RecyclerView.ViewHolder{
        ImageView mImaView;
        public ImageAdaViewHolder(View itemView) {
            super(itemView);
            mImaView=(ImageView)itemView.findViewById(R.id.iv_rv_ada);
        }
    }
}