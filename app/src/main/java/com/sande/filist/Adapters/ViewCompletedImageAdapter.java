package com.sande.filist.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.sande.filist.Fragments.ViewCompletedFragments.ImagesCompletedFragment;
import com.sande.filist.R;

import java.util.ArrayList;

/**
 * Created by Sandeep on 08-Apr-16.
 */
public class ViewCompletedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements android.view.ActionMode.Callback{
    private Context mContext;
    private ArrayList<String> mImgs;
    private LayoutInflater mLay;
    public static boolean selectable=false;
    private SparseBooleanArray selectedItems=new SparseBooleanArray();

    public ViewCompletedImageAdapter(Context context, ArrayList<String> imgs) {
        mContext=context;
        mImgs=imgs;
        mLay=LayoutInflater.from(context);
    }

    public void setSelectable(boolean se){
        selectable=se;
    }

    public void changeActionBar(){
        ((Activity)mContext).startActionMode(this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View getV=mLay.inflate(R.layout.view_completed_image_item,parent,false);
        return new ViewCompletedImageHolder(getV);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewCompletedImageHolder){
            Glide.with(mContext).load(mImgs.get(position)).centerCrop().into(((ViewCompletedImageHolder) holder).mImageView);
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(!selectable) {
                        selectable = true;
                        changeActionBar();
                        ((ViewCompletedImageHolder) holder).mLinear.setSelected(true);
                        selectedItems.put(position,true);
                    }
                    return true;
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(selectable){
                        if(selectedItems.get(position,false)){
                            selectedItems.put(position,false);
                            ((ViewCompletedImageHolder) holder).mLinear.setSelected(false);
                        }else {
                            selectedItems.put(position, true);
                            ((ViewCompletedImageHolder) holder).mLinear.setSelected(true);
                        }
                    }
                }
            });
            if(!selectable){
                ((ViewCompletedImageHolder) holder).mLinear.setSelected(false);
            }
        }

    }

    @Override
    public int getItemCount() {
        return mImgs.size();
    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
        MenuInflater inflater=mode.getMenuInflater();
        inflater.inflate(R.menu.contextual_action_image_adapter,menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_del:
                for(int i=0;i<mImgs.size();i++){
                    if(selectedItems.get(i,false)){
                        mImgs.remove(i);
                    }
                }
                mode.finish();
                break;
            case R.id.menu_share:
                ArrayList<Uri> mShare=new ArrayList<>();;
                for(int i=0;i<mImgs.size();i++){
                    if(selectedItems.get(i,false)){
                        mShare.add(Uri.parse(mImgs.get(i)));
                    }
                }
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND_MULTIPLE);
                intent.setType("image/*");
                intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, mShare);
                mContext.startActivity(intent);
                mode.finish();
                break;
        }
        return true;
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {
        selectable=false;
        notifyDataSetChanged();
    }

    public static class ViewCompletedImageHolder extends RecyclerView.ViewHolder {
        ImageView mImageView;
        LinearLayout mLinear;
        public ViewCompletedImageHolder(final View itemView) {
            super(itemView);
            mImageView=(ImageView)itemView.findViewById(R.id.iv_vcii);
            mLinear=(LinearLayout)itemView.findViewById(R.id.ll_vcii);
        }
    }
}
