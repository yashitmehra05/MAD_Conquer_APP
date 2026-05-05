package com.example.conquer_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.conquer_app.adapters.MessageThreadAdapter;
import com.example.conquer_app.models.MessageThread;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView rvMessages;
    private EditText etSearch;
    private MessageThreadAdapter adapter;
    private List<MessageThread> allThreads = new ArrayList<>();
    private List<MessageThread> filteredThreads = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        // Back button
        findViewById(R.id.ivBack).setOnClickListener(v -> finish());

        rvMessages = findViewById(R.id.rvMessages);
        etSearch = findViewById(R.id.etSearch);

        // Seed dummy data
        allThreads.add(new MessageThread("Ankit Jha", "@jha.007", "When can we start?", R.drawable.ic_avatar_placeholder));
        allThreads.add(new MessageThread("Harsh", "@_yolo", "I've reviewed the contract", R.drawable.ic_avatar_placeholder));
        allThreads.add(new MessageThread("Priya Sharma", "@priya.creates", "Sounds great! I'll send the draft.", R.drawable.ic_avatar_placeholder));
        allThreads.add(new MessageThread("Ravi Mehta", "@ravimehta", "Let me check my schedule.", R.drawable.ic_avatar_placeholder));
        allThreads.add(new MessageThread("Neha Kapoor", "@nehakapoor", "Thanks for reaching out!", R.drawable.ic_avatar_placeholder));

        filteredThreads.addAll(allThreads);

        adapter = new MessageThreadAdapter(filteredThreads);
        rvMessages.setLayoutManager(new LinearLayoutManager(this));
        rvMessages.setAdapter(adapter);

        // Search / filter
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterThreads(s.toString());
            }
            @Override public void afterTextChanged(Editable s) {}
        });
    }

    private void filterThreads(String query) {
        filteredThreads.clear();
        if (query.isEmpty()) {
            filteredThreads.addAll(allThreads);
        } else {
            String lower = query.toLowerCase();
            for (MessageThread thread : allThreads) {
                if (thread.getName().toLowerCase().contains(lower)
                        || thread.getHandle().toLowerCase().contains(lower)
                        || thread.getLastMessage().toLowerCase().contains(lower)) {
                    filteredThreads.add(thread);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }
}