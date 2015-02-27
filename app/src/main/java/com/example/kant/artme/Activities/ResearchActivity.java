package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.MyImageLoader;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 13/02/2015.
 */
public class ResearchActivity extends BaseActivity implements ResearchAdapter.ClickListener {

    private List<Event> adapterData = new ArrayList<>();
    private ResearchAdapter mResearchAdapter;
    private RestAdapter restAdapter;
    private ArtmeAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_board);

        getActionBarToolbar().setTitle(R.string.title_activity_research);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mResearchAdapter = new ResearchAdapter(this, adapterData);
        mResearchAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mResearchAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //API
        if (MySharedPreferences.readToPreferences(this, getString(R.string.token_string), "") != "") {
            restAdapter = new RestAdapter.Builder()
                    .setEndpoint(getString(R.string.base_url))
                    .build();
            api = restAdapter.create(ArtmeAPI.class);
            api.getEvents(new Callback<List<Event>>() {
                @Override
                public void success(List<Event> events, Response response) {
                    for (int i = 0; i < events.size() ; ++i) {
                        adapterData.add(events.get(i));
                    }
                    mResearchAdapter.notifyDataSetChanged();
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.d("RESEARCH RETROFIT ERROR ===>", retrofitError.getMessage());
                }
            });

        }
    }

    protected int getSelfNavDrawerItem() {
        return RESEARCH_ID;
    }


    @Override
    public void reserchItemClicked(int position) {
        Intent intent = new Intent(this, EventItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

}
