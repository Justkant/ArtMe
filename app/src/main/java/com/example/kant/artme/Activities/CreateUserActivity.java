package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.MyImageLoader;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 25/02/2015.
 */
public class CreateUserActivity extends ActionBarActivity {

    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private User postUser = new User();
    private EditText mUsername;
    private EditText mPassword;
    private EditText mMail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
            ((Button) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mUsername = (EditText) findViewById(R.id.username);
                    mPassword = (EditText) findViewById(R.id.password);
                    mMail = (EditText) findViewById(R.id.email);

                    postUser.username = mUsername.getText().toString();
                    postUser.password = mPassword.getText().toString();
                    postUser.email = mMail.getText().toString();

                    //API
                    restAdapter = new RestAdapter.Builder()
                            .setEndpoint(getString(R.string.base_url))
                            .build();
                    api = restAdapter.create(ArtmeAPI.class);
                    api.postUser(postUser, new Callback<String>() {
                        @Override
                        public void success(String token, Response response) {
                            MySharedPreferences.saveToPreferences(getBaseContext(), getString(R.string.token_string), token);
                            startActivity(new Intent(getBaseContext(), BaseActivity.class));
                            finish();
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
