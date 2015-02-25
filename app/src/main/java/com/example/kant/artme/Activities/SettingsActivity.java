package com.example.kant.artme.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.LruCache;
import android.view.View;

import com.example.kant.artme.MyApplication;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

public class SettingsActivity extends ActionBarActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle(R.string.title_activity_settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragment {

        protected LruCache<String, Bitmap> mMemoryCache;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            mMemoryCache = ((MyApplication) getActivity().getApplication()).mMemoryCache;

            Preference mdeco = findPreference("deco_button");
            mdeco.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference arg0) {
                    MySharedPreferences.saveToPreferences(getActivity(), getString(R.string.token_string), "");
                    MySharedPreferences.clearPreferences(getActivity());
                    mMemoryCache.remove("userPicture");
                    getActivity().finish();
                    return true;
                }
            });
        }

    }
}
