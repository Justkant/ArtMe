package com.example.kant.artme.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 13/02/2015.
 */
public class ResearchActivity extends BaseActivity implements ResearchAdapter.ClickListener{

    private List<Event> adapterData = new ArrayList<>();
    private ResearchAdapter mResearchAdapter;

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

        Event event1 = new Event();
        event1.title = "test1";
        event1.description = "toto blabla";
        event1.date = "01/01/1001";
        event1.adress = "Dans ton Uc";
        Event event2 = new Event();
        event2.title = "test2";
        event2.description = "toto blabla";
        event2.date = "01/01/1001";
        event2.adress = "Dans ton Uc";
        adapterData.add(event1);
        adapterData.add(event2);
        mResearchAdapter.notifyDataSetChanged();

    }

    protected int getSelfNavDrawerItem() {
        return RESEARCH_ID;
    }


    @Override
    public void itemClicked(int position) {
        Log.d("itemclick ===>", "ResearchActivity");
        Intent intent = new Intent(this,EventItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }

}
