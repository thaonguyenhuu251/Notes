package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class profile_details extends AppCompatActivity {
    ImageView backProfile;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_details);
        backProfile = findViewById(R.id.backProfile);
        backProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_profile();
            }
        });
    }

    private void back_profile() {
//        Intent intent = new Intent(profile_details.this, Profile.class);
//        startActivity(intent);
        finish();
    }
}