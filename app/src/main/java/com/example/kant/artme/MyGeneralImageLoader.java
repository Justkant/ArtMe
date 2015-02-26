package com.example.kant.artme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

public class MyGeneralImageLoader extends AsyncTask<String, Void, Bitmap> {

    ImageView toSet;

    public MyGeneralImageLoader(ImageView ProfileImage) {
        toSet = ProfileImage;
    }

    @Override
    protected Bitmap doInBackground(String... Url) {

        Bitmap bitmap = null;
        try {
            URL url = new URL(Url[0]);
            bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
       toSet.setImageBitmap(bitmap);
    }
}
