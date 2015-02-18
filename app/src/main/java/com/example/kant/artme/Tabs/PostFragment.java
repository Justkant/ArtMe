package com.example.kant.artme.Tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kant.artme.BaseActivity;
import com.example.kant.artme.R;

/**
 * Created by Shaft on 16/02/2015.
 */
public class PostFragment extends Fragment {

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_fragment, container, false);

        ((BaseActivity) getActivity()).getActionBarToolbar().setTitle(R.string.title_activity_post);
        ((BaseActivity) getActivity()).setSupportActionBar(((BaseActivity) getActivity()).getActionBarToolbar());

        ((TextView) view.findViewById(R.id.title)).setText("Test Title");

        return view;
    }
}
