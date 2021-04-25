package com.example.androidserver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.androidserver.API.UserAPI;
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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewUserList;
    private UserAdapter adapter;
    private List<User> userList;
    private long pressedTime;

    //private UserRequest request;
    public static String URL = "http://192.168.1.8:3000";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewUserList = findViewById(R.id.recyclerview_user_list);
        FloatingActionButton floatAdd = findViewById(R.id.float_button);
        floatAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ActivityInsertUser.class);
                startActivity(intent);
                finish();
            }
        });
        //request = new UserRequest();
        recyclerViewUserList.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
    }

    @Override
    protected void onStart() {
        super.onStart();
        setInitRecyclerViewUserList();
    }

    private void setInitRecyclerViewUserList(){
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
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
            public void onClickListener(long position) {
                ActivityInsertUser.TYPE = "INSERT";
                DeleteUserAsyncTask delete = new DeleteUserAsyncTask();
                delete.execute(adapter.getSingleUser((int)position)._id);
                adapter.remove((int)position);
            }
        });

        adapter.setUpdateListener(new Listener() {
            @Override
            public void onClickListener(long position) {
                ActivityInsertUser.TYPE = "UPDATE";
                User user = adapter.getSingleUser((int)position);
                Intent intent = new Intent(MainActivity.this, ActivityInsertUser.class);
                Bundle bundle = new Bundle();
                bundle.putString(ActivityInsertUser.EMAIL,user.getEmail());
                bundle.putString(ActivityInsertUser.PASSWORD,user.getPassword());
                bundle.putString(ActivityInsertUser.CONFIRMPASSWORD,user.getConfirmPassword());
                bundle.putString(ActivityInsertUser.FIRSTNAME,user.getFirstName());
                bundle.putString(ActivityInsertUser.LASTNAME,user.getLastName());
                bundle.putString(ActivityInsertUser._ID,user.get_id());
                intent.putExtra("KEY",bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
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
    }
}