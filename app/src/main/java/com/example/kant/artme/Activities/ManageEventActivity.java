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
import com.example.kant.artme.ArtmeAPI.Group;
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
    private static final int SUB_EVENT = 3;
    private static final int LIST_GROUPS = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_board);

        //RECUP PARAMS + SET DES BONNES CARD -PASS -NEXT
        paramUser = (User) getIntent().getSerializableExtra("user");
        typeEvent = (int) getIntent().getSerializableExtra("typeEvent");

        if (typeEvent == NEXT_EVENT)
            getActionBarToolbar().setTitle(R.string.title_activity_manage_event);
        else if (typeEvent == PASS_EVENT)
            getActionBarToolbar().setTitle(R.string.title_activity_pass_event);
        else if (typeEvent == SUB_EVENT)
            getActionBarToolbar().setTitle(R.string.title_activity_sub_event);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mManageEventAdapter = new ManageEventAdapter(this, adapterData);
        mManageEventAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mManageEventAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());

        Log.d("ITE", "ITE");
        //SET RIGHT CARDS
        if (typeEvent == NEXT_EVENT) {
            if (paramUser.next_events != null)
                for (int i = 0; i < paramUser.next_events.size(); ++i) {
                    Event nEvent = paramUser.next_events.get(i);
                    adapterData.add(nEvent);
                }
        } else if (typeEvent == PASS_EVENT) {
            if (paramUser.past_events != null)
                for (int i = 0; i < paramUser.past_events.size(); ++i) {
                    Event nEvent = paramUser.past_events.get(i);
                    adapterData.add(nEvent);
                }
        } else if (typeEvent == SUB_EVENT) {
            if (paramUser.sub_events != null)
                for (int i = 0; i < paramUser.sub_events.size(); ++i) {
                    Event nEvent = paramUser.sub_events.get(i);
                    adapterData.add(nEvent);
                }
        }
        mManageEventAdapter.notifyDataSetChanged();
    }

    @Override
    public void manageItemClicked(int position) {
        //TODO INTENT ITEMEVENT
        if (typeEvent == NEXT_EVENT) {
            Intent intent = new Intent(this, ManageItemActivity.class);
            intent.putExtra("item", adapterData.get(position));
            startActivity(intent);
        } else if (typeEvent == PASS_EVENT) {
            Intent intent = new Intent(this, EventItemActivity.class);
            intent.putExtra("item", adapterData.get(position));
            startActivity(intent);
        } else if (typeEvent == SUB_EVENT) {
            Intent intent = new Intent(this, EventItemActivity.class);
            intent.putExtra("item", adapterData.get(position));
            startActivity(intent);
        }
    }

    protected int getSelfNavDrawerItem() {
        if (typeEvent == NEXT_EVENT)
            return MANAGE_ID;
        else if (typeEvent == SUB_EVENT)
            return UPCOMING_ID;
        return MANAGE_ID;
    }
}