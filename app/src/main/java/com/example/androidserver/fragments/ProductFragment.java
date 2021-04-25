package com.example.androidserver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidserver.API.ProductAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.adapter.ProductAdapter;
import com.example.androidserver.models.Product;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProductFragment extends Fragment {
    public static String isProduct = "PRODUCT";
    private RecyclerView recyclerViewProduct;
    private ProductAdapter productAdapter;
    private List<Product> productList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewProduct = view.findViewById(R.id.recyclerview_product_list);
        recyclerViewProduct.setLayoutManager(new GridLayoutManager(getContext(),2));
        productList = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        productAPI.productList().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    productList = response.body();
                    Log.d("Size",productList.size()+"");
                    productAdapter = new ProductAdapter(productList);
                    recyclerViewProduct.setAdapter(productAdapter);
                    //onClick();
                }else{
                    Log.d("Error","Error");
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Error:",t.toString());
            }
        });
    }
}