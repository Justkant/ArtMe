package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class ManageEventActivity extends BaseActivity implements ManageEventAdapter.ClickListener {

    private List<Event> adapterData = new ArrayList<>();
    private ManageEventAdapter mManageEventAdapter;
    private User paramUser;
    private int typeEvent;
    private final static int NEXT_EVENT = 1;
    private final static int PASS_EVENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_board);

        getActionBarToolbar().setTitle(R.string.title_activity_manage_event);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mManageEventAdapter = new ManageEventAdapter(this, adapterData);
        mManageEventAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mManageEventAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());

        //RECUP PARAMS + SET DES BONNES CARD -PASS -NEXT
        paramUser = (User) getIntent().getSerializableExtra("user");
        typeEvent = (int) getIntent().getSerializableExtra("typeEvent");
        Log.d("TAILLE NEXT EVENTS", String.valueOf(paramUser.next_events.size()));
        Log.d("NAME NEXT EVENTS", String.valueOf(paramUser.next_events.get(0).title));
        if (typeEvent == NEXT_EVENT)
        for (int i = 0; i < paramUser.next_events.size(); ++i) {
            Event nEvent = paramUser.next_events.get(i);
            adapterData.add(nEvent);
        }
        else if (typeEvent == PASS_EVENT)
            for (int i = 0; i < paramUser.past_events.size(); ++i) {
                Event nEvent = paramUser.past_events.get(i);
                adapterData.add(nEvent);
            }
        mManageEventAdapter.notifyDataSetChanged();
    }

    @Override
    public void manageItemClicked(int position) {

        //TODO INTENT ITEMEVENT
        Intent intent = new Intent(this, ManageItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

    protected int getSelfNavDrawerItem() {
        return MANAGE_ID;
    }
}