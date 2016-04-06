package com.sande.filist.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sande.filist.Activity.AddDetailsActivity;
import com.sande.filist.Interfaces.callEditTitleDialog;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;

import org.w3c.dom.Text;

import java.util.zip.Inflater;

import io.realm.RealmResults;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class PendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    callEditTitleDialog mActInterfaced;
    Context mContext;
    LayoutInflater mInflater;
    private RealmResults<PendingDB> mResults;
    public PendingAdapter(Context context,RealmResults<PendingDB> results) {
        mContext=context;
        mActInterfaced=(callEditTitleDialog)context;
        mInflater= LayoutInflater.from(context);
        mResults=results;
    }

    public void update(RealmResults<PendingDB> newResults){
        mResults=newResults;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mItem=mInflater.inflate(R.layout.pending_list_item,parent,false);
        return new PendingVH(mItem);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof PendingVH){
            ((PendingVH)holder).titleTV.setText(mResults.get(position).getTitle());
            ((PendingVH)holder).dateTV.setText(mResults.get(position).getDateFormatted());
            ((PendingVH)holder).editIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActInterfaced.callETD(mResults.get(position));
                }
            });
            ((PendingVH)holder).comIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mInte=new Intent(mContext, AddDetailsActivity.class);
                    mInte.putExtra("longTime",mResults.get(position).getDateAdded());
                    mContext.startActivity(mInte);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public static class PendingVH extends RecyclerView.ViewHolder {
        TextView titleTV;
        ImageButton comIB;
        ImageButton editIB;
        TextView dateTV;
        public PendingVH(View itemView) {
            super(itemView);
            titleTV=(TextView)itemView.findViewById(R.id.tv_title_pli);
            comIB=(ImageButton)itemView.findViewById(R.id.ib_com_pli);
            editIB=(ImageButton)itemView.findViewById(R.id.ib_edit_pli);
            dateTV=(TextView)itemView.findViewById(R.id.tv_time_pli);
        }
    }
}
