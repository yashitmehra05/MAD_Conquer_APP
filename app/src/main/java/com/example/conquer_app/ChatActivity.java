package com.example.conquer_app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {

    private String requestId;
    private String influencerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        requestId      = getIntent().getStringExtra("requestId");
        influencerName = getIntent().getStringExtra("influencerName");
        String handle  = getIntent().getStringExtra("influencerHandle");

        ((TextView) findViewById(R.id.tvChatName)).setText(influencerName);
        ((TextView) findViewById(R.id.tvChatHandle)).setText(handle);

        findViewById(R.id.btnChatBack).setOnClickListener(v -> finish());

        EditText etMessage = findViewById(R.id.etChatMessage);
        // Pre-fill automated message
        etMessage.setText("Hi! I'd love to collaborate with you. Please let me know if you're interested.");

        MaterialButton btnSend = findViewById(R.id.btnSendMessage);
        btnSend.setOnClickListener(v -> {
            String messageText = etMessage.getText().toString().trim();
            if (messageText.isEmpty()) {
                Toast.makeText(this, "Message cannot be empty", Toast.LENGTH_SHORT).show();
                return;
            }
            sendMessage(messageText);
        });
    }

    private void sendMessage(String messageText) {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Fetch brandName then save
        FirebaseDatabase.getInstance().getReference("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        String brandName = snapshot.child("name").getValue(String.class);
                        if (brandName == null) brandName = snapshot.child("brandName").getValue(String.class);

                        DatabaseReference msgRef = FirebaseDatabase.getInstance()
                                .getReference("messages").child(requestId).push();

                        Map<String, Object> msg = new HashMap<>();
                        msg.put("brandUid",       uid);
                        msg.put("brandName",      brandName);
                        msg.put("influencerName", influencerName);
                        msg.put("message",        messageText);
                        msg.put("timestamp",      System.currentTimeMillis());

                        msgRef.setValue(msg)
                                .addOnSuccessListener(unused -> {
                                    Toast.makeText(ChatActivity.this,
                                            "Message sent!", Toast.LENGTH_SHORT).show();
                                    finish();
                                })
                                .addOnFailureListener(e ->
                                        Toast.makeText(ChatActivity.this,
                                                "Failed: " + e.getMessage(),
                                                Toast.LENGTH_SHORT).show());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {}
                });
    }
}