package com.example.kant.artme;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.LruCache;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.Drawer.DrawerAdapter;
import com.example.kant.artme.Drawer.DrawerRawInfo;
import com.example.kant.artme.Tabs.ManageEventFragment;
import com.example.kant.artme.Tabs.PostFragment;
import com.example.kant.artme.Tabs.RecyclerViewScrollListener;
import com.example.kant.artme.Tabs.ResearchFragment;
import com.example.kant.artme.Tabs.UpcomingEventFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Quentin on 23/01/2015.
 * EpiAndroid Project.
 */

public class BaseActivity extends ActionBarActivity implements DrawerAdapter.ClickListener {

    protected static final int RESEARCH_ID = 0;
    protected static final int POST_ID = 1;
    protected static final int MANAGE_ID = 2;
    protected static final int UPCOMING_ID = 3;
    protected static final int SETTINGS_ID = 4;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Toolbar toolbar;
    private Handler mHandler;
    protected LruCache<String, Bitmap> mMemoryCache;
    private ImageView profileImage;
    private RecyclerViewScrollListener recyclerScrollListener;
    private Fragment currentFragment = null;
    private int currentFragmentId = -1;
    private RestAdapter restAdapter;
    private ArtmeAPI api;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        mHandler = new Handler();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
        mMemoryCache = ((MyApplication) getApplication()).mMemoryCache;

        //API
        restAdapter = new RestAdapter.Builder()
                .setEndpoint(getString(R.string.base_url))
                .build();
        api = restAdapter.create(ArtmeAPI.class);
        Log.d("TEST ===>", "BITE");
        api.userGet(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                Log.d("TEST ===>", user.last_name + user.first_name);

                TextView usernametext = (TextView) findViewById(R.id.usernameText);
                usernametext.setText(user.last_name + " " + user.first_name + "\n");
                ImageView userpic = (ImageView) findViewById(R.id.profile_image);
                //TODO ADD PIC USER !
                userpic.setImageResource(R.drawable.pika);
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.d("FAIL", retrofitError.getMessage());
            }
        });

    }

    protected void setupDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (mDrawerLayout == null) {
            return;
        }

        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                toolbar, 0, 0);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }
        });

        profileImage = (ImageView) findViewById(R.id.profile_image);
        updateUserInfos();
        setImageProfileClickListener(MySharedPreferences.readToPreferences(this, "userLogin", ""));
        if (mMemoryCache.get("userPicture") != null)
            profileImage.setImageBitmap(mMemoryCache.get("userPicture"));
        else
            profileImage.setImageResource(R.drawable.profile);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.drawer_recycler);

        DrawerAdapter mDrawerAdapter = new DrawerAdapter(this,
                getDrawerData(this));
        mDrawerAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mDrawerAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    protected void setImageProfileClickListener(final String login) {
         //TODO VERIF !!
        //if (login.length() == 0)
         //   return;
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startProfileActivity(login);
            }
        });

    }

    private void startProfileActivity(String login) {
         Intent intent = new Intent(this, ProfilActivity.class);
         intent.putExtra("profileLogin", login);
         startActivity(intent);
    }

    protected void updateUserPhoto() {
        if (mMemoryCache.get("userPicture") == null && MySharedPreferences.readToPreferences(this, "userPhoto", "").length() > 0) {
            new MyImageLoader(mMemoryCache, (ImageView) findViewById(R.id.profile_image))
                    .execute(MySharedPreferences.readToPreferences(this, "userPhoto", ""));
        }
    }

    protected void updateUserInfos() {
        if (MySharedPreferences.readToPreferences(this, "hasUserInfos", "").equals("y")) {
            TextView usernametext = (TextView) findViewById(R.id.usernameText);
            usernametext.setText(MySharedPreferences.readToPreferences(this, "userName",
                    getString(R.string.username_textview)));

        }
    }

    private static List<DrawerRawInfo> getDrawerData(Context context) {
        List<DrawerRawInfo> data = new ArrayList<>();

        int[] icons = {
                R.drawable.ic_home_grey,
                R.drawable.ic_assignment_grey,
                R.drawable.ic_event_grey,
                R.drawable.ic_settings_grey,
                R.drawable.ic_settings_grey};
        String[] titles = context.getResources().getStringArray(R.array.drawer_strings);

        for (int i = 0; i < titles.length && i < icons.length; i++) {
            DrawerRawInfo current = new DrawerRawInfo();
            current.title = titles[i];
            current.iconId = icons[i];
            data.add(current);
        }
        return data;
    }

    protected boolean isNavDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(Gravity.START);
    }

    protected void closeNavDrawer() {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(Gravity.START);
        }
    }

    @Override
    public void onBackPressed() {
        if (isNavDrawerOpen()) {
            closeNavDrawer();
        } else {
            super.onBackPressed();
        }
    }

    public RecyclerViewScrollListener getRecyclerScrollListener() {
        if (recyclerScrollListener == null)
            recyclerScrollListener = new RecyclerViewScrollListener(getSupportActionBar());
        return recyclerScrollListener;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        getActionBarToolbar();
    }

    public Toolbar getActionBarToolbar() {
        if (toolbar == null) {
            toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
            if (toolbar != null) {
                setSupportActionBar(toolbar);
            }
        }
        return toolbar;
    }

    @Override
    public void itemClicked(final int position) {
        if (position == currentFragmentId) {
            mDrawerLayout.closeDrawer(Gravity.START);
            return;
        }

        if (isSpecialItem(position)) {
            goToNavDrawerItem(position, 0);
        } else {
            currentFragmentId = position;
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToNavDrawerItem(position, 0);
                }
            }, 250);
            setSelectedNavDrawerItem(position);
        }

        mDrawerLayout.closeDrawer(Gravity.START);
    }

    // TODO: implement function
    private void setSelectedNavDrawerItem(int position) {
    }

    protected void goToNavDrawerItem(int position, final int tabId) {
        Intent intent;

        if (currentFragment != null)
            getSupportFragmentManager().beginTransaction()
                    .remove(currentFragment)
                    .commit();

        switch (position) {
            case RESEARCH_ID:
                currentFragment = new ResearchFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, currentFragment)
                        .commit();
                break;
            case POST_ID:
                currentFragment = new PostFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, currentFragment)
                        .commit();
                break;
            case MANAGE_ID:
                currentFragment = new ManageEventFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, currentFragment)
                        .commit();
                break;
            case UPCOMING_ID:
                currentFragment = new UpcomingEventFragment();
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, currentFragment)
                        .commit();
                break;
            case SETTINGS_ID:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }

    private boolean isSpecialItem(int position) {
        return position == SETTINGS_ID;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupDrawer();
    }
}
