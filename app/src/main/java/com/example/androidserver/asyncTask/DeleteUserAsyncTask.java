package com.example.androidserver.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.androidserver.API.UserAPI;
import com.example.androidserver.MainActivity;
import com.example.androidserver.adapter.UserAdapter;
import com.example.androidserver.models.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DeleteUserAsyncTask extends AsyncTask<String,Void,Void>{
    @Override
    protected Void doInBackground(String... id) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(MainActivity.URL).addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        userAPI.deleteUser(id[0]).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(context, "Delete successfuly", Toast.LENGTH_SHORT).show();
                    Log.d("Delete:","Delete thành công");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Error",t.toString());
            }
        });
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
