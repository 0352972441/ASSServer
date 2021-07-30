package com.example.androidserver.screen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.androidserver.R;
import com.example.androidserver.fragments.ProductFragment;
import com.example.androidserver.fragments.UserFragment;
import com.google.android.material.navigation.NavigationView;

public class HomeScreen extends AppCompatActivity {
    public static String isCurrentFragment = "";
    private boolean doubleBackToExitPressedOnce = false;
    private FrameLayout mFrameLayout;
    private NavigationView mNavDraw;
    private static DrawerLayout mDrawerLayout;
    private long pressedTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        mFrameLayout = findViewById(R.id.m_frame_layout);
        mNavDraw = findViewById(R.id.m_nav_draw);
        mDrawerLayout  = findViewById(R.id.m_draw_layout);
        setUpNavigationDraw();
        setAnimationDrawLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavDraw.setCheckedItem(R.id.menu_product);
        loadFragment(new ProductFragment());
    }

    private void setUpNavigationDraw(){
        mNavDraw.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_product:
                        if(!ProductFragment.isProduct.equals(isCurrentFragment)){
                            mNavDraw.setCheckedItem(menuItem.getItemId());
                            loadFragment(new ProductFragment());
                        }
                        break;
                    case R.id.menu_user:
                        if(!UserFragment.isUser.equals(isCurrentFragment)){
                            mNavDraw.setCheckedItem(menuItem.getItemId());
                            loadFragment(new UserFragment());
                        }
                        break;
                    case R.id.menu_logout:
                        switchActivity(LoginActivity.class);
                        break;
                }
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
    }

    private void switchActivity(Object nameClass){
        Intent intent = new Intent(HomeScreen.this, (Class<?>) nameClass);
        startActivity(intent);
        //finish();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = (DrawerLayout)findViewById(R.id.m_draw_layout);
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    private void setAnimationDrawLayout(){
        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale
                final float diffScaleOffset = slideOffset * (1-0.7f);
                final float offsetScale = 1 - diffScaleOffset;
                mFrameLayout.setScaleX(offsetScale);
                mFrameLayout.setScaleY(offsetScale);
                // Transcation
                final float offset = drawerView.getWidth() * slideOffset;
                final float offsetDiff = drawerView.getWidth() * diffScaleOffset /2;
                mFrameLayout.setTranslationX(offset - offsetDiff);
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.m_frame_layout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
        mDrawerLayout.closeDrawers();
    }

    public static void openDraw(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

}