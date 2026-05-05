package com.example.conquer_app;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import java.util.HashMap;
import java.util.Map;

public class InfluencerDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_influencer_detail);

        String name      = getIntent().getStringExtra("name");
        String handle    = getIntent().getStringExtra("handle");
        String location  = getIntent().getStringExtra("location");
        String category  = getIntent().getStringExtra("category");
        String followers = getIntent().getStringExtra("followers");

        ((TextView) findViewById(R.id.tvDetailName)).setText(name);
        ((TextView) findViewById(R.id.tvDetailHandle)).setText(handle);
        ((TextView) findViewById(R.id.tvDetailLocation)).setText("📍 " + location);
        ((TextView) findViewById(R.id.tvDetailCategory)).setText(category);
        ((TextView) findViewById(R.id.tvDetailFollowers)).setText(followers + " Followers");

        findViewById(R.id.btnDetailBack).setOnClickListener(v -> finish());

        MaterialButton btnCollab = findViewById(R.id.btnCollab);
        btnCollab.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setTitle("Request Collaboration")
                    .setMessage("Send a collaboration request to " + name + " (" + handle + ")?")
                    .setPositiveButton("Send", (dialog, which) -> sendCollabRequest(name, handle, category, followers))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void sendCollabRequest(String influencerName, String handle,
                                   String category, String followers) {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "You must be logged in.", Toast.LENGTH_SHORT).show();
            return;
        }

        String brandUid = user.getUid();
        DatabaseReference usersRef = FirebaseDatabase.getInstance()
                .getReference("users").child(brandUid);

        // Fetch brandName from Firebase users node
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                String brandName = snapshot.child("name").getValue(String.class);
                if (brandName == null) brandName = snapshot.child("brandName").getValue(String.class);

                // Build the request object
                DatabaseReference requestsRef = FirebaseDatabase.getInstance()
                        .getReference("collaboration_requests");
                String requestId = requestsRef.push().getKey();

                Map<String, Object> request = new HashMap<>();
                request.put("brandUid",        brandUid);
                request.put("brandName",        brandName);
                request.put("influencerName",   influencerName);
                request.put("influencerHandle", handle);
                request.put("category",         category);
                request.put("followers",        followers);
                request.put("status",           "pending");
                request.put("timestamp",        System.currentTimeMillis());

                requestsRef.child(requestId).setValue(request)
                        .addOnSuccessListener(unused ->
                                Toast.makeText(InfluencerDetailActivity.this,
                                        "Request sent to " + influencerName + "!",
                                        Toast.LENGTH_SHORT).show())
                        .addOnFailureListener(e ->
                                Toast.makeText(InfluencerDetailActivity.this,
                                        "Failed: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(InfluencerDetailActivity.this,
                        "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}