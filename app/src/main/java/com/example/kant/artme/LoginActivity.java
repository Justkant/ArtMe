package com.example.kant.artme;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.RestAdapter;

public class LoginActivity extends BaseActivity {

    private EditText mLogin;
    private EditText mPassword;
    private Button mBtnLogin;
    private boolean isLoginValide;
    private boolean isPasswordValide;

    private void resetCache() {
        MySharedPreferences.clearPreferences(this);
        mMemoryCache.remove("userPicture");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = getActionBarToolbar();
        toolbar.setSubtitle(R.string.title_activity_login);
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
                mBtnLogin.setEnabled(false);

                //TODO : ASYNC REQUEST
                startActivity(new Intent(getBaseContext(), HomeActivity.class));
                finish();
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
