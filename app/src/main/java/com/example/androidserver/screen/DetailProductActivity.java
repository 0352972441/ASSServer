package com.example.androidserver.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidserver.R;
import com.squareup.picasso.Picasso;

public class DetailProductActivity extends AppCompatActivity {
    TextView nameProduct, descProduct;
    ImageView imgProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_product);
        nameProduct = findViewById(R.id.tx_name_product);
        descProduct = findViewById(R.id.tx_desc_product);
        imgProduct = findViewById(R.id.img_product);
        showData();
    }

    void showData(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras().getBundle("Bundle");
        String name = bundle.getString("name");
        String desc = bundle.getString("desc");
        String image = bundle.getString("image");
        Picasso.with(this).load(image).into(imgProduct);
        nameProduct.setText(name);
        descProduct.setText(desc);
    }
}