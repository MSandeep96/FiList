package com.sande.filist.Activity;

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
        import android.widget.Toast;

        import com.sande.filist.DialogueFragments.AddorEditDial;
        import com.sande.filist.Fragments.*;
        import com.sande.filist.R;
        import com.sande.filist.Utils.Utils;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int frag;
    private int onFrag;
    private FloatingActionButton fab;
    private Pending mPend;
    private Completed mComp;
    private Inspiration mInsp;
    private About_Us mAbus;

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
                    AddorEditDial mAdd = new AddorEditDial();
                    mAdd.show(mFragManager, "Dialogue");
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        onFrag = 2;//gets set to 0 after next instruction
        changeFrag(0, new Pending());
        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment mVisFrag = getSupportFragmentManager().findFragmentByTag("visible_frag");
                if (mVisFrag instanceof Pending) {
                    setTopBar(0);
                    onFrag = 0;
                    fab.show();
                } else if (mVisFrag instanceof Completed) {
                    setTopBar(1);
                    onFrag = 1;
                    fab.hide();
                } else if (mVisFrag instanceof Inspiration) {
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
        Fragment mFrag = null;
        if (id == R.id.nav_pending) {
            frag = 0;
            if(mPend==null){
                mPend=new Pending();
            }
            mFrag = mPend;
            fab.show();
        } else if (id == R.id.nav_completed) {
            frag = 1;
            if(mComp==null){
                mComp=new Completed();
            }
            mFrag = mComp;
            fab.hide();
        } else if (id == R.id.nav_inspire) {
            frag = 2;
            if(mInsp==null){
                mInsp=new Inspiration();
            }
            mFrag = mInsp;
            fab.hide();
        } else if (id == R.id.nav_aboutus) {
            frag = 3;
            if(mAbus==null){
                mAbus=new About_Us();
            }
            mFrag = mAbus;
            fab.hide();
        }
        changeFrag(frag, mFrag);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        return true;
    }

    private void changeFrag(int frag, Fragment mFrag) {
        if (frag == onFrag) {
            return;
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

    }
}
