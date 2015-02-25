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
import com.example.kant.artme.R;

import java.util.HashMap;

/**
 * Created by Shaft on 17/02/2015.
 */
public class EventItemActivity extends ActionBarActivity implements BaseSliderView.OnSliderClickListener {

    private Toolbar toolbar;
    private SliderLayout mDemoSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_item);

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

        //SLIDER
        mDemoSlider = (SliderLayout)findViewById(R.id.slider);
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("sala",R.drawable.sala);
        file_maps.put("bulbi",R.drawable.bulbi);
        file_maps.put("cara",R.drawable.cara);
        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }

        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
    // FIN SLIDER


        ((TextView) findViewById(R.id.event_title)).setText("TOTO");
        ((TextView) findViewById(R.id.event_desc)).setText("DESCRiPTIOn blaaaa blaaablaaaaaaaaaaaaaaaaaaa balbla lblalbla blla blalbla lbla lbllblalbllballlllaaaaaaaaaaaaaaaaaaaaaaaa lalblabl alblalb lall ablblalblalblbalb abla balbla lblalb alblal ablalbla llab alb alblala balblall blab lalblalblalblabl alga");
        ((TextView) findViewById(R.id.date)).setText("01/01/1001");
        ((TextView) findViewById(R.id.location)).setText("Toujour dans ton cul");
        ((TextView) findViewById(R.id.participant)).setText("1250 \n Participant");
        ((ImageView) findViewById(R.id.title_pic)).setImageResource(R.drawable.pika);

        ((TextView) findViewById(R.id.participate)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EventItemActivity.this, "Ajout event à la list des upcoming", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }
}
