package com.example.kant.artme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.Tabs.Group;

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


        Log.d("BEFORE GROUP", "LA");
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
        Log.d("PROFIL ===>", "ACTIVITY");
        api.userGet(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                TextView usernametext = (TextView) findViewById(R.id.username);
                usernametext.setText(user.last_name + " " + user.first_name + "\n");
                ImageView userpic = (ImageView) findViewById(R.id.profile_pic);
                //TODO ADD PIC USER !
                userpic.setImageResource(R.drawable.pika);
                //TODO ADD DESC
                TextView desc = (TextView) findViewById(R.id.desc);
                desc.setText("DESCRIPTION HERE");
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("FAIL", retrofitError.getMessage());
            }
        });
        //FIN API

    }

}
