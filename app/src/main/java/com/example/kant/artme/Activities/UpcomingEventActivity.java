package com.example.kant.artme.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class UpcomingEventActivity extends BaseActivity implements UpcomingEventAdapter.ClickListener {

    private List<Event> adapterData = new ArrayList<>();
    private UpcomingEventAdapter mUpcomingEventAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_board);

        getActionBarToolbar().setTitle(R.string.title_activity_upcoming_event);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mUpcomingEventAdapter = new UpcomingEventAdapter(this, adapterData);
        mUpcomingEventAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mUpcomingEventAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());

        Event event1 = new Event();
        event1.title = "test1";
        Event event2 = new Event();
        event2.title = "test2";
        adapterData.add(event1);
        adapterData.add(event2);
        mUpcomingEventAdapter.notifyDataSetChanged();
    }

    @Override
    public void itemClicked(int position) {

        //TODO INTENT ITEMEVENT
/*        Intent intent = new Intent(getActivity(), ProjectItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);*/
    }

    protected int getSelfNavDrawerItem() {
        return UPCOMING_ID;
    }

}