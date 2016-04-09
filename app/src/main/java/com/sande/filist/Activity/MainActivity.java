package com.sande.filist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sande.filist.DialogueFragments.AddPendDial;
import com.sande.filist.DialogueFragments.EditPendDial;
import com.sande.filist.Fragments.MainActivityFragments.About_Us;
import com.sande.filist.Fragments.MainActivityFragments.Completed;
import com.sande.filist.Fragments.MainActivityFragments.Inspiration;
import com.sande.filist.Fragments.MainActivityFragments.Pending;
import com.sande.filist.Interfaces.AdaptersMainCallbackInterface;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.PendingDB;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AdaptersMainCallbackInterface {

    private static final int ADD_DETAILS_TO_ITEM = 33;
    private int frag;
    private int onFrag;
    private FloatingActionButton fab;
    private Fragment mFrag;
    private boolean isFirst=true;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager mFragManager = getSupportFragmentManager();
                    AddPendDial mAdd = new AddPendDial();
                    mAdd.show(mFragManager, "Dialogue");
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }

        if (savedInstanceState != null) {
            mFrag = getSupportFragmentManager().getFragment(savedInstanceState, "confgFragm");
            onFrag = savedInstanceState.getInt("onFrag");
            changeFrag(onFrag, mFrag);
        } else {
            onFrag = 0;
            mFrag = new Pending();
            changeFrag(0, mFrag);
        }
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                mFrag = getSupportFragmentManager().findFragmentByTag("visible_frag");
                if (mFrag instanceof Pending) {
                    setTopBar(0);
                    onFrag = 0;
                    fab.show();
                } else if (mFrag instanceof Completed) {
                    setTopBar(1);
                    onFrag = 1;
                    fab.hide();
                } else if (mFrag instanceof Inspiration) {
                    setTopBar(2);
                    onFrag = 2;
                    fab.hide();
                } else {
                    setTopBar(3);
                    onFrag = 3;
                    fab.hide();
                }
                assert navigationView != null;
                navigationView.getMenu().getItem(onFrag).setChecked(true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_pending) {
            frag = 0;
            mFrag = new Pending();
            fab.show();
        } else if (id == R.id.nav_completed) {
            frag = 1;
            mFrag = new Completed();
            fab.hide();
        } else if (id == R.id.nav_inspire) {
            frag = 2;
            mFrag = new Inspiration();
            fab.hide();
        } else if (id == R.id.nav_aboutus) {
            frag = 3;
            mFrag = new About_Us();
            fab.hide();
        }
        changeFrag(frag, mFrag);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void compCallViewCompletedActivity(long dateCompleted) {
        Intent viewComp=new Intent(this,ViewCompleted.class);
        viewComp.putExtra(ViewCompleted.CALL_FRAGS,dateCompleted);
        startActivity(viewComp);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        toggle.syncState();
    }

    private void changeFrag(int frag, Fragment mFrag) {
        if (frag == onFrag && !isFirst) {
            return;
        }
        if(isFirst){
            isFirst=false;
        }
        onFrag = frag;
        FragmentManager mFragMan = getSupportFragmentManager();
        FragmentTransaction mFragTrans = mFragMan.beginTransaction();
        mFragTrans.setCustomAnimations(0, 0, R.anim.pop_enter, R.anim.pop_exit);
        mFragTrans.replace(R.id.fl_cm, mFrag, "visible_frag");
        if (frag != 0) {
            mFragTrans.addToBackStack(null);
        } else {
            mFragMan.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
        setTopBar(frag);
        mFragTrans.commit();
    }

    private void setTopBar(int frag) {
        switch (frag) {
            case 0:
                setTitle("Pending");
                break;
            case 1:
                setTitle("Completed");
                break;
            case 2:
                setTitle("Inspiration");
                break;
            case 3:
                setTitle("About Us");
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mFrag.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "confgFragm", mFrag);
            outState.putInt("onFrag", onFrag);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        toggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void callEditDialog(PendingDB pendingObj) {
        FragmentManager mFragManager = getSupportFragmentManager();
        EditPendDial epd=EditPendDial.getInstance(pendingObj);
        epd.show(mFragManager, "Dialogue");
    }

    @Override
    public void callDataChangedPending() {
        ((Pending)mFrag).callNotifyUpd();
    }

    @Override
    public void callAddDetailsActivity(long dateAdded) {
        Intent mInte=new Intent(this, AddDetailsActivity.class);
        mInte.putExtra(AddDetailsActivity.CALL_ADD_DETAILS,dateAdded);
        startActivityForResult(mInte,ADD_DETAILS_TO_ITEM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ADD_DETAILS_TO_ITEM && resultCode== Activity.RESULT_OK){
            changeFrag(1,new Completed());
        }
    }
}
