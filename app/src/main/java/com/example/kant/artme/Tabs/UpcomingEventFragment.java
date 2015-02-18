package com.example.kant.artme.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kant.artme.BaseActivity;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class UpcomingEventFragment extends Fragment implements UpcomingEventAdapter.ClickListener {

    private List<Event> adapterData = new ArrayList<>();
    private UpcomingEventAdapter mUpcomingEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        ((BaseActivity) getActivity()).getActionBarToolbar().setTitle(R.string.title_activity_upcoming_event);
        ((BaseActivity) getActivity()).setSupportActionBar(((BaseActivity) getActivity()).getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler);
        mUpcomingEventAdapter = new UpcomingEventAdapter(getActivity(), adapterData);
        mUpcomingEventAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mUpcomingEventAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnScrollListener(((BaseActivity) getActivity()).getRecyclerScrollListener());

        Event event1 = new Event();
        event1.title = "test1";
        Event event2 = new Event();
        event2.title = "test2";
        adapterData.add(event1);
        adapterData.add(event2);
        mUpcomingEventAdapter.notifyDataSetChanged();
        return view;
    }

    @Override
    public void itemClicked(int position) {

        //TODO INTENT ITEMEVENT
/*        Intent intent = new Intent(getActivity(), ProjectItemActivity.class);
        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);*/
    }
}