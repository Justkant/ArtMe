package com.example.kant.artme.ArtmeAPI;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class Group implements Serializable {
    public List<User> listUsers;
    public String title;
    public String description;
    public String picture_url;
    public String creation_date;
    public String adress;
    public List<Event> next_events;
    public List<Event> pass_events;
    public List<String> photos;
}
