package com.example.voluntaria360;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class MainFeedActivity extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem meventos, manuncios;
    PagerAdapter pagerAdapter;
    Toolbar mtoolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        meventos = findViewById(R.id.eventos);
        manuncios = findViewById(R.id.anuncios);
        tabLayout=findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0||tab.getPosition()==1){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}