package com.example.androidserver.API;

import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartAPI {
    @GET("/cart/{id}")
    public Call<List<Cart>> cart(@Path("id") String id);

    @POST("/cart/{id}")
    public Call<Cart> Addcart(@Body Cart cart,@Path("id") String id);

    @POST("/inc/{id}")
    public Call<Void> inc(@Path("id") String id);

    @POST("/dec/{id}")
    public Call<Void> dec(@Path("id")String id);
}
