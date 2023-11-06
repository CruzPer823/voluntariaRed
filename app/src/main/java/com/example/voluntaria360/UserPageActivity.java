package com.example.voluntaria360;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileNotFoundException;

public class UserPageActivity extends AppCompatActivity {

    SecondFragment secondFragment = new SecondFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);
        BottomNavigationView navigation = findViewById(R.id.barra_Menu);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Button uploadPic = findViewById(R.id.uploadPic);
        uploadPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 3);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null){
            Uri selectedImage = data.getData();
            ImageView imageView = findViewById(R.id.profilePic);
            Button uploadbtn = findViewById(R.id.uploadPic);
            Bitmap newImage = null;
            try {
                newImage = decodeUri(this.getApplicationContext(), selectedImage, 290);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            imageView.setImageBitmap(newImage);
            uploadbtn.setBackground(null);
        }
    }

    public static Bitmap decodeUri(Context c, Uri uri, final int requiredSize)
            throws FileNotFoundException {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;

            while (true) {
                if (width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2);
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
                startActivity(new Intent(getApplicationContext(), UserPageActivity.class));
                overridePendingTransition(0,0);
                return true;
            }
            return false;
        }

        public void loadFragment(Fragment fragment){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container_Menu,fragment);
            transaction.commit();
        }
    };

}