package com.example.kant.artme.Tabs;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Shaft on 16/02/2015.
 */
public class User implements Serializable{
    public int id;
    public String last_name;
    public String first_name;
    public String username;
    public String picture_url;
    public String email;
    public String password;
    public String description;
    public String creation_date;

    public List<Group> list_groups;

    public List<Event> sub_events;
    public List<Event> pass_events;
    public List<Event> next_events;

    public List<String> photos;
}
