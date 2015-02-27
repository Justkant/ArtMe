package com.example.kant.artme.ArtmeAPI;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class Event implements Serializable{
    public String title;
    public String description;
    public String date;
    public String adress;
    public String picture_url;
    public Bitmap picture_btm;
    public List<User> sub_users;
    public List<String>  photos;
    //TODO ADD HOUR + TAG (SOUS TYPE ?) + PRIX ? + lieu et non adress (zenith toussa)
}
