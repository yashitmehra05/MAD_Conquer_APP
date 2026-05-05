package com.example.conquer_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.conquer_app.adapters.MessageThreadAdapter;
import com.example.conquer_app.models.MessageThread;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;
import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private MessageThreadAdapter adapter;
    private List<MessageThread> allThreads = new ArrayList<>();
    private List<MessageThread> filteredThreads = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        RecyclerView rvMessages = findViewById(R.id.rvMessages);
        EditText etSearch = findViewById(R.id.etSearch);

        adapter = new MessageThreadAdapter(filteredThreads, thread -> {
            // Open chat screen
            android.content.Intent intent = new android.content.Intent(this, ChatActivity.class);
            intent.putExtra("requestId",       thread.getRequestId());
            intent.putExtra("influencerName",  thread.getName());
            intent.putExtra("influencerHandle",thread.getHandle());
            startActivity(intent);
        });

        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(adapter);

        loadFromFirebase();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterThreads(s.toString());
            }
        });
    }

    private void loadFromFirebase() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("collaboration_requests");

        ref.orderByChild("brandUid").equalTo(uid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        allThreads.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String requestId      = ds.getKey();
                            String influencerName = ds.child("influencerName").getValue(String.class);
                            String handle         = ds.child("influencerHandle").getValue(String.class);
                            String status         = ds.child("status").getValue(String.class);
                            String lastMsg        = "Status: " + (status != null ? status : "pending");

                            allThreads.add(new MessageThread(
                                    requestId, influencerName, handle, lastMsg,
                                    R.drawable.ic_avatar_placeholder));
                        }
                        filteredThreads.clear();
                        filteredThreads.addAll(allThreads);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        Toast.makeText(MessagesActivity.this,
                                "Failed to load: " + error.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void filterThreads(String query) {
        filteredThreads.clear();
        if (query.isEmpty()) {
            filteredThreads.addAll(allThreads);
        } else {
            String lower = query.toLowerCase();
            for (MessageThread t : allThreads) {
                if (t.getName().toLowerCase().contains(lower)
                        || t.getHandle().toLowerCase().contains(lower)) {
                    filteredThreads.add(t);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}