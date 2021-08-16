package com.example.androidserver.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.androidserver.API.ProductAPI;
import com.example.androidserver.Account;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.adapter.ProductAdapter;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.Product;
import com.example.androidserver.screen.ActivityInsertUser;
import com.example.androidserver.screen.AddProductActivity;
import com.example.androidserver.screen.DetailProductActivity;
import com.example.androidserver.widgets.SnackBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
        FloatingActionButton floatAdd = view.findViewById(R.id.float_button);
        if(Account.getAccountInstance().getAccount().isAdmin()){
            floatAdd.setVisibility(View.VISIBLE);
        }else{
            floatAdd.setVisibility(View.INVISIBLE);
        }

        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProductActivity.class);
                intent.putExtra(AddProductActivity.TYPEAPI,"ADD");
                startActivity(intent);
                //getActivity().finish();
            }
        });

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
                    productAdapter.setListener(new Listener() {
                        @Override
                        public void onClickListener(int position) {
                            Log.d("Data","Product");
                            Intent intent = new Intent(getContext(), DetailProductActivity.class);
                            Bundle bundle = new Bundle();
                            Product product = productAdapter.getSingleProduct((int)position);
                            bundle.putString("name",product.getName());
                            bundle.putString("desc",product.getDesc());
                            bundle.putString("image",product.getImage());
                            bundle.putString("id",product.get_id());
                            bundle.putString("price",String.valueOf(product.getPrice()));
                            intent.putExtra("Bundle",bundle);
                            startActivity(intent);
                        }
                    });

                    productAdapter.setDeleteListener(new Listener() {
                        @Override
                        public void onClickListener(int position) {
                            deleteProduct(productAdapter.getSingleProduct(position).get_id());
                            productList.remove(position);
                            productAdapter.notifyDataSetChanged();
                            productAdapter = new ProductAdapter(productList);
                            recyclerViewProduct.setAdapter(productAdapter);
                        }
                    });

                    productAdapter.setEditListener(new Listener() {
                        @Override
                        public void onClickListener(int position) {
                            Intent intent = new Intent(getContext(),AddProductActivity.class);
                            intent.putExtra(AddProductActivity.TYPEAPI,"UPDATE");
                            intent.putExtra(AddProductActivity._ID,productAdapter.getSingleProduct(position).get_id());
                            startActivity(intent);
                        }
                    });

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


    void deleteProduct(String id){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        productAPI.deleteProduct(id).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                showSnackBar("Xoá thành công");
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                showSnackBar("Xoá thất bại");
            }
        });
    }

    void showSnackBar(String message){
        new SnackBar(recyclerViewProduct).showSnackBar(message);
    }
}