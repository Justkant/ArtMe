package com.example.kant.artme.ArtmeAPI;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Shaft on 22/02/2015.
 */
public interface ArtmeAPI {

    @GET(Constants.USER)
    public void userGet(Callback<User> callback);

    @POST(Constants.LOGIN)
    void login(@Body User log, Callback<String> cb);
}
