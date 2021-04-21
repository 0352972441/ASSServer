package com.example.androidserver.asyncTask;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.androidserver.API.UserAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InsertUserAsyncTask extends AsyncTask<User,Void,Void> {
    private ProgressBar progressBar;

    public InsertUserAsyncTask(ProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    @Override
    protected Void doInBackground(User... users) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.insertUser(users[0]).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    onProgressUpdate();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.d("Error",t.toString());
            }
        });
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
