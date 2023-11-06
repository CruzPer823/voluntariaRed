package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BadgesActivity extends AppCompatActivity {
    FirstFragment firstFragment = new FirstFragment();
    SecondFragment secondFragment = new SecondFragment();
    ThirdFragment thirdFragment = new ThirdFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges);

        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        startActivity(new Intent(getApplicationContext(), EventoFragmentActivity.class));
        overridePendingTransition(0,0);
    }
    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            int itemId = item.getItemId();
            if (itemId == R.id.firstFragment) {
                startActivity(new Intent(getApplicationContext(), EventoFragmentActivity.class));
                overridePendingTransition(0,0);
                return true;
            } else if (itemId == R.id.secondFragment) {
                loadFragment(secondFragment);
                return true;
            } else if (itemId == R.id.thirdFragment) {
                loadFragment(thirdFragment);
                return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_Menu,fragment);
        transaction.commit();
    }
}
