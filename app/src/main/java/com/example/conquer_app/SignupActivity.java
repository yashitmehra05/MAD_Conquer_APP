package com.conquer.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ImageButton btnBack = findViewById(R.id.btnBack);
        LinearLayout cardBrand = findViewById(R.id.cardBrand);
        LinearLayout cardInfluencer = findViewById(R.id.cardInfluencer);

        btnBack.setOnClickListener(v -> finish());

        cardBrand.setOnClickListener(v -> {
            Toast.makeText(this, "Signed up as Brand", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        });

        cardInfluencer.setOnClickListener(v -> {
            Toast.makeText(this, "Signed up as Influencer", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
            startActivity(intent);
        });
    }
}