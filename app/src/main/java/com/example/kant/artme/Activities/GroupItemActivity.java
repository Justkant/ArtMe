package com.example.kant.artme.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.kant.artme.ArtmeAPI.ApiReturn;
import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.Group;
import com.example.kant.artme.MyGeneralImageLoader;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 27/02/2015.
 */
public class GroupItemActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {

    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private SliderLayout mDemoSlider;
    private Group group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_item);

        group = (Group) getIntent().getSerializableExtra("item");

        setSupportActionBar(getActionBarToolbar());
        getActionBarToolbar().setTitle(group.title);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //SLIDER
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("sala", R.drawable.sala);
        file_maps.put("bulbi", R.drawable.bulbi);
        file_maps.put("cara", R.drawable.cara);
        for (String name : file_maps.keySet()) {
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra", name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        // FIN SLIDER


        ((TextView) findViewById(R.id.event_title)).setText(group.title);
        ((TextView) findViewById(R.id.event_desc)).setText(group.description);
        ((TextView) findViewById(R.id.location)).setText(group.adress);
        if (group.picture_url != null)
            try {
                new MyGeneralImageLoader((ImageView) findViewById(R.id.title_pic))
                        .execute(getString(R.string.base_url) + "/" + group.picture_url)
                        .get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        else
            ((ImageView) findViewById(R.id.title_pic)).setImageResource(R.drawable.df);


        ((Button) findViewById(R.id.unsub)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //API
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.base_url))
                        .build();
                restAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
                api = restAdapter.create(ArtmeAPI.class);
                Log.d("ID GRP ==> ", String.valueOf(group.id));
                Log.d("ID USER ==> ", String.valueOf(currentUser.id));
                api.unsubGroup(group.id, currentUser.id, MySharedPreferences.readToPreferences(getApplicationContext(), getString(R.string.token_string), ""), new Callback<ApiReturn>() {
                    @Override
                    public void success(ApiReturn ret, Response response) {
                        Toast.makeText(GroupItemActivity.this, "UNSUB OK", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("FAIL", retrofitError.getMessage());
                    }
                });
            }
        });

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
