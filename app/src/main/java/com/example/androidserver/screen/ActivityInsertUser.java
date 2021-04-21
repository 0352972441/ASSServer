package com.example.androidserver.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.androidserver.MainActivity;
import com.example.androidserver.R;
import com.example.androidserver.asyncTask.InsertUserAsyncTask;
import com.example.androidserver.asyncTask.UpdateUserAsyncTask;
import com.example.androidserver.models.User;
import com.example.androidserver.validator.UserValidator;
import com.example.androidserver.widgets.SnackBar;


public class ActivityInsertUser extends AppCompatActivity {
    private EditText edFirstName,edLastName,edEmail,edPassword,edConfirmPassword;
    private Button btnConfirm;
    private UserValidator validate;
    private SnackBar snackbar;
    private ProgressBar progressBar;
    public static String EMAIL = "EMAIL";
    public static String FIRSTNAME = "FIRSTNAME";
    public static String LASTNAME = "LASTNAME";
    public static String PASSWORD = "PASSWORD";
    public static String CONFIRMPASSWORD = "CONFIRMPASSWORD";
    public static String _ID = "_ID";
    public static String TYPE = "INSERT";
    private String _password;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_user);
        validate = new UserValidator();
        edEmail = findViewById(R.id.ed_email);
        edFirstName = findViewById(R.id.ed_first_name);
        edLastName = findViewById(R.id.ed_last_name);
        edPassword = findViewById(R.id.ed_passwrod);
        edConfirmPassword = findViewById(R.id.ed_confirm_password);
        progressBar = findViewById(R.id.progrssbar);
        btnConfirm = findViewById(R.id.btn_add);
        onClickButton();
        if(TYPE.equals("UPDATE")){
            Intent intent = getIntent();
            bundle = intent.getExtras().getBundle("KEY");
            String confirmPassword =(String)bundle.get(ActivityInsertUser.CONFIRMPASSWORD);
            _password = bundle.getString(ActivityInsertUser.PASSWORD);
            String email = bundle.getString(ActivityInsertUser.EMAIL);
            String firstName = bundle.getString(ActivityInsertUser.FIRSTNAME);
            String lastName = bundle.getString(ActivityInsertUser.LASTNAME);
            edConfirmPassword.setText(confirmPassword);
            edEmail.setText(email);
            edFirstName.setText(firstName);
            edLastName.setText(lastName);
            edPassword.setText(_password);
        }
    }

    private void onClickButton(){
        snackbar = new SnackBar(edEmail);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
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
                }
                if(TYPE.equals("UPDATE")){
                    if(!password.equals(_password)){
                        if(validate.passwordValidator(password)){
                            snackbar.showSnackBar("Password too short");
                            return;
                        }else if(!password.equals(confirmPassword)){
                            snackbar.showSnackBar("Password doesn't match");
                            return;
                        }
                    }
                }else {
                    if(validate.passwordValidator(password)){
                        snackbar.showSnackBar("Password too short");
                        return;
                    }else if(!password.equals(confirmPassword)){
                        snackbar.showSnackBar("Password doesn't match");
                        return;
                    }
                }
                progressBar.setVisibility(View.VISIBLE);
                User user = new User(email,password,firstName,lastName,confirmPassword);
                if(TYPE.equals("INSERT")){
                    new InsertUserAsyncTask(progressBar).execute(user);
                }else{
                    new UpdateUserAsyncTask().execute(bundle.getString(ActivityInsertUser._ID),user);
                }
                TYPE = "INSERT";
                Intent intent = new Intent(ActivityInsertUser.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ActivityInsertUser.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}