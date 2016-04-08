package com.sande.filist.RecyclerViewDecorations;

import android.graphics.Canvas;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.LinearLayout;

import com.sande.filist.Adapters.CompletedAdapter;
import com.sande.filist.Adapters.PendingAdapter;
import com.sande.filist.Interfaces.SwipeableLeft;
import com.sande.filist.RealmClasses.PendingDB;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by Sandeep on 31-Mar-16.
 */
public class SwipeLeft extends ItemTouchHelper.Callback {
    private SwipeableLeft mSwipedLeft;

    public SwipeLeft(SwipeableLeft swipedLeft) {
        mSwipedLeft=swipedLeft;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0,ItemTouchHelper.START);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(viewHolder instanceof PendingAdapter.PendingVH || viewHolder instanceof CompletedAdapter.ComViewHolder){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (viewHolder instanceof PendingAdapter.PendingVH || viewHolder instanceof CompletedAdapter.ComViewHolder){
            super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        if (viewHolder instanceof PendingAdapter.PendingVH || viewHolder instanceof CompletedAdapter.ComViewHolder){
            mSwipedLeft.swipedLeft(viewHolder.getLayoutPosition());
        }

    }
}