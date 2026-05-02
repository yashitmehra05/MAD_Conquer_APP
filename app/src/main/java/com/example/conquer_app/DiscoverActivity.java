package com.example.conquer_app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {

    private InfluencerAdapter adapter;
    private TextView tvResultsCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        tvResultsCount = findViewById(R.id.tv_results_count);
        EditText etSearch = findViewById(R.id.et_search);
        RecyclerView recyclerView = findViewById(R.id.rv_influencers);

        List<Influencer> influencerList = getDummyInfluencers();
        adapter = new InfluencerAdapter(influencerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        updateCount(influencerList.size());

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString().trim());
                updateCount(adapter.getItemCount());
            }
        });
    }

    private void updateCount(int count) {
        tvResultsCount.setText("Showing " + count + " influencer" + (count != 1 ? "s" : ""));
    }

    private List<Influencer> getDummyInfluencers() {
        List<Influencer> list = new ArrayList<>();
        list.add(new Influencer("Aisha Sharma",  "@aisha.style",     "Mumbai, IN",     "Fashion",     "1.2M"));
        list.add(new Influencer("Rohan Mehta",   "@rohanfitness",    "Delhi, IN",      "Fitness",     "890K"));
        list.add(new Influencer("Priya Kapoor",  "@priyaeats",       "Bangalore, IN",  "Food",        "540K"));
        list.add(new Influencer("Dev Malhotra",  "@devtech",         "Hyderabad, IN",  "Tech",        "2.1M"));
        list.add(new Influencer("Neha Gupta",    "@nehatravels",     "Jaipur, IN",     "Travel",      "320K"));
        list.add(new Influencer("Kabir Singh",   "@kabircreates",    "Pune, IN",       "Art",         "175K"));
        list.add(new Influencer("Zara Ahmed",    "@zarabeauty",      "Chennai, IN",    "Beauty",      "760K"));
        list.add(new Influencer("Arjun Nair",    "@arjungaming",     "Kochi, IN",      "Gaming",      "1.5M"));
        list.add(new Influencer("Simran Kaur",   "@simranlifestyle",  "Chandigarh, IN","Lifestyle",   "430K"));
        list.add(new Influencer("Vikram Bose",   "@vikramphoto",     "Kolkata, IN",    "Photography", "290K"));
        return list;
    }
}