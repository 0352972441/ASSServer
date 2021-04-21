package com.example.androidserver.asyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.androidserver.API.UserAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateUserAsyncTask extends AsyncTask<Object,Void,Void> {
    @Override
    protected Void doInBackground(Object... data) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.update((String)data[0],(User)data[1]).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Log.d("INFO","Update succesfully!");
                }else{
                    Log.d("INFO","Update Fail!");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Error",t.toString());
            }
        });
        return null;
    }
}
