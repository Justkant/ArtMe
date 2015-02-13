package com.example.kant.artme.Tabs;

import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Quentin on 30/01/2015.
 * EpiAndroid Project.
 */
public class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    boolean hideToolBar = false;
    private ActionBar supportActionBar;

    public RecyclerViewScrollListener(ActionBar supportActionBar) {
        this.supportActionBar = supportActionBar;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (hideToolBar) {
            supportActionBar.hide();
        } else {
            supportActionBar.show();
        }
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (dy > 20) {
            hideToolBar = true;

        } else if (dy < -5) {
            hideToolBar = false;
        }
    }
}
