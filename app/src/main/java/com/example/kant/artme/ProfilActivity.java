package com.example.kant.artme;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.ArtmeAPI.User;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 23/02/2015.
 */
public class ProfilActivity extends ActionBarActivity {

    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private Toolbar toolbar;
    private List<Group> adapterData = new ArrayList<>();
    public Bitmap btm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_activity_profile);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.groups);
        for (int i = 0; i < 3; ++i) {
            LinearLayout newLinear = (LinearLayout) View.inflate(this, R.layout.group_row, null);
            ((ImageView) newLinear.findViewById(R.id.group_pic)).setImageResource(R.drawable.pika);
            ((TextView) newLinear.findViewById(R.id.group_title)).setText("TEST");
            mLinearLayout.addView(newLinear);
        }

        //API
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.base_url))
                .build();
        api = restAdapter.create(ArtmeAPI.class);
        api.userGet(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                //USER NAME
                TextView usernametext = (TextView) findViewById(R.id.username);
                usernametext.setText(user.username + "\n");
                //USER PIC
                ImageView userpic = (ImageView) findViewById(R.id.profile_pic);
                new DownloadPicsTask(userpic).execute(user);
                //USER DESC
                TextView desc = (TextView) findViewById(R.id.desc);
                desc.setText(user.description);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("FAIL", retrofitError.getMessage());
            }
        });
        //FIN API

    }

    //ASYNC
    private class DownloadPicsTask extends AsyncTask<User, Integer, Bitmap> {

        ImageView img;

        public DownloadPicsTask(ImageView userpic) {
            img = userpic;
        }

        @Override
        protected Bitmap doInBackground(User... user) {
            Bitmap bitmap = null;
            try {
                URL networkUrl = new URL(user[0].picture_url);
                bitmap = BitmapFactory.decodeStream(
                        networkUrl.openConnection().getInputStream());
                return bitmap;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            img.setImageBitmap(result);
        }
    }

}
