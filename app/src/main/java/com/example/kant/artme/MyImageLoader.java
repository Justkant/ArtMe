package com.example.kant.artme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * Created by Quentin on 30/01/2015.
 * EpiAndroid Project.
 */

public class MyImageLoader extends AsyncTask<String, Void, Bitmap> {

    LruCache<String, Bitmap> mMemoryCache;
    ImageView profileImage;

    public MyImageLoader(LruCache<String, Bitmap> MemoryCache, ImageView ProfileImage) {
        profileImage = ProfileImage;
        mMemoryCache = MemoryCache;
    }

    @Override
    protected Bitmap doInBackground(String... Url) {

        Bitmap bitmap = null;
        try {
            URL url = new URL(Url[0]);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
            mMemoryCache.put("userPicture", bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        profileImage.setImageBitmap(bitmap);
    }
}