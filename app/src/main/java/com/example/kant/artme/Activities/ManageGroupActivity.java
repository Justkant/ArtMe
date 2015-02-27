package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 27/02/2015.
 */
public class ManageGroupActivity extends BaseActivity implements ManageGroupAdapter.ClickListener {

    private List<Group> adapterData = new ArrayList<>();
    private ManageGroupAdapter mManageGroupAdapter;
    private User paramUser;
    private List<Group> groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_board);

        paramUser = (User) getIntent().getSerializableExtra("user");
        groups = paramUser.groups;

        getActionBarToolbar().setTitle(R.string.title_activity_groups);
        setSupportActionBar(getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.board_recycler);
        mManageGroupAdapter = new ManageGroupAdapter(this, adapterData);
        mManageGroupAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mManageGroupAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setOnScrollListener(getRecyclerScrollListener());

        //SET RIGHT CARDS
        if (paramUser.groups != null)
            for (int i = 0; i < paramUser.groups.size(); ++i) {
                Group nGroup = paramUser.groups.get(i);
                adapterData.add(nGroup);
            }

    }

    @Override
    public void manageItemClicked(int position) {
        Intent intent = new Intent(this, GroupItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }
}
