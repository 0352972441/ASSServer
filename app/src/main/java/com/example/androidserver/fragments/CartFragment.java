package com.example.androidserver.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidserver.API.CartAPI;
import com.example.androidserver.API.OrderAPI;
import com.example.androidserver.API.ProductAPI;
import com.example.androidserver.Account;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.adapter.CartAdapter;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Order;
import com.example.androidserver.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartFragment extends Fragment {
    public static String isProduct = "CART";
    RecyclerView recycler_view_cart;
    CartAdapter adapter;
    Button order;
    TextView tv_total;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recycler_view_cart = view.findViewById(R.id.recycler_view_cart);
        order = view.findViewById(R.id.buy);
        tv_total = view.findViewById(R.id.tv_total);
        if(Account.getAccountInstance().getAccount().get_id() !=null){
            getInstance().cart(Account.getAccountInstance().getAccount().get_id()).enqueue(new Callback<List<Cart>>() {
                @Override
                public void onResponse(Call<List<Cart>> call, Response<List<Cart>> response) {
                    adapter = new CartAdapter(response.body());
                    recycler_view_cart.setAdapter(adapter);
                    recycler_view_cart.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
                    tv_total.setText(String.valueOf(adapter.total()));
                    adapter.setDeleteListener(new Listener() {
                        @Override
                        public void onClickListener(int position) {
                            //Log.d("Id",adapter.getSingleProduct(position).get_id());
                            getInstance().dec(adapter.getSingleProduct(position).get_id()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        tv_total.setText(String.valueOf(adapter.total()));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    });

                    adapter.setAddListener(new Listener() {
                        @Override
                        public void onClickListener(int position) {
                            getInstance().inc(adapter.getSingleProduct(position).get_id()).enqueue(new Callback<Void>() {
                                @Override
                                public void onResponse(Call<Void> call, Response<Void> response) {
                                    if(response.isSuccessful()){
                                        tv_total.setText(String.valueOf(adapter.total()));
                                    }
                                }

                                @Override
                                public void onFailure(Call<Void> call, Throwable t) {

                                }
                            });
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<Cart>> call, Throwable t) {

                }
            });

            order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Gson gson = getGson();
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
                    OrderAPI order = retrofit.create(OrderAPI.class);
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                    Date date = new Date();
                    Order orderpro = new Order(formatter.format(date).toString(),tv_total.getText().toString());
                    order.order(orderpro,Account.getAccountInstance().getAccount().get_id()).enqueue(new Callback<Order>() {
                        @Override
                        public void onResponse(Call<Order> call, Response<Order> response) {
                            new SnackBar(tv_total).showSnackBar("Đặt hàng thành công");
                        }

                        @Override
                        public void onFailure(Call<Order> call, Throwable t) {

                        }
                    });
                }
            });
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    private CartAPI getInstance(){
        Gson gson = getGson();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        CartAPI cartApi = retrofit.create(CartAPI.class);
        return cartApi;
    }

    private Gson getGson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        return gson;
    }
}