package com.example.kant.artme.ArtmeAPI;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Shaft on 22/02/2015.
 */
public interface ArtmeAPI {

    @GET(Constants.ME)
    public void userMe(@Header("TOKEN") String token, Callback<User> callback);

    @GET(Constants.EVENTS)
    public void getEvents(Callback<List<Event>> callback);

    @POST(Constants.LOGIN)
    void login(@Body User log, Callback<String> cb);

    @POST(Constants.USERS)
    void postUser(@Body User log, Callback<String> cb);

    @POST("/users/{id}/event")
    void postEvent(@Path("id") int id, @Header("TOKEN") String token, @Body Event event, Callback<Event> cb);

    @POST("/events/{id}/user")
    void subEvent(@Path("id") int id, @Header("TOKEN") String token, Callback<Event> cb);

    @POST("/groups/{id_grp}/user/{id}")
    void unsubGroup(@Path("id_grp") int id_grp, @Path("id") int id, @Header("TOKEN") String token, Callback<ApiReturn> cb);

    @POST(Constants.C_GROUP)
    void crtGroup(@Header("TOKEN") String token, @Body String group, Callback<ApiReturn> cb);
}
