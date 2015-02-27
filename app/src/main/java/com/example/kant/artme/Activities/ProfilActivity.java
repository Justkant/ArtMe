package com.example.kant.artme.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.MyApplication;
import com.example.kant.artme.MyImageLoader;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

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

    private User currentUser;
    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private Toolbar toolbar;
    private List<Group> adapterData = new ArrayList<>();
    public Bitmap btm;
    protected LruCache<String, Bitmap> mMemoryCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        mMemoryCache = ((MyApplication) getApplication()).mMemoryCache;

        currentUser = (User) getIntent().getSerializableExtra("user");
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

        //GROUP
        LinearLayout mLinearLayout = (LinearLayout) findViewById(R.id.groups);
        for (int i = 0; i < 3; ++i) {
            LinearLayout newLinear = (LinearLayout) View.inflate(this, R.layout.group_row, null);
            ((ImageView) newLinear.findViewById(R.id.group_pic)).setImageResource(R.drawable.pika);
            ((TextView) newLinear.findViewById(R.id.group_title)).setText("TEST");
            mLinearLayout.addView(newLinear);
        }

        //USER NAME
        TextView usernametext = (TextView) findViewById(R.id.username);
        usernametext.setText(currentUser.username + "\n");
        //USER PIC
        ImageView userpic = (ImageView) findViewById(R.id.profile_pic);
        if (mMemoryCache.get("userPicture") != null)
            userpic.setImageBitmap(mMemoryCache.get("userPicture"));
        else
            userpic.setImageResource(R.drawable.profile);
        //USER DESC
        TextView desc = (TextView) findViewById(R.id.desc);
        desc.setText(currentUser.description);

        ((Button) findViewById(R.id.passed)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(getApplicationContext(), ManageEventActivity.class);
                intent2.putExtra("user", currentUser);
                intent2.putExtra("typeEvent", 2);
                startActivity(intent2);
                finish();
            }
        });

    }

}
