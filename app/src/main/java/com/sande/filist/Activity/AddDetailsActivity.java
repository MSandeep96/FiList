package com.sande.filist.Activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.gun0912.tedpicker.Config;
import com.gun0912.tedpicker.ImagePickerActivity;
import com.sande.filist.Adapters.ADAImageAdapter;
import com.sande.filist.Fragments.Completed;
import com.sande.filist.R;
import com.sande.filist.RealmClasses.CompletedDB;
import com.sande.filist.RealmClasses.PendingDB;

import java.util.ArrayList;

import io.realm.Realm;

public class AddDetailsActivity extends AppCompatActivity {

    private static final int INTENT_REQUEST_GET_IMAGES = 22;
    private static final String SAVE_ARRAYLIST_OF_IMAGES = "SAVEIMAGES";
    private Realm mRealm;
    private PendingDB pendObj;
    private EditText titleEditText;
    private EditText descEditText;
    private ImageButton mImageBut;
    private RecyclerView mRecView;
    private ADAImageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Details");
        Intent inte=getIntent();
        long ident=inte.getLongExtra("longTime",0);
        mRealm=Realm.getDefaultInstance();
        pendObj=mRealm.where(PendingDB.class).equalTo("dateAdded",ident).findFirst();
        titleEditText=(EditText)findViewById(R.id.et_title_ada);
        descEditText=(EditText)findViewById(R.id.et_desc_ada);
        mImageBut=(ImageButton)findViewById(R.id.ib_ada);
        mRecView=(RecyclerView)findViewById(R.id.rv_ada);
        mRecView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        mAdapter=new ADAImageAdapter(this);
        if(savedInstanceState!=null){
            mAdapter.update(savedInstanceState.<Uri>getParcelableArrayList(SAVE_ARRAYLIST_OF_IMAGES));
        }
        mRecView.setAdapter(mAdapter);
        mRecView.setHasFixedSize(true);
        mRecView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            this.setResult(Activity.RESULT_CANCELED);
            finish();
            return true;
        }else if (item.getItemId()==R.id.addToComp){
            mRealm.beginTransaction();
            long timeCompl=System.currentTimeMillis();
            String title=titleEditText.getText().toString();
            String desc=descEditText.getText().toString();
            CompletedDB comObj=new CompletedDB(title,desc,pendObj,timeCompl);
            comObj.setImageUris(mAdapter.getFinalImages());
            mRealm.copyToRealmOrUpdate(comObj);
            pendObj.removeFromRealm();
            mRealm.commitTransaction();
            this.setResult(Activity.RESULT_OK);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resuleCode, Intent intent) {
        super.onActivityResult(requestCode, resuleCode, intent);

        if (requestCode == INTENT_REQUEST_GET_IMAGES && resuleCode == Activity.RESULT_OK ) {

            ArrayList<Uri> image_uris = intent.getParcelableArrayListExtra(ImagePickerActivity.EXTRA_IMAGE_URIS);
            mAdapter.update(image_uris);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void addImages(View v){
        Config mConf=new Config();
        mConf.setSelectionLimit(20);
        ImagePickerActivity.setConfig(mConf);
        Intent intent = new Intent(this, ImagePickerActivity.class);
        startActivityForResult(intent, INTENT_REQUEST_GET_IMAGES);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_ARRAYLIST_OF_IMAGES,mAdapter.getFinalImages());
    }
}
