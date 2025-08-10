package com.example.testproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class QuizOptionsActivity extends AppCompatActivity {

    private MaterialButton optionA, optionB, optionC, optionD;
    private Button btnNext, btnPrevious;
    private TextView questionIdText, questionText;

    private int currentQuestionId = 1;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_options);

        // Get data from Intent
        Intent intent = getIntent();
        currentQuestionId = intent.getIntExtra("question_id", 1);
        username = intent.getStringExtra("username");

        if (username == null) {
            Toast.makeText(this, "Error: Username not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Bind UI components
        questionIdText = findViewById(R.id.questionIdText);
        questionText = findViewById(R.id.questionText);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        btnNext = findViewById(R.id.btnNext);
        btnPrevious = findViewById(R.id.btnPrevious);

        // Load initial question
        loadQuestionFromRemoteSQL(currentQuestionId);

        // Set up option buttons
        View.OnClickListener optionClickListener = v -> {
            MaterialButton selectedButton = (MaterialButton) v;
            String optionLetter = "";

            if (v == optionA) optionLetter = "A";
            else if (v == optionB) optionLetter = "B";
            else if (v == optionC) optionLetter = "C";
            else if (v == optionD) optionLetter = "D";

            String finalOption = optionLetter;

            new Thread(() -> {
                try {
                    URL url = new URL("http://10.0.2.2:9999/clicker/submitAnswer");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                    String data = "user_id=" + URLEncoder.encode(username, "UTF-8") +
                            "&question_id=" + currentQuestionId +
                            "&selected_option=" + URLEncoder.encode(finalOption, "UTF-8");

                    OutputStream os = conn.getOutputStream();
                    os.write(data.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();

                    runOnUiThread(() -> {
                        if (responseCode == 200) {
                            Toast.makeText(this, "Answer submitted: " + finalOption, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Submission failed (code: " + responseCode + ")", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    runOnUiThread(() ->
                            Toast.makeText(this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }
            }).start();
        };

        optionA.setOnClickListener(optionClickListener);
        optionB.setOnClickListener(optionClickListener);
        optionC.setOnClickListener(optionClickListener);
        optionD.setOnClickListener(optionClickListener);

        // Next button
        btnNext.setOnClickListener(v -> {
            int nextQuestionId = currentQuestionId + 1;
            loadQuestionFromRemoteSQL(nextQuestionId);
        });

        // Previous button
        btnPrevious.setOnClickListener(v -> {
            if (currentQuestionId > 1) {
                int previousQuestionId = currentQuestionId - 1;
                loadQuestionFromRemoteSQL(previousQuestionId);
            } else {
                Toast.makeText(this, "This is the first question", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadQuestionFromRemoteSQL(int questionId) {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2:9999/clicker/question?question_id=" + questionId);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                reader.close();
                String response = stringBuilder.toString();

                JSONObject json = new JSONObject(response);

                if (json.has("error")) {
                    runOnUiThread(() -> {
                        Toast.makeText(this, "This is the last question", Toast.LENGTH_SHORT).show();
                    });
                } else {
                    currentQuestionId = questionId;

                    String questionTextValue = json.getString("text");
                    String optionAText = json.getString("a");
                    String optionBText = json.getString("b");
                    String optionCText = json.getString("c");
                    String optionDText = json.getString("d");

                    runOnUiThread(() -> {
                        questionIdText.setText("Question ID: " + currentQuestionId);
                        questionText.setText(questionTextValue);
                        optionA.setText(optionAText);
                        optionB.setText(optionBText);
                        optionC.setText(optionCText);
                        optionD.setText(optionDText);
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(this, "Error loading question", Toast.LENGTH_SHORT).show();
                });
            }
        }).start();
    }
}
