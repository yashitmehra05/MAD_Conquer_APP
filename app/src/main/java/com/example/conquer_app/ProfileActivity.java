package com.example.conquer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        ImageButton btnBack        = findViewById(R.id.btnBack);
        ImageButton btnSettings    = findViewById(R.id.btnSettings);
        View btnEditProfile        = findViewById(R.id.btnEditProfile);
        LinearLayout itemAccountSettings = findViewById(R.id.itemAccountSettings);
        LinearLayout itemPrivacy   = findViewById(R.id.itemPrivacy);
        LinearLayout itemHelp      = findViewById(R.id.itemHelp);
        LinearLayout itemLogOut    = findViewById(R.id.itemLogOut);
        TextView tvName            = findViewById(R.id.tvProfileName);
        TextView tvEmail           = findViewById(R.id.tvProfileEmail);

        // Load brand name + email from Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            tvEmail.setText(user.getEmail());

            FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.getUid())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            String brandName = snapshot.child("name").getValue(String.class);
                            if (brandName == null) brandName = snapshot.child("brandName").getValue(String.class);
                            tvName.setText(brandName != null ? brandName : "Your Brand");
                        }
                        @Override
                        public void onCancelled(DatabaseError error) {
                            tvName.setText("Your Brand");
                        }
                    });
        }

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
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}