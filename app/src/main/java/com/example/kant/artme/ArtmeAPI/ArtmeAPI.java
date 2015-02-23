package com.example.kant.artme.ArtmeAPI;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by Shaft on 22/02/2015.
 */
public interface ArtmeAPI {

    @GET(Constants.USER)
    public void userGet(Callback<User> callback);
}
