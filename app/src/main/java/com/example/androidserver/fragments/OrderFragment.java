package com.example.androidserver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.androidserver.API.CartAPI;
import com.example.androidserver.API.OrderAPI;
import com.example.androidserver.Account;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.adapter.CartAdapter;
import com.example.androidserver.adapter.OrderAdapter;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderFragment extends Fragment {

    public static String isProduct = "Order";
    RecyclerView recycler_view_order;
    OrderAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_view_order = view.findViewById(R.id.recycler_view_order);
        getInstance().orders(Account.getAccountInstance().getAccount().get_id()).enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                adapter = new OrderAdapter(response.body());
                recycler_view_order.setAdapter(adapter);
                recycler_view_order.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            }

            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    private OrderAPI getInstance(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        OrderAPI orderApi = retrofit.create(OrderAPI.class);
        return orderApi;
    }
}