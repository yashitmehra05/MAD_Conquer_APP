package com.conquer.app;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conquer_app.R;

public class CampaignDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_campaign_details);

        ImageButton btnBack = findViewById(R.id.btnBack);
        ImageButton btnEdit = findViewById(R.id.btnEdit);

        btnBack.setOnClickListener(v -> finish());
        btnEdit.setOnClickListener(v ->
                Toast.makeText(this, "Edit Campaign", Toast.LENGTH_SHORT).show());

        // Receive data from Intent (sent by HomeActivity campaign click)
        String title = getIntent().getStringExtra("campaign_title");
        String budget = getIntent().getStringExtra("campaign_budget");
        String duration = getIntent().getStringExtra("campaign_duration");
        String description = getIntent().getStringExtra("campaign_description");
        String influencers = getIntent().getStringExtra("campaign_influencers");

        if (title != null) {
            TextView tvTitle = findViewById(R.id.tvCampaignTitle);
            tvTitle.setText(title);
        }
        if (budget != null) {
            TextView tvBudget = findViewById(R.id.tvBudget);
            tvBudget.setText(budget);
        }
        if (duration != null) {
            TextView tvDuration = findViewById(R.id.tvDuration);
            tvDuration.setText(duration);
        }
        if (description != null) {
            TextView tvDescription = findViewById(R.id.tvDescription);
            tvDescription.setText(description);
        }
        if (influencers != null) {
            TextView tvInfluencers = findViewById(R.id.tvInfluencers);
            tvInfluencers.setText(influencers);
        }

        // Add deliverable bullet points dynamically
        LinearLayout layoutDeliverables = findViewById(R.id.layoutDeliverables);
        String[] deliverables = {"3 Instagram feed posts", "5 Instagram stories", "1 Instagram reel"};
        for (String item : deliverables) {
            TextView tv = new TextView(this);
            tv.setText("● " + item);
            tv.setTextColor(0xFFFFFFFF);
            tv.setTextSize(14f);
            tv.setPadding(0, 6, 0, 6);
            tv.setGravity(Gravity.START);
            layoutDeliverables.addView(tv);
        }
    }
}