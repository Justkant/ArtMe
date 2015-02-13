package com.example.kant.artme;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends BaseActivity {

    private static final String TAG = "Home Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (savedInstanceState == null) {
            /*if (MySharedPreferences.readToPreferences(this, getString(R.string.token_string), "").length() == 0) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }*/
        }

        getActionBarToolbar().setTitle(R.string.title_activity_main);
        setSupportActionBar(getActionBarToolbar());

        //TODO : ASYNC REQUEST FOR DATA

        int[] buttonIdTab = {
                R.id.modules_button,
                R.id.activities_button,
                R.id.projects_button,
                R.id.susies_button,
                R.id.marks_button,
                R.id.trombi_button
        };

        for (int i = 0; i < buttonIdTab.length; i++) {
            Button button = (Button) findViewById(buttonIdTab[i]);
            final int finalI = i;
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToNavDrawerItem(BOARD_ID, finalI);
                }
            });
        }

    }

    private void backToLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected int getSelfNavDrawerItem() {
        return HOME_ID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.notif_icon:
                // TODO : START NOTIF ACTIVITY
                // startActivity(new Intent(this, NotifActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*if (MySharedPreferences.readToPreferences(this, getString(R.string.token_string), "").length() == 0) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }*/
    }
}
