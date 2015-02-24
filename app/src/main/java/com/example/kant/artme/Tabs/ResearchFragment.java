package com.example.kant.artme.Tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.BaseActivity;
import com.example.kant.artme.EventItemActivity;
import com.example.kant.artme.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shaft on 13/02/2015.
 */
public class ResearchFragment extends Fragment implements ResearchAdapter.ClickListener{

    private List<Event> adapterData = new ArrayList<>();
    private ResearchAdapter mResearchAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        ((BaseActivity) getActivity()).getActionBarToolbar().setTitle(R.string.title_activity_research);
        ((BaseActivity) getActivity()).setSupportActionBar(((BaseActivity) getActivity()).getActionBarToolbar());

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler);
        mResearchAdapter = new ResearchAdapter(getActivity(), adapterData);
        mResearchAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mResearchAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnScrollListener(((BaseActivity) getActivity()).getRecyclerScrollListener());

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
        return view;
    }

    @Override
    public void itemClicked(int position) {
        Intent intent = new Intent(getActivity(), EventItemActivity.class);
//        intent.putExtra("item", adapterData.get(position));
        startActivity(intent);
    }
}
