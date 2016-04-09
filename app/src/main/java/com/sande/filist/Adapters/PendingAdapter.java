package com.sande.filist.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sande.filist.Interfaces.AdaptersMainCallbackInterface;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;

import io.realm.RealmResults;

/**
 * Created by Sandeep on 30-Mar-16.
 */
public class PendingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    AdaptersMainCallbackInterface mActInterfaced;
    Context mContext;
    LayoutInflater mInflater;
    private RealmResults<PendingDB> mResults;
    public PendingAdapter(Context context,RealmResults<PendingDB> results) {
        mContext=context;
        mActInterfaced=(AdaptersMainCallbackInterface)context;
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
            ((PendingVH)holder).titleTV.setText(mResults.get(position).task);
            ((PendingVH)holder).dateTV.setText(mResults.get(position).getDateFormatted());
            ((PendingVH)holder).editIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActInterfaced.callEditDialog(mResults.get(position));
                }
            });
            ((PendingVH)holder).comIB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActInterfaced.callAddDetailsActivity(mResults.get(position).dateAdded);
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
