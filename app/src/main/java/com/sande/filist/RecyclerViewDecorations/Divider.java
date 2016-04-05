package com.sande.filist.RecyclerViewDecorations;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sande.filist.R;

/**
 * Created by Sandeep on 31-Mar-16.
 */
public class Divider extends RecyclerView.ItemDecoration {
    private Drawable mDivider;
    private int mOrientation;
    public Divider(Context context,int orientation){
        mDivider= ContextCompat.getDrawable(context, R.drawable.divider_list);
        if(orientation!= LinearLayoutManager.VERTICAL){
            //swallow
        }
        mOrientation=orientation;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if(mOrientation==LinearLayoutManager.VERTICAL){
            drawHorizontalDivider(c,parent,state);
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,right,top,bot;
        left=0;
        right=parent.getWidth();
        int count=parent.getChildCount();
        for(int i=0;i<count;i++){
            View current=parent.getChildAt(i);
            bot=current.getTop();
            top=bot-mDivider.getIntrinsicHeight();
            mDivider.setBounds(left,top,right,bot);
            mDivider.draw(c);
        }
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if(mOrientation==LinearLayoutManager.VERTICAL){
            outRect.set(0,mDivider.getIntrinsicHeight(),0,0);
        }
    }
}
