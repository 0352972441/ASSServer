package com.example.androidserver.screen;

import android.content.Intent;
import android.os.Bundle;

import com.example.androidserver.API.UserAPI;
import com.example.androidserver.Account;
import com.example.androidserver.MainActivity;
import com.example.androidserver.models.User;
import com.example.androidserver.widgets.SnackBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidserver.R;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText edEmail,edPassword;
    private Button btnLogin;
    private TextView tvTitle,tvSwitch;
    private SnackBar snackBar;
    private Intent intent;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        edEmail = findViewById(R.id.medemail);
        edPassword = findViewById(R.id.medpassword);
        btnLogin = findViewById(R.id.mbtnLg);
        snackBar = new SnackBar(edEmail);
        tvSwitch = findViewById(R.id.mtvchange);
        login();
        tvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this,ActivitySignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void login(){
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create()).build();
                UserAPI userAPI = retrofit.create(UserAPI.class);
                userAPI.login(new User(email,password)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Account.getAccountInstance().setAccount(response.body());
                            intent = new Intent(LoginActivity.this,HomeScreen.class);
                            startActivity(intent);
                            finish();
                            snackBar.showSnackBar("Đăng nhập thành công");
                        }else{
                            snackBar.showSnackBar("Đăng nhập thất bại!");
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        snackBar.showSnackBar("Đăng nhập thất bại !");
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(getBaseContext(), "Press again exit !", Toast.LENGTH_SHORT).show();
            /*intent = new Intent(LoginActivity.this,ActivitySignUp.class);
            startActivity(intent);*/
        }
        pressedTime = System.currentTimeMillis();
    }
}