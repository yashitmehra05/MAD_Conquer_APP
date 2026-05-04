package com.conquer.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conquer_app.LoginActivity;
import com.example.conquer_app.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnSettings = findViewById(R.id.btnSettings);
        View btnEditProfile = findViewById(R.id.btnEditProfile);
        LinearLayout itemAccountSettings = findViewById(R.id.itemAccountSettings);
        LinearLayout itemPrivacy = findViewById(R.id.itemPrivacy);
        LinearLayout itemHelp = findViewById(R.id.itemHelp);
        LinearLayout itemLogOut = findViewById(R.id.itemLogOut);

        btnBack.setOnClickListener(v -> finish());

        btnSettings.setOnClickListener(v ->
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show());

        btnEditProfile.setOnClickListener(v ->
                Toast.makeText(this, "Edit Profile", Toast.LENGTH_SHORT).show());

        itemAccountSettings.setOnClickListener(v ->
                Toast.makeText(this, "Account Settings", Toast.LENGTH_SHORT).show());

        itemPrivacy.setOnClickListener(v ->
                Toast.makeText(this, "Privacy & Security", Toast.LENGTH_SHORT).show());

        itemHelp.setOnClickListener(v ->
                Toast.makeText(this, "Help & Support", Toast.LENGTH_SHORT).show());

        itemLogOut.setOnClickListener(v -> {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}