package com.example.kant.artme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.User;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginActivity extends ActionBarActivity {

    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private EditText mLogin;
    private EditText mPassword;
    private Button mBtnLogin;
    private boolean isLoginValide;
    private boolean isPasswordValide;
    private Toolbar toolbar;
    private User postLogin = new User();

    private void resetCache() {
        MySharedPreferences.clearPreferences(this);
        //mMemoryCache.remove("userPicture");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_activity_connexion);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mLogin = (EditText) findViewById(R.id.loginText);
        mPassword = (EditText) findViewById(R.id.passwordText);
        mBtnLogin = (Button) findViewById(R.id.loginButton);

        mLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateLogin(s.toString());
                updateLoginBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword(s.toString());
                updateLoginBtnState();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postLogin.username = mLogin.getText().toString();
                postLogin.password = mPassword.getText().toString();
                mBtnLogin.setEnabled(false);
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.base_url))
                        .build();

                api = restAdapter.create(ArtmeAPI.class);
                api.login(postLogin, new Callback<String>() {
                    @Override
                    public void success(String token, Response response) {
                        MySharedPreferences.saveToPreferences(getBaseContext(), getString(R.string.token_string), token);
                        startActivity(new Intent(getBaseContext(), BaseActivity.class));
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("FAIL", retrofitError.getMessage());
                        mPassword.setText("");
                        mPassword.setError(getString(R.string.login_error));
                    }
                });
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        resetCache();
    }

    private void validateLogin(String text) {
        isLoginValide = !text.isEmpty();
    }

    private void validatePassword(String text) {
        isPasswordValide = !text.isEmpty();
    }

    private void updateLoginBtnState() {
        if (isLoginValide && isPasswordValide) {
            mBtnLogin.setEnabled(true);
        } else {
            mBtnLogin.setEnabled(false);
        }
    }
}
