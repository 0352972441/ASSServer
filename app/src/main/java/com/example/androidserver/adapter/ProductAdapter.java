package com.example.androidserver.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidserver.R;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.Product;
import com.example.androidserver.models.User;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public List<Product> productList = new ArrayList<>();
    private Listener listener;
    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_product,parent,false);
        return new ProductAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        if(productList != null){
            holder.tv_price.setText(String.valueOf(productList.get(position).getPrice()));
            holder.tv_name.setText(productList.get(position).getName());
            //decode base64 string to image
            byte[] imageBytes = Base64.decode(productList.get(position).getImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            holder.img_product.setImageBitmap(decodedImage);
            holder.img_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(position);
                }
            });
        }
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    /*public void remove(int position){
        if(userList.get(position) != null){
            userList.remove(position);
            notifyDataSetChanged();
        }
    }

    public User getSingleUser(int position){
        notifyDataSetChanged();
        return userList.get(position);
    }*/

    @Override
    public int getItemCount() {
        return productList != null ? productList.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_price,tv_name;
        public ViewHolder(@NonNull View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_price = view.findViewById(R.id.tv_price);
            img_product = view.findViewById(R.id.img_product);
        }
    }
}