package com.sande.filist.Activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;
import com.sande.filist.Fragments.ViewCompletedFragments.DescriptionFragment;
import com.sande.filist.Fragments.ViewCompletedFragments.ImagesCompletedFragment;
import com.sande.filist.Fragments.ViewCompletedFragments.TaskFragment;
import com.sande.filist.R;

public class ViewCompleted extends AppCompatActivity {

    private BottomBar mBottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_completed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final long dateAdded=getIntent().getLongExtra("callCompeted",0);

        mBottomBar = BottomBar.attach(this, savedInstanceState);
        mBottomBar.setItemsFromMenu(R.menu.bottombar_menu, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int resId) {
                Fragment mFrag;
                if (resId == R.id.bb_task) {
                    mFrag=TaskFragment.newInstance(dateAdded);
                }else if(resId==R.id.bb_desc){
                    mFrag=new ImagesCompletedFragment();
                }else{
                    mFrag=new DescriptionFragment();
                }
                FragmentManager mFragMan=getSupportFragmentManager();
                FragmentTransaction mFragTrans=mFragMan.beginTransaction();
                mFragTrans.replace(R.id.fl_vc,mFrag,"completed_frag");
                mFragTrans.commit();
            }

            @Override
            public void onMenuTabReSelected(@IdRes int resId) {
                //// TODO: 08-Apr-16 Add appr stuff here
                if (resId == R.id.bb_task) {
                    // The user reselected item number one, scroll your content to top.
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBottomBar.onSaveInstanceState(outState);
    }
}
