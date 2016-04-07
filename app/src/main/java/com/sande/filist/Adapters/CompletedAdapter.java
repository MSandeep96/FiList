package com.sande.filist.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    public CompletedAdapter(Context context, RealmResults<CompletedDB> objs) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mResults = objs;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = mInflater.inflate(R.layout.completed_list_item, parent, false);
        return new ComViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ComViewHolder) {
            File firstImg = new File(Uri.parse(mResults.get(position).firstImage).getPath());
            //// TODO: 07-Apr-16 remove if else and add placeholder
            if (firstImg == null) {
                Glide.with(mContext).load(firstImg).centerCrop().into(((ComViewHolder) holder).mImageView);
            }
            ((ComViewHolder) holder).mTitleView.setText(mResults.get(position).comTitle);
            ((ComViewHolder) holder).mDateComp.setText(mResults.get(position).getComDateFormatted());
            ((ComViewHolder) holder).mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //// TODO: 07-Apr-16 Add another activity!
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
