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

    // ── Inner model ─────────────────────────────────────────────
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

    // ── Views ───────────────────────────────────────────────────
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

    // ── Bind Views ──────────────────────────────────────────────
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

    // ── Stats ───────────────────────────────────────────────────
    private void setupStats() {
        tvUserName.setText("Kartik Sharma");
        tvCampaigns.setText("2");
        tvInfluencers.setText("7");
        tvRoi.setText("3.2x");
    }

    // ── Quick Actions ───────────────────────────────────────────
    private void setupQuickActions() {

        actionFindInfluencers.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, DiscoverActivity.class));
        });

        // ✅ NEW CAMPAIGN COMMAND (as required)
        actionNewCampaign.setOnClickListener(v -> {
            startActivity(new Intent(this, com.example.conquer_app.NewCampaignActivity.class));
        });

        actionMessages.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MessagesActivity.class));
        });

        actionAnalytics.setOnClickListener(v ->
                Toast.makeText(this, "Analytics coming soon!", Toast.LENGTH_SHORT).show()
        );

        tvViewAll.setOnClickListener(v ->
                Toast.makeText(this, "View all campaigns", Toast.LENGTH_SHORT).show()
        );
    }

    // ── Campaigns ───────────────────────────────────────────────
    private void setupCampaigns() {
        List<Campaign> campaigns = new ArrayList<>();
        campaigns.add(new Campaign("Summer Collection 2026", 5, "₹50,000", "2 days ago"));
        campaigns.add(new Campaign("Product Launch", 2, "₹35,500", "20 days ago"));

        for (Campaign c : campaigns) {
            View card = LayoutInflater.from(this)
                    .inflate(R.layout.item_campaign, campaignContainer, false);

            ((TextView) card.findViewById(R.id.tvCampaignName)).setText(c.name);
            ((TextView) card.findViewById(R.id.tvInfluencerCount))
                    .setText(c.influencerCount + " influencers");
            ((TextView) card.findViewById(R.id.tvBudget)).setText(c.budget);
            ((TextView) card.findViewById(R.id.tvTimeAgo)).setText(c.timeAgo);

            // ✅ CAMPAIGN CLICK WITH FULL DATA
            card.setOnClickListener(v -> {
                Intent intent = new Intent(this, com.example.conquer_app.CampaignDetailsActivity.class);

                intent.putExtra("campaign_title", c.name);
                intent.putExtra("campaign_budget", c.budget);
                intent.putExtra("campaign_duration", "Jan 15 - Feb 28, 2026");
                intent.putExtra("campaign_description", "Promote our new summer collection...");
                intent.putExtra("campaign_influencers", String.valueOf(c.influencerCount));

                startActivity(intent);
            });

            campaignContainer.addView(card);
        }
    }

    // ── Bottom Navigation ───────────────────────────────────────
    private void setupBottomNav() {
        bottomNav = findViewById(R.id.bottomNav);
        bottomNav.setSelectedItemId(R.id.nav_home);

        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;

            } else if (id == R.id.nav_discover) {
                startActivity(new Intent(this, DiscoverActivity.class));
                overridePendingTransition(0, 0);
                return true;

            } else if (id == R.id.nav_messages) {
                startActivity(new Intent(this, MessagesActivity.class));
                overridePendingTransition(0, 0);
                return true;

                // ✅ PROFILE COMMAND (as required)
            } else if (id == R.id.nav_profile) {
                startActivity(new Intent(this, com.example.conquer_app.ProfileActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }

            return false;
        });
    }
}