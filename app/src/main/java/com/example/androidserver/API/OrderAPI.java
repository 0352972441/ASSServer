package com.example.androidserver.API;

import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Order;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrderAPI {
    @GET("/order/{id}")
    public Call<List<Order>> orders(@Path("id") String id);

    @POST("/order/{id}")
    public Call<Order> order(@Body Order order, @Path("id") String id);

    @DELETE("/order/{id}")
    public Call<Void> deleteOrder(@Query("id") String id);
}
