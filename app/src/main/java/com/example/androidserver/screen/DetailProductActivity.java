package com.example.androidserver.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidserver.API.CartAPI;
import com.example.androidserver.API.ProductAPI;
import com.example.androidserver.Account;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Product;
import com.example.androidserver.widgets.SnackBar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailProductActivity extends AppCompatActivity {
    TextView nameProduct, descProduct, priceProduct;
    ImageView imgProduct;
    ImageButton imgCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        nameProduct = findViewById(R.id.tx_name_product);
        descProduct = findViewById(R.id.tx_desc_product);
        priceProduct = findViewById(R.id.tx_price_product);

        imgProduct = findViewById(R.id.img_product);

        imgCart = findViewById(R.id.btn_cart);
        showData();
    }

    void showData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().getBundle("Bundle");
        String name = bundle.getString("name");
        String desc = bundle.getString("desc");
        String image = bundle.getString("image");
        String price = bundle.getString("price");
        String id = bundle.getString("id");
        Picasso.with(this).load(image).into(imgProduct);
        nameProduct.setText(name);
        descProduct.setText(desc);
        priceProduct.setText(price);
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cart cart = new Cart(nameProduct.getText().toString(),1,image,Double.valueOf(price),id);
                getInstance().Addcart(cart, Account.getAccountInstance().getAccount().get_id()).enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        showSnackBar("Thêm vào giỏ hàng thành công");
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {

                    }
                });
            }
        });
    }

  void showSnackBar(String message){
        new SnackBar(imgCart).showSnackBar(message);
    }
    private CartAPI getInstance(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        CartAPI cartAPI = retrofit.create(CartAPI.class);
        return cartAPI;
    }
}