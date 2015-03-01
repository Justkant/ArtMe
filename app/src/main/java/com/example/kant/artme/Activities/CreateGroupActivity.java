package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kant.artme.ArtmeAPI.ApiReturn;
import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 01/03/2015.
 */
public class CreateGroupActivity extends ActionBarActivity {

    private Group mGroup = new Group();
    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

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

        mGroup.title = ((EditText) findViewById(R.id.group_title)).getText().toString();

        ((Button) findViewById(R.id.send_grp)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //API
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.base_url))
                        .build();
                api = restAdapter.create(ArtmeAPI.class);
                api.crtGroup(MySharedPreferences.readToPreferences(getApplicationContext(), getString(R.string.token_string), ""), "{\"title\":\"bite\"}", new Callback<ApiReturn>() {
                    @Override
                    public void success(ApiReturn ret, Response response) {
                        Toast.makeText(CreateGroupActivity.this, "GROUP CREATION OK", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("FAIL", retrofitError.getMessage());
                    }
                });
            }
        });

    }
}
