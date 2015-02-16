package com.example.kant.artme.Tabs;

import android.app.Fragment;
import android.os.Bundle;
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
 * Created by Shaft on 13/02/2015.
 */
public class ResearchEvent extends Fragment implements ResearchAdapter.ClickListener{

    private List<Event> adapterData = new ArrayList<>();
    private ResearchAdapter mResearchAdapter;

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_board, container, false);

        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.board_recycler);
        mResearchAdapter = new ResearchAdapter(getActivity(), adapterData);
        mResearchAdapter.setClickListener(this);

        mRecyclerView.setAdapter(mResearchAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnScrollListener(((BaseActivity) getActivity()).getRecyclerScrollListener());

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
