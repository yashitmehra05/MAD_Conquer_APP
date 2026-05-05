package com.example.conquer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
public class HomeActivity extends AppCompatActivity {

    static class Campaign {
        String name;
        int influencerCount;
        String budget;
        String timeAgo;

        Campaign(String name, int influencerCount, String budget, String timeAgo) {
            this.name = name;
            this.influencerCount = influencerCount;
            this.budget = budget;
            this.timeAgo = timeAgo;
        }
    }

    private TextView tvUserName, tvCampaigns, tvInfluencers, tvViewAll;
    private LinearLayout actionFindInfluencers, actionNewCampaign, actionMessages, actionAnalytics;
    private LinearLayout campaignContainer;
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bindViews();
        setupStats();
        setupQuickActions();
        setupCampaigns();
        setupBottomNav();
    }

    private void bindViews() {
        tvUserName = findViewById(R.id.tvUserName);
        tvCampaigns = findViewById(R.id.tvCampaigns);
        tvInfluencers = findViewById(R.id.tvInfluencers);
        tvViewAll = findViewById(R.id.tvViewAll);

        actionFindInfluencers = findViewById(R.id.actionFindInfluencers);
        actionNewCampaign = findViewById(R.id.actionNewCampaign);
        actionMessages = findViewById(R.id.actionMessages);
        actionAnalytics = findViewById(R.id.actionAnalytics);

        campaignContainer = findViewById(R.id.campaignContainer);
    }

    private void setupStats() {
        // Fetch real user name from Firebase
        com.google.firebase.auth.FirebaseUser user =
                com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            com.google.firebase.database.FirebaseDatabase.getInstance()
                    .getReference("users")
                    .child(user.getUid())
                    .get()
                    .addOnSuccessListener(snapshot -> {
                        String name = snapshot.child("name").getValue(String.class);
                        if (name == null) name = snapshot.child("brandName").getValue(String.class);
                        if (name != null && !name.isEmpty()) {
                            tvUserName.setText(name);
                        } else {
                            tvUserName.setText("User");
                        }
                    })
                    .addOnFailureListener(e -> tvUserName.setText("User"));

        } else {
            tvUserName.setText("User");
        }

        tvCampaigns.setText("2");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("collaboration_requests")
                .orderByChild("brandUid").equalTo(uid)
                .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                        tvInfluencers.setText(String.valueOf(snapshot.getChildrenCount()));
                    }
                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError error) {}
                });
    }

    private void setupQuickActions() {

        actionFindInfluencers.setOnClickListener(v ->
                startActivity(new Intent(this, DiscoverActivity.class))
        );

        actionNewCampaign.setOnClickListener(v ->
                startActivity(new Intent(this, NewCampaignActivity.class))
        );

        actionMessages.setOnClickListener(v ->
                startActivity(new Intent(this, MessagesActivity.class))
        );

        actionAnalytics.setOnClickListener(v ->
                Toast.makeText(this, "Analytics coming soon!", Toast.LENGTH_SHORT).show()
        );

        tvViewAll.setOnClickListener(v ->
                startActivity(new Intent(this, DiscoverActivity.class))
        );
    }

    private void setupCampaigns() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance()
                .getReference("campaigns").child(uid)
                .orderByChild("timestamp")
                .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot snapshot) {
                        campaignContainer.removeAllViews();
                        int count = 0;
                        // iterate in reverse so newest shows first
                        java.util.List<com.google.firebase.database.DataSnapshot> list = new java.util.ArrayList<>();
                        for (com.google.firebase.database.DataSnapshot ds : snapshot.getChildren()) {
                            list.add(ds);
                        }
                        java.util.Collections.reverse(list);

                        for (com.google.firebase.database.DataSnapshot ds : list) {
                            String campaignId   = ds.getKey();
                            String name         = ds.child("name").getValue(String.class);
                            String budget       = ds.child("budget").getValue(String.class);
                            String description  = ds.child("description").getValue(String.class);
                            String deliverables = ds.child("deliverables").getValue(String.class);
                            String startDate    = ds.child("startDate").getValue(String.class);
                            String endDate      = ds.child("endDate").getValue(String.class);
                            Long timestamp      = ds.child("timestamp").getValue(Long.class);

                            String timeAgo = getTimeAgo(timestamp);

                            android.view.View card = android.view.LayoutInflater.from(HomeActivity.this)
                                    .inflate(R.layout.item_campaign, campaignContainer, false);

                            ((android.widget.TextView) card.findViewById(R.id.tvCampaignName)).setText(name);
                            ((android.widget.TextView) card.findViewById(R.id.tvBudget)).setText(budget != null ? "₹" + budget : "N/A");
                            ((android.widget.TextView) card.findViewById(R.id.tvTimeAgo)).setText(timeAgo);
                            ((android.widget.TextView) card.findViewById(R.id.tvInfluencerCount)).setText("Active");

                            String finalName        = name;
                            String finalBudget      = budget;
                            String finalDescription = description;
                            String finalDeliverables= deliverables;
                            String finalStartDate   = startDate;
                            String finalEndDate     = endDate;

                            card.setOnClickListener(v -> {
                                Intent intent = new Intent(HomeActivity.this, CampaignDetailsActivity.class);
                                intent.putExtra("campaign_title",       finalName);
                                intent.putExtra("campaign_budget",      finalBudget);
                                intent.putExtra("campaign_description", finalDescription);
                                intent.putExtra("campaign_deliverables",finalDeliverables);
                                intent.putExtra("campaign_duration",    finalStartDate + " – " + finalEndDate);
                                startActivity(intent);
                            });

                            campaignContainer.addView(card);
                            count++;
                        }
                        tvCampaigns.setText(String.valueOf(count));
                    }

                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError error) {
                        Toast.makeText(HomeActivity.this,
                                "Failed to load campaigns", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getTimeAgo(Long timestamp) {
        if (timestamp == null) return "";
        long diff = System.currentTimeMillis() - timestamp;
        long days = diff / (1000 * 60 * 60 * 24);
        if (days == 0) return "Today";
        if (days == 1) return "1 day ago";
        return days + " days ago";
    }

    private void setupBottomNav() {
        bottomNav = findViewById(R.id.bottomNav);

        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) return true;

            if (id == R.id.nav_discover) {
                startActivity(new Intent(this, DiscoverActivity.class));
                return true;
            }

            if (id == R.id.nav_messages) {
                startActivity(new Intent(this, MessagesActivity.class));
                return true;
            }

            if (id == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                return true;
            }

            return false;
        });
    }
}