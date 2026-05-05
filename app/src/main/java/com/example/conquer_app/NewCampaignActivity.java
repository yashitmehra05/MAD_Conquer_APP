package com.example.conquer_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import java.util.HashMap;
import java.util.Map;

public class NewCampaignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_campaign);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnCancel    = findViewById(R.id.btnCancel);
        Button btnCreate    = findViewById(R.id.btnCreateCampaign);

        EditText etName         = findViewById(R.id.etCampaignName);
        EditText etCategory     = findViewById(R.id.etCategory);
        EditText etBudget       = findViewById(R.id.etBudget);
        EditText etStartDate    = findViewById(R.id.etStartDate);
        EditText etEndDate      = findViewById(R.id.etEndDate);
        EditText etDescription  = findViewById(R.id.etDescription);
        EditText etDeliverables = findViewById(R.id.etDeliverables);

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        btnCreate.setOnClickListener(v -> {
            String name         = etName.getText().toString().trim();
            String category     = etCategory.getText().toString().trim();
            String budget       = etBudget.getText().toString().trim();
            String startDate    = etStartDate.getText().toString().trim();
            String endDate      = etEndDate.getText().toString().trim();
            String description  = etDescription.getText().toString().trim();
            String deliverables = etDeliverables.getText().toString().trim();

            if (name.isEmpty()) {
                etName.setError("Campaign name is required");
                return;
            }

            saveCampaign(name, category, budget, startDate, endDate, description, deliverables);
        });
    }

    private void saveCampaign(String name, String category, String budget,
                              String startDate, String endDate,
                              String description, String deliverables) {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance()
                .getReference("campaigns").child(uid).push();

        Map<String, Object> campaign = new HashMap<>();
        campaign.put("name",         name);
        campaign.put("category",     category);
        campaign.put("budget",       budget);
        campaign.put("startDate",    startDate);
        campaign.put("endDate",      endDate);
        campaign.put("description",  description);
        campaign.put("deliverables", deliverables);
        campaign.put("status",       "active");
        campaign.put("timestamp",    System.currentTimeMillis());

        ref.setValue(campaign)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(this,
                            "Campaign \"" + name + "\" created!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this,
                                "Failed: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }
}