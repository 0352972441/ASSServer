package com.example.androidserver.widgets;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackBar {
    private View view;
    public SnackBar(View view) {
        this.view = view;
    }

    public void showSnackBar(String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
