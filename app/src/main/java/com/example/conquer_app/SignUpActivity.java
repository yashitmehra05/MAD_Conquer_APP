package com.example.conquer_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.LinearLayout;
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
            Intent intent = new Intent(SignUpActivity.this, RegisterActivity.class);
            intent.putExtra("account_type", "Brand");
            startActivity(intent);
        });

        cardInfluencer.setOnClickListener(v -> {
            Intent intent = new Intent(SignUpActivity.this, RegisterActivity.class);
            intent.putExtra("account_type", "Influencer");
            startActivity(intent);
        });
    }
}