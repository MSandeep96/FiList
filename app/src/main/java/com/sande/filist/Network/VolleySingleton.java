package com.sande.filist.Network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.sande.filist.RealmInitApp;

/**
 * Created by Sandeep on 09-Apr-16.
 */
public class VolleySingleton {
    private static VolleySingleton mVolSin=null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    public VolleySingleton() {
        mRequestQueue= Volley.newRequestQueue(RealmInitApp.getAppContext());
        mImageLoader=new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private LruCache<String,Bitmap> cache=new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                cache.put(url,bitmap);
            }
        });
    }

    public static VolleySingleton getInstance() {
        if(mVolSin==null){
            mVolSin=new VolleySingleton();
        }
        return mVolSin;
    }

    public RequestQueue getRequestQueue(){
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
        return mImageLoader;
    }
}
