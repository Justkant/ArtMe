package com.example.kant.artme.Activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.ArtmeAPI.User;
import com.example.kant.artme.R;

import java.util.HashMap;

/**
 * Created by Shaft on 17/02/2015.
 */
public class EventItemActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {

    private Toolbar toolbar;
    private SliderLayout mDemoSlider;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_item);

        setSupportActionBar(getActionBarToolbar());
        getActionBarToolbar().setTitle(R.string.title_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBarToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

/*
        toolbar = (Toolbar) findViewById(R.id.actionBarToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setSubtitle(R.string.title_activity_settings);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
*/
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

        event = (Event) getIntent().getSerializableExtra("item");


        ((TextView) findViewById(R.id.event_title)).setText(event.title);
        ((TextView) findViewById(R.id.event_desc)).setText(event.description);
        ((TextView) findViewById(R.id.date)).setText(event.date);
        ((TextView) findViewById(R.id.location)).setText(event.adress);
        ((TextView) findViewById(R.id.participant)).setText("1250 \n Participant");
        if (event.picture_btm != null)
            ((ImageView) findViewById(R.id.title_pic)).setImageBitmap(event.picture_btm);
        else
            ((ImageView) findViewById(R.id.title_pic)).setImageResource(R.drawable.df);

        ((TextView) findViewById(R.id.participate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventItemActivity.this, "Ajout event à la list des upcoming", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }
}
