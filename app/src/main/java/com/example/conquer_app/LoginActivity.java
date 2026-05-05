package com.example.conquer_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginBtn;
    TextView signupBtn;
    FirebaseAuth mAuth;

    // Google Sign-In
    ActivityResultLauncher<Intent> googleLauncher;
    GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        // Updated IDs to match activity_login.xml
        emailInput    = findViewById(R.id.etEmail);
        passwordInput = findViewById(R.id.etPassword);
        loginBtn      = findViewById(R.id.btnLogin);
        signupBtn     = findViewById(R.id.tvSignUp);

        // ── Email/Password login ──────────────────────
        if (loginBtn != null) {
            loginBtn.setOnClickListener(v -> {
                String email = emailInput.getText().toString().trim();
                String password = passwordInput.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(result -> goToHome())
                        .addOnFailureListener(e ->
                                Toast.makeText(this, "Error: " + e.getMessage(),
                                        Toast.LENGTH_SHORT).show());
            });
        }

        // ── Go to Signup ──────────────────────────────
        if (signupBtn != null) {
            signupBtn.setOnClickListener(v ->
                    startActivity(new Intent(this, SignUpActivity.class)));
        }

        // ── Google Sign-In setup ──────────────────────
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        googleLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK) {
                        Task<GoogleSignInAccount> task =
                                GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account.getIdToken());
                        } catch (ApiException e) {
                            Toast.makeText(this, "Google Sign-In failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Safe check for googleBtn as it is currently missing in activity_login.xml
        View googleBtn = findViewById(R.id.googleBtn);
        if (googleBtn != null) {
            googleBtn.setOnClickListener(v ->
                    googleLauncher.launch(googleSignInClient.getSignInIntent()));
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(result -> goToHome())
                .addOnFailureListener(e ->
                        Toast.makeText(this, "Auth Failed: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show());
    }

    private void goToHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    // ── Auto-skip login if already logged in ─────────
    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null) goToHome();
    }
}
