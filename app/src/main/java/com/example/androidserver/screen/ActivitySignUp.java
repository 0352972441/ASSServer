package com.example.androidserver.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidserver.R;
import com.example.androidserver.asyncTask.InsertUserAsyncTask;
import com.example.androidserver.models.User;
import com.example.androidserver.validator.UserValidator;
import com.example.androidserver.widgets.SnackBar;
import com.google.android.material.textfield.TextInputEditText;

public class ActivitySignUp extends AppCompatActivity {
    private TextInputEditText edEmail,edPassword,edFirstName,edLastName,edConfirmPassword;
    private Button btnLogin;
    private TextView tvTitle,tvSwitch;
    private ProgressBar progressBar;
    private SnackBar snackbar;
    private UserValidator validate;
    private Intent intent;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        edEmail = findViewById(R.id.medemail);
        edPassword = findViewById(R.id.medpassword);
        btnLogin = findViewById(R.id.mbtnLg);
        edConfirmPassword = findViewById(R.id.medconfirmpassword);
        edFirstName = findViewById(R.id.medfirstname);
        edLastName = findViewById(R.id.medlastname);
        tvSwitch = findViewById(R.id.mtvchange);
        progressBar = findViewById(R.id.progrssbar);
        validate = new UserValidator();
        signup();
        tvSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(ActivitySignUp.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void signup(){
        snackbar = new SnackBar(edEmail);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString().trim();
                String firstName = edFirstName.getText().toString().trim();
                String lastName = edLastName.getText().toString().trim();
                String password = edPassword.getText().toString().trim();
                String confirmPassword = edConfirmPassword.getText().toString().trim();
                if(validate.emptyValidator(email,firstName,lastName,password,confirmPassword)){
                    snackbar.showSnackBar("Invalid value!");
                    return;
                }else if(validate.emailValidator(email)){
                    snackbar.showSnackBar("Invalid email!");
                    return;
                }else if(validate.passwordValidator(password)){
                    snackbar.showSnackBar("Password too short");
                    return;
                }else if(!password.equals(confirmPassword)){
                    snackbar.showSnackBar("Password doesn't match");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                User user = new User(email,password,firstName,lastName,confirmPassword);
                new InsertUserAsyncTask(progressBar).execute(user);
                intent = new Intent(ActivitySignUp.this, LoginActivity.class);
                startActivity(intent);
                finish();
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
//            intent = new Intent(ActivitySignUp.this,LoginActivity.class);
//            startActivity(intent);
        }
        pressedTime = System.currentTimeMillis();
    }
}