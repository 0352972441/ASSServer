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
import com.example.androidserver.models.Product;
import com.example.androidserver.models.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    public List<Cart> cartList = new ArrayList<>();
    private Listener deleteListener;
    private Listener AddListener;

    public void setDeleteListener(Listener deleteListener) {
        this.deleteListener = deleteListener;
    }

    public void setAddListener(Listener addListener) {
        AddListener = addListener;
    }

    public CartAdapter(List<Cart> cartList) {
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart,parent,false);
        return new CartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if(cartList != null){
            holder.tv_price.setText(String.valueOf(cartList.get(position).getPrice() * cartList.get(position).getNum()));
            holder.tv_name.setText(cartList.get(position).getName());
            Picasso.with(holder.img_product.getContext()).load(cartList.get(position).getImage()).into(holder.img_product);
            holder.tvNum.setText(cartList.get(position).getNum()+"");
            holder.tvAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartList.get(position).setNum(cartList.get(position).getNum()+1);
                    holder.tvNum.setText(cartList.get(position).getNum()+"");
                    holder.tv_price.setText(String.valueOf(cartList.get(position).getPrice() * cartList.get(position).getNum()));
                    AddListener.onClickListener(position);
                }
            });

            holder.tvRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cartList.get(position).setNum(cartList.get(position).getNum()-1);
                    holder.tvNum.setText(cartList.get(position).getNum()+"");
                    holder.tv_price.setText(String.valueOf(cartList.get(position).getPrice() * cartList.get(position).getNum()));
                    deleteListener.onClickListener(position);
                }
            });

        }
    }

    public int total(){
        int total = 0;
        for(Cart i : cartList){
            total += i.getNum() * i.getPrice();
        }
        return total;
    }

    public Cart getSingleProduct(int position){
        return cartList.get(position);
    }
    public void deleteProduct(int position){
        cartList.remove(getSingleProduct(position));
    }



    @Override
    public int getItemCount() {
        return cartList != null ? cartList.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_product;
        private TextView tv_price,tv_name,tvAdd,tvRemove,tvNum;
        public ViewHolder(@NonNull View view) {
            super(view);
            tv_name = view.findViewById(R.id.tv_name_cart);
            tv_price = view.findViewById(R.id.tv_price_cart);
            img_product = view.findViewById(R.id.img_cart);
            tvAdd = view.findViewById(R.id.inc);
            tvNum = view.findViewById(R.id.ed_cart);
            tvRemove = view.findViewById(R.id.dec);
        }
    }
}