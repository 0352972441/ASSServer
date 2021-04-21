package com.example.androidserver.API;

import com.example.androidserver.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserAPI {
    @GET("/users")
    public Call<List<User>> userList();

    @DELETE("/users/{id}")
    public Call<Void> deleteUser(@Path("id")String id);

    @POST("/user")
    public Call<Void> insertUser(@Body User user);

    @POST("/users/login")
    public Call<Void> login(@Body User user);

    @PATCH("users/{id}")
    public Call<Void> update(@Query("id") String id, @Body User user);
}
