package com.example.androidserver.validator;

import android.util.Log;

public class UserValidator {
    public boolean emptyValidator(String ...agr){
        for (String s : agr) {
            if(s.isEmpty()){
                Log.d("aa","Rỗng");
                return true;
            }
        }
        Log.d("aa","Không rỗng");
        return false;
    }

    public boolean emailValidator(String email){
        return !email.contains("@");
    }

    public boolean passwordValidator(String password){
        return (password.length() <7);
    }
}
