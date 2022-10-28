package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Profile extends AppCompatActivity {
    EditText edtProfileDitls;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        edtProfileDitls = findViewById(R.id.edtProfilDetails);
        edtProfileDitls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this, profile_details.class);
                startActivity(intent);
            }
        });
    }
}