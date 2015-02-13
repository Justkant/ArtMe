package com.example.kant.artme.Tabs;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kant.artme.R;

/**
 * Created by Quentin on 23/01/2015.
 * EpiAndroid Project.
 */
public class SlidingTabPagerAdapter extends FragmentPagerAdapter {

    private final static int MODULES_POS = 0;
    private final static int ACTIVITIES_POS = 1;
    private final static int PROJECTS_POS = 2;
    private final static int SUSIES_POS = 3;
    private final static int MARKS_POS = 4;
    private final static int TROMBI_POS = 5;
    private String[] tabs;

    public SlidingTabPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        tabs = context.getResources().getStringArray(R.array.tabs);
    }


    // TODO : UPDATE WITH YOUR FRAGMENTS
    @Override
    public Fragment getItem(int position) {
        /*Fragment fragment = null;
        switch (position) {
            case MODULES_POS:
                fragment = new ModulesFragment();
                break;
            case ACTIVITIES_POS:
                fragment = new ActivitiesFragment();
                break;
            case PROJECTS_POS:
                fragment = new ProjectsFragment();
                break;
            case SUSIES_POS:
                fragment = new SusiesFragment();
                break;
            case MARKS_POS:
                fragment = new MarksFragment();
                break;
            case TROMBI_POS:
                fragment = new TrombiFragment();
                break;
        }
        return fragment;*/
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabs[position];
    }

    @Override
    public int getCount() {
        return tabs.length;
    }
}
