package com.example.kant.artme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.example.kant.artme.ArtmeAPI.ArtmeAPI;
import com.example.kant.artme.ArtmeAPI.Event;
import com.example.kant.artme.MySharedPreferences;
import com.example.kant.artme.R;

import java.io.File;
import java.util.HashMap;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Shaft on 16/02/2015.
 */
public class PostActivity extends BaseActivity implements BaseSliderView.OnSliderClickListener {

    private RestAdapter restAdapter;
    private ArtmeAPI api;
    private EditText mTitle;
    private EditText mDate;
    private Event postEvent = new Event();

    private static final int RESULT_LOAD_IMAGE_B = 2;
    private static int RESULT_LOAD_IMAGE = 1;
    //private View v;

    //loader multiple
    private String file_path = "";
    private HashMap<String, Integer> file_maps = new HashMap<String, Integer>();
    private SliderLayout mDemoSlider;
    private int nb_pic_loaded = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        getActionBarToolbar().setTitle(R.string.title_activity_post);
        setSupportActionBar(getActionBarToolbar());

        mTitle = (EditText) findViewById(R.id.title);
        mDate = (EditText) findViewById(R.id.date);


        ((Button) findViewById(R.id.load)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        //test custom gallery
        mDemoSlider = (SliderLayout) findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.stopAutoCycle();
        ((Button) findViewById(R.id.load_b)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb_pic_loaded += 1;
                if (nb_pic_loaded > 1)
                    mDemoSlider.startAutoCycle();
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_B);
            }
        });


        //API POST EVENT
        ((Button) findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postEvent.title = mTitle.getText().toString();
                postEvent.date = mDate.getText().toString();
                Log.d("DATE ===> ", "[" + mDate.getText().toString() + "]");

                //API
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.base_url))
                        .build();
                api = restAdapter.create(ArtmeAPI.class);
                api.postEvent(currentUser.id ,MySharedPreferences.readToPreferences(getApplicationContext(), getString(R.string.token_string), ""), postEvent, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Toast.makeText(PostActivity.this, "EVENT POST", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("FAIL", retrofitError.getMessage());
                    }
                });

            }
        });

    }


    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.post_fragment, container, false);
        v = view;

        mTitle = (EditText) v.findViewById(R.id.title);
        mDate = (EditText) v.findViewById(R.id.date);

        ((BaseActivity) getActivity()).getActionBarToolbar().setTitle(R.string.title_activity_post);
        ((BaseActivity) getActivity()).setSupportActionBar(((BaseActivity) getActivity()).getActionBarToolbar());

        ((Button) view.findViewById(R.id.load)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        //test custom gallery
        mDemoSlider = (SliderLayout) v.findViewById(R.id.slider);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.stopAutoCycle();
        ((Button) view.findViewById(R.id.load_b)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nb_pic_loaded += 1;
                if (nb_pic_loaded > 1)
                    mDemoSlider.startAutoCycle();
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE_B);
            }
        });


        //API POST EVENT
        ((Button) view.findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                postEvent.title = mTitle.getText().toString();
                postEvent.date = mDate.getText().toString();

                //API
                restAdapter = new RestAdapter.Builder()
                        .setEndpoint(getString(R.string.base_url))
                        .build();
                api = restAdapter.create(ArtmeAPI.class);
                api.postEvent(((BaseActivity) getActivity()).currentUser.id ,MySharedPreferences.readToPreferences(getActivity(), getString(R.string.token_string), ""), postEvent, new Callback<Event>() {
                    @Override
                    public void success(Event event, Response response) {
                        Toast.makeText(getActivity(), "EVENT POST", Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        Log.d("FAIL", retrofitError.getMessage());
                    }
                });

            }
        });

        return view;
    }*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor =getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            ImageView imageView = (ImageView) findViewById(R.id.loaded_pic);
            imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        } else if (requestCode == RESULT_LOAD_IMAGE_B && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            file_path = picturePath;
            DefaultSliderView defaultSliderView = new DefaultSliderView(this);
            defaultSliderView
                    .image(new File(file_path))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);
            //Var envoyer au onSliderClick
/*            textSliderView.getBundle()
                    .putString("extra",name); */

            mDemoSlider.addSlider(defaultSliderView);
        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
       // Toast.makeText(getActivity(), slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    protected int getSelfNavDrawerItem() {
        return POST_ID;
    }
}
