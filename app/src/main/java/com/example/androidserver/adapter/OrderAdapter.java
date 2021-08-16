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
import com.example.androidserver.models.Cart;
import com.example.androidserver.models.Order;
import com.example.androidserver.models.Product;
import com.example.androidserver.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    public List<Order> orderList = new ArrayList<>();

    public OrderAdapter(List<Order> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(orderList != null){
            holder.tv_total.setText(String.valueOf(orderList.get(position).getTotal()) + " Đồng");
            holder.tv_order.setText(orderList.get(position).getName());
        }
    }


    public Order getSingleProduct(int position){
        return orderList.get(position);
    }
    public void deleteProduct(int position){
        orderList.remove(getSingleProduct(position));
    }



    @Override
    public int getItemCount() {
        return orderList != null ? orderList.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_total,tv_order;
        public ViewHolder(@NonNull View view) {
            super(view);
            tv_order = view.findViewById(R.id.tv_order);
            tv_total = view.findViewById(R.id.tv_total);
        }
    }
}