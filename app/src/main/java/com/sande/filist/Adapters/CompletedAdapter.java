package com.sande.filist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sande.filist.Interfaces.AdaptersMainCallbackInterface;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;

import java.io.File;

import io.realm.RealmResults;


/**
 * Created by Sandeep on 07-Apr-16.
 */
public class CompletedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater mInflater;
    private RealmResults<CompletedDB> mResults;
    private AdaptersMainCallbackInterface mActInterfaced;

    public CompletedAdapter(Context context, RealmResults<CompletedDB> objs) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mResults = objs;
        mActInterfaced=(AdaptersMainCallbackInterface)context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.completed_list_item, parent, false);
        return new ComViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ComViewHolder) {
            File firstImg = new File(mResults.get(position).firstImage);
            //// TODO: 07-Apr-16 remove if else and add placeholder
            if (firstImg != null) {
                Glide.with(mContext).load(firstImg).centerCrop().into(((ComViewHolder) holder).mImageView);
            }
            ((ComViewHolder) holder).mTitleView.setText(mResults.get(position).comTitle);
            ((ComViewHolder) holder).mDateComp.setText(mResults.get(position).getComDateFormatted());
            ((ComViewHolder) holder).mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActInterfaced.compCallViewCompletedActivity(mResults.get(position).comTimeComp);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class ComViewHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        TextView mTitleView;
        TextView mDateComp;
        Button mButton;

        public ComViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.iv_cli);
            mTitleView = (TextView) itemView.findViewById(R.id.title_tv_cli);
            mDateComp = (TextView) itemView.findViewById(R.id.completedDate_tv_cli);
            mButton = (Button) itemView.findViewById(R.id.btn_cli);
        }
    }
}
