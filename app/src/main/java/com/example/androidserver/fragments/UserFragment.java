package com.example.androidserver.fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.example.androidserver.API.UserAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.adapter.UserAdapter;
import com.example.androidserver.asyncTask.DeleteUserAsyncTask;
import com.example.androidserver.listener.Listener;
import com.example.androidserver.models.User;
import com.example.androidserver.screen.ActivityInsertUser;
import com.example.androidserver.screen.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserFragment extends Fragment {
    public static String isUser = "USER";
    private RecyclerView recyclerViewUserList;
    private UserAdapter adapter;
    private List<User> userList;
    private long pressedTime;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewUserList = view.findViewById(R.id.recyclerview_user_list);
        FloatingActionButton floatAdd = view.findViewById(R.id.float_button);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ActivityInsertUser.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        //request = new UserRequest();
        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }

    @Override
    public void onStart() {
        super.onStart();
        setInitRecyclerViewUserList();
    }

    private void setInitRecyclerViewUserList(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.userList().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    userList = response.body();
                    adapter = new UserAdapter(userList);
                    recyclerViewUserList.setAdapter(adapter);
                    onClick();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.e("Error:",t.toString());
            }
        });
    }

    private void onClick(){
        adapter.setListener(new Listener() {
            @Override
            public void onClickListener(int position) {
                ActivityInsertUser.TYPE = "INSERT";
                DeleteUserAsyncTask delete = new DeleteUserAsyncTask();
                delete.execute(adapter.getSingleUser((int)position)._id);
                adapter.remove((int)position);
            }
        });

        adapter.setUpdateListener(new Listener() {
            @Override
            public void onClickListener(int position) {
                ActivityInsertUser.TYPE = "UPDATE";
                User user = adapter.getSingleUser((int)position);
                Intent intent = new Intent(getContext(), ActivityInsertUser.class);
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInsertUser.EMAIL,user.getEmail());
                bundle.putString(ActivityInsertUser.PASSWORD,user.getPassword());
                bundle.putString(ActivityInsertUser.CONFIRMPASSWORD,user.getConfirmPassword());
                bundle.putString(ActivityInsertUser.FIRSTNAME,user.getFirstName());
                bundle.putString(ActivityInsertUser.LASTNAME,user.getLastName());
                bundle.putString(ActivityInsertUser._ID,user.get_id());
                intent.putExtra("KEY",bundle);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    /*@Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Do you want to logout!", Toast.LENGTH_SHORT).show();
        }
        pressedTime = System.currentTimeMillis();
    }*/
}