package com.example.androidserver.screen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.androidserver.API.ProductAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
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

public class AddProductActivity extends AppCompatActivity {
    public static final int CODE_ID_IMAGE = 1000;
    public static final String TYPEAPI = "TYPE";
    public static final String _ID = "ID";

    ImageView imgProduct;
    Button btnUpload;
    EditText edName,edDesc,edPrice,edImage;
    SnackBar snackBar;
    Handler hander;
    String TYPE = "ADD";
    String _id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        imgProduct = findViewById(R.id.img_product);
        btnUpload = findViewById(R.id.btn_upload);
        edPrice = findViewById(R.id.ed_price);
        edDesc = findViewById(R.id.ed_description);
        edName = findViewById(R.id.ed_name);
        edImage = findViewById(R.id.ed_image);
        snackBar = new SnackBar(edImage);

        TYPE = getIntent().getExtras().getString(TYPEAPI);
        if(TYPE.equals("UPDATE")){
            _id = getIntent().getExtras().getString(_ID);
            getInstance().getSingleProduct(_id).enqueue(new Callback<Product>() {
                @Override
                public void onResponse(Call<Product> call, Response<Product> response) {
                    if(response.isSuccessful()){
                        Product product = response.body();
                        edDesc.setText(product.getDesc());
                        edImage.setText(product.getImage());
                        edPrice.setText(product.getPrice()+"");
                        edName.setText(product.getName());
                    }
                }

                @Override
                public void onFailure(Call<Product> call, Throwable t) {
                    showSnackBar("Lỗi load dữ liệu");
                }
            });
        }

        edPrice.setText("0");
        hander = new Handler();
        hander.post(new Runnable() {
            @Override
            public void run() {
                String link = edImage.getText().toString().trim();
                if(link.isEmpty()){
                    hander.postDelayed(this,500);
                    return;
                }
                hander.postDelayed(this,500);
                Picasso.with(AddProductActivity.this).load(link).into(imgProduct);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString();
                String desc = edDesc.getText().toString();
                double price = Double.valueOf(edPrice.getText().toString());
                String image = edImage.getText().toString().trim();
                if(name.isEmpty()){
                    showSnackBar("Tên không được để trống !");
                    return;
                }else if(desc.isEmpty()){
                    showSnackBar("Miêu tả không được để trống !");
                    return;
                }else if(price <= 0){
                    showSnackBar("Tiền không được bé hơn 0 !");
                    return;
                }else if(image.isEmpty()){
                    showSnackBar("Ảnh không được để trống !");
                    return;
                }else if(!image.endsWith("jpg")){
                    showSnackBar("Ảnh không hợp lệ !");
                    return;
                }else{
                    feature(image,price,name,desc);
                }
            }
        });
    }


    private ProductAPI getInstance(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        return productAPI;
    }

    void feature(String image,double price,String name,String desc){
        Product product = new Product(image,price,name,desc);
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setLenient();
        Gson gson = gsonBuilder.create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        ProductAPI productAPI = retrofit.create(ProductAPI.class);
        switch(TYPE){
            case "ADD":{
                productAPI.createProduct(product).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        showSnackBar("Thêm sản phẩm thành công");
                        hander.removeCallbacksAndMessages(null);
                        Intent intent = new Intent(AddProductActivity.this,HomeScreen.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showSnackBar("Thêm thất bại" + t.getMessage());
                    }
                });
                break;
            }
            case "UPDATE":{
                productAPI.update(_id,product).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        showSnackBar("Cập nhật sản phẩm thành công");
                        hander.removeCallbacksAndMessages(null);
                        Intent intent = new Intent(AddProductActivity.this,HomeScreen.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        showSnackBar("Cập nhật sản phẩm thất bại" + t.getMessage());
                    }
                });
                break;
            }
        }
    }

    void showSnackBar(String message){
        snackBar.showSnackBar(message);
    }

    /*private void pickImageFromGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,CODE_ID_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PermissionReadExternalStorage.PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickImageFromGallery();
            }else{
                Toast.makeText(this, "Permission denied ....", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_ID_IMAGE) {
            if (resultCode == RESULT_OK) {
                try {
                    Uri filePath = data.getData();
                    mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                    if (mBitmap != null) {
                        Picasso.with(this).load(filePath).into(imgProduct);
                    }
                } catch (Exception ex) {
                    Log.d("Eorror=============", ex.getMessage(), ex.getCause());
                    Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public static class PermissionReadExternalStorage {
        public static final int PERMISSION_CODE = 1001;
        public static boolean checkVersionSDk(){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                return true;
            }else {
                return false;
            }
        }

        public static boolean checkPermissionImage(final Context context, final Activity activity){
            if(checkVersionSDk()){
                if(ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED){
                    String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                    ActivityCompat.requestPermissions(activity,permission,PERMISSION_CODE);
                    return true;
                }else{
                    return false;
                }
            }else{
                return false;
            }
        }
    }*/

}