package com.example.flavormap.ui;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flavormap.R;

public class AboutUsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        if (getSupportActionBar() != null) {

            getSupportActionBar().hide();
        }
        ImageView backBtn = findViewById(R.id.btnBackAbout);
        backBtn.setOnClickListener(v -> {
            finish();
        });

    }

}