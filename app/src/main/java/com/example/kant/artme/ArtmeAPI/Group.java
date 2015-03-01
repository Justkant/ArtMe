package com.example.kant.artme.ArtmeAPI;

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class Group implements Serializable {
    public int id;
    public List<User> listUsers;
    @SerializedName("title")
    public String title;
    public String description;
    public String picture_url;
    public Bitmap picture_btm;
    public String creation_date;
    public String adress;
    public List<Event> next_events;
    public List<Event> pass_events;
    public List<String> photos;
    public boolean can_edit;
}
