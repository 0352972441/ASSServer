package com.example.androidserver.API;

import com.example.androidserver.models.Product;
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

public interface ProductAPI {
    @GET("/listproducts")
    public Call<List<Product>> productList();

    @DELETE("/products/{id}")
    public Call<Void> productUser(@Path("id")String id);

    @POST("/product")
    public Call<Void> productUser(@Body Product product);

    @PATCH("products/{id}")
    public Call<Void> update(@Query("id") String id, @Body Product product);
}
