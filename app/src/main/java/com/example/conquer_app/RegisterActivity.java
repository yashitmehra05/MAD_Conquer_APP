package com.example.conquer_app;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etCompany, etNiche;
    String accountType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        accountType = getIntent().getStringExtra("account_type");

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etCompany = findViewById(R.id.etCompany);
        etNiche = findViewById(R.id.etNiche);
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvCompanyLabel = findViewById(R.id.tvCompanyLabel);
        TextView tvNicheLabel = findViewById(R.id.tvNicheLabel);
        Button btnComplete = findViewById(R.id.btnComplete);
        ImageButton btnBack = findViewById(R.id.btnBack);

        // Customize labels based on account type
        if ("Influencer".equals(accountType)) {
            tvTitle.setText("Influencer Details");
            tvCompanyLabel.setText("Social Handle");
            etCompany.setHint("@yourhandle");
            tvNicheLabel.setText("Your Niche");
            etNiche.setHint("E.g. Fitness, Tech, Fashion");
        } else {
            tvTitle.setText("Brand Details");
            tvCompanyLabel.setText("Company Name");
            etCompany.setHint("Your Company");
            tvNicheLabel.setText("Industry");
            etNiche.setHint("Your Industry");
        }

        btnBack.setOnClickListener(v -> finish());

        btnComplete.setOnClickListener(v -> {
            if (validateInputs()) {
                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs() {
        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String company = etCompany.getText().toString().trim();
        String niche = etNiche.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            etName.setError("Name is required");
            etName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(email)) {
            etEmail.setError("Email is required");
            etEmail.requestFocus();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Enter a valid email (must include @)");
            etEmail.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            etPassword.setError("Password is required");
            etPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            etPassword.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(company)) {
            etCompany.setError("This field is required");
            etCompany.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(niche)) {
            etNiche.setError("This field is required");
            etNiche.requestFocus();
            return false;
        }

        return true;
    }
}