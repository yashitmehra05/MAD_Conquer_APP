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

    private TextView tvUserName, tvCampaigns, tvInfluencers, tvRoi, tvViewAll;
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
        tvRoi = findViewById(R.id.tvRoi);
        tvViewAll = findViewById(R.id.tvViewAll);

        actionFindInfluencers = findViewById(R.id.actionFindInfluencers);
        actionNewCampaign = findViewById(R.id.actionNewCampaign);
        actionMessages = findViewById(R.id.actionMessages);
        actionAnalytics = findViewById(R.id.actionAnalytics);

        campaignContainer = findViewById(R.id.campaignContainer);
    }

    private void setupStats() {
        tvUserName.setText("Kartik Sharma");
        tvCampaigns.setText("2");
        tvInfluencers.setText("7");
        tvRoi.setText("3.2x");
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
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(new Campaign("Summer Collection 2026", 5, "₹50,000", "2 days ago"));
        campaigns.add(new Campaign("Product Launch", 2, "₹35,500", "20 days ago"));

        for (Campaign c : campaigns) {
            View card = LayoutInflater.from(this)
                    .inflate(R.layout.item_campaign, campaignContainer, false);

            ((TextView) card.findViewById(R.id.tvCampaignName)).setText(c.name);
            ((TextView) card.findViewById(R.id.tvInfluencerCount)).setText(c.influencerCount + " influencers");
            ((TextView) card.findViewById(R.id.tvBudget)).setText(c.budget);
            ((TextView) card.findViewById(R.id.tvTimeAgo)).setText(c.timeAgo);

            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, CampaignDetailsActivity.class);
                intent.putExtra("campaign_name", c.name);
                startActivity(intent);
            });

            campaignContainer.addView(card);
        }
    }

    private void setupBottomNav() {
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) return true;
            if (id == R.id.nav_discover) {
                Toast.makeText(this, "Discover tapped", Toast.LENGTH_SHORT).show();
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
    }}