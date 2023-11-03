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
        ViewPager viewPager = findViewById(R.id.fragmentcontainer);
        tabLayout=findViewById(R.id.include);

        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
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
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}