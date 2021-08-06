package com.example.androidserver.adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidserver.R;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.Product;
import com.example.androidserver.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    public List<Product> productList = new ArrayList<>();
    private Listener listener;
    private Listener deleteListener;
    private Listener editListener;

    public void setDeleteListener(Listener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setEditListener(Listener editListener) {
        this.editListener = editListener;
    }

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
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(productList != null){
            holder.tv_price.setText(String.valueOf(productList.get(position).getPrice()));
            holder.tv_name.setText(productList.get(position).getName());
            Picasso.with(holder.img_product.getContext()).load(productList.get(position).getImage()).into(holder.img_product);
            holder.card_product.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(position);
                }
            });
            holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("Index",String.valueOf(position));
                    deleteListener.onClickListener(position);
                }
            });

            holder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    editListener.onClickListener(position);
                }
            });
            // Đề vào card
            holder.card_product.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    view.findViewById(R.id.btn_delete).setVisibility(View.VISIBLE);
                    holder.btnEdit.setVisibility(View.VISIBLE);
                    return false;
                }
            });
        }
    }

    public Product getSingleProduct(int position){
        return productList.get(position);
    }
    public void deleteProduct(int position){
        productList.remove(getSingleProduct(position));
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
        private CardView card_product;
        private ImageButton btnDelete,btnEdit;
        public ViewHolder(@NonNull View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name);
            tv_price = view.findViewById(R.id.tv_price);
            img_product = view.findViewById(R.id.img_product);
            card_product = view.findViewById(R.id.card_product);
            btnDelete = view.findViewById(R.id.btn_delete);
            btnEdit = view.findViewById(R.id.btn_edit);
        }
    }
}