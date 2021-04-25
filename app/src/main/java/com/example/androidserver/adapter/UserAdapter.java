package com.example.androidserver.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidserver.R;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    public List<User> userList = new ArrayList<>();
    private Listener listener;
    private Listener updateListener;
    public UserAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_view_user,parent,false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(userList != null){
            holder.tv_User.setText(userList.get(position).getLastName() +" "+ userList.get(position).getFirstName());
            holder.img_Edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateListener.onClickListener(position);
                }
            });
            holder.img_Delete.setOnClickListener(new View.OnClickListener() {
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

    public void setUpdateListener(Listener updateListener) {
        this.updateListener = updateListener;
    }

    public void remove(int position){
        if(userList.get(position) != null){
            userList.remove(position);
            notifyDataSetChanged();
        }
    }

    public User getSingleUser(int position){
        notifyDataSetChanged();
        return userList.get(position);
    }

    @Override
    public int getItemCount() {
        return userList != null ? userList.size(): 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img_Delete,img_Edit;
        private TextView tv_User;
        public ViewHolder(@NonNull View view) {
            super(view);
            img_Delete = view.findViewById(R.id.img_delete);
            img_Edit = view.findViewById(R.id.img_edit);
            tv_User = view.findViewById(R.id.user_name);
        }
    }
}
