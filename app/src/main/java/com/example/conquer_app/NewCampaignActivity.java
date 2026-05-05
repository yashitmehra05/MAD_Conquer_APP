package com.example.conquer_app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.conquer_app.R;

public class NewCampaignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_campaign);

        ImageButton btnBack = findViewById(R.id.btnBack);
        Button btnCancel = findViewById(R.id.btnCancel);
        Button btnCreate = findViewById(R.id.btnCreateCampaign);

        EditText etName = findViewById(R.id.etCampaignName);
        EditText etCategory = findViewById(R.id.etCategory);
        EditText etBudget = findViewById(R.id.etBudget);
        EditText etStartDate = findViewById(R.id.etStartDate);
        EditText etEndDate = findViewById(R.id.etEndDate);
        EditText etDescription = findViewById(R.id.etDescription);
        EditText etDeliverables = findViewById(R.id.etDeliverables);

        btnBack.setOnClickListener(v -> finish());
        btnCancel.setOnClickListener(v -> finish());

        btnCreate.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            if (name.isEmpty()) {
                etName.setError("Campaign name is required");
                return;
            }
            Toast.makeText(this, "Campaign \"" + name + "\" created!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}