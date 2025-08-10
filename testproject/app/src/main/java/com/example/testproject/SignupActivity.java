package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignupActivity extends AppCompatActivity {

    private TextInputEditText usernameInput, passwordInput, phoneInput; // Add phoneInput
    private MaterialButton signupButton, backToLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        phoneInput = findViewById(R.id.phoneInput); // Initialize phoneInput
        signupButton = findViewById(R.id.signupButton);
        backToLoginButton = findViewById(R.id.backToLoginButton);

        signupButton.setOnClickListener(v -> {
            String username = usernameInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            String phone = phoneInput.getText().toString().trim(); // Assuming phoneInput is the EditText for the phone number

            // Validate phone number
            if (phone.length() != 8 || !phone.matches("\\d+")) { // Check if phone number is 8 digits and only contains numbers
                runOnUiThread(() ->
                        Toast.makeText(SignupActivity.this, "Phone number must be 8 digits and contain only numbers.", Toast.LENGTH_SHORT).show());
                return;
            }

            new Thread(() -> {
                try {
                    URL url = new URL("http://10.0.2.2:9999/clicker/signup"); // Update if needed
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    String data = "username=" + URLEncoder.encode(username, "UTF-8") +
                            "&password=" + URLEncoder.encode(password, "UTF-8") +
                            "&phone=" + URLEncoder.encode(phone, "UTF-8");

                    OutputStream os = conn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();

                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String responseLine = in.readLine();
                    in.close();

                    runOnUiThread(() -> {
                        Toast.makeText(SignupActivity.this, responseLine, Toast.LENGTH_SHORT).show();
                        if (responseLine.contains("successful")) {
                            startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                            finish();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(SignupActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show());
                }
            }).start();
        });


        backToLoginButton.setOnClickListener(v -> {
            finish();
        });
    }
}
