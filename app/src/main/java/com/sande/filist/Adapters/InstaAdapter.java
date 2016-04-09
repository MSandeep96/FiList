package com.sande.filist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.sande.filist.Network.VolleySingleton;
import com.sande.filist.Pojo.InstaFeed;
import com.sande.filist.R;

import java.util.ArrayList;

/**
 * Created by Sandeep on 09-Apr-16.
 */
public class InstaAdapter extends RecyclerView.Adapter<InstaAdapter.InstaViewHolder>{
    Context mContext;
    ArrayList<InstaFeed> mFeed;
    LayoutInflater mInflater;
    VolleySingleton mVolley;
    ImageLoader mImageLoader;

    public InstaAdapter(Context context) {
        mContext=context;
        mInflater=LayoutInflater.from(context);
        mVolley=VolleySingleton.getInstance();
        mImageLoader=mVolley.getImageLoader();
    }

    public void setmFeed(ArrayList<InstaFeed> mFeed) {
        this.mFeed=mFeed;
        notifyItemRangeChanged(0,mFeed.size());
    }

    @Override
    public InstaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View thisView=mInflater.inflate(R.layout.insta_feed_layout,parent,false);
        return new InstaViewHolder(thisView);
    }

    @Override
    public void onBindViewHolder(final InstaViewHolder holder, int position) {
        holder.mUser.setText(mFeed.get(position).getUserName());
        holder.mCaption.setText(mFeed.get(position).getCaption());
        String urlProfPic=mFeed.get(position).getProfPic();
        String urlImage=mFeed.get(position).getImageStandard();
        mImageLoader.get(urlProfPic, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.mProf.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        mImageLoader.get(urlImage, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.mImage.setImageBitmap(response.getBitmap());
            }

            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mFeed==null){
            return 0;
        }
        return mFeed.size();
    }

    static class InstaViewHolder extends RecyclerView.ViewHolder{
        ImageView mProf;
        TextView mUser;
        ImageView mImage;
        TextView mCaption;

        public InstaViewHolder(View itemView) {
            super(itemView);
            mProf=(ImageView)itemView.findViewById(R.id.prof_iv_in);
            mUser=(TextView)itemView.findViewById(R.id.username_tv_in);
            mImage=(ImageView)itemView.findViewById(R.id.image_iv_in);
            mCaption=(TextView)itemView.findViewById(R.id.caption_tv_in);
        }
    }
}
