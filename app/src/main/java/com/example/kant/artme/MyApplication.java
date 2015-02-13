package com.example.kant.artme;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Created by Quentin on 30/01/2015.
 * EpiAndroid Project.
 */
public class MyApplication extends Application {

    public final LruCache<String, Bitmap> mMemoryCache;

    public MyApplication() {
        super();
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getByteCount() / 1024;
            }
        };
    }
}
