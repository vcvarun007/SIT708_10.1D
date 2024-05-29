package com.example.personalizedlearningexperiencesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class InterestActivity extends AppCompatActivity {

    private Button nextButton;
    private Button mobileAppDevButton;
    private Button testingButton;
    private Button algorithmButton;
    private Button dataStructureButton;
    private Button webDeveloperButton;
    private Button devOpsButton;
    private Button softwareEngineerButton;
    private Button consultantButton;
    private TextView selectedInterestTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_interest);

        // Initialize views
        nextButton = findViewById(R.id.nextButton);
        mobileAppDevButton = findViewById(R.id.mobileAppDev);
        testingButton = findViewById(R.id.testing);
        algorithmButton = findViewById(R.id.algorithm);
        dataStructureButton = findViewById(R.id.dataStructure);
        webDeveloperButton = findViewById(R.id.webDeveloper);
        devOpsButton = findViewById(R.id.devOps);
        softwareEngineerButton = findViewById(R.id.softwareEngineer);
        consultantButton = findViewById(R.id.consultant);
        selectedInterestTextView = findViewById(R.id.selectedInterest);

        // Set onClick listeners for interest buttons
        mobileAppDevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedInterestTextView.setText(mobileAppDevButton.getText());
            }
        });

        testingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedInterestTextView.setText(testingButton.getText());
            }
        });

        // Similar onClick listeners for other interest buttons...

        // Set onClick listener for the next button
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create intent to navigate to MainActivity and pass selected interest
                Intent intent = new Intent(InterestActivity.this, MainActivity.class);
                intent.putExtra("Interest", selectedInterestTextView.getText().toString());
                startActivity(intent);
                finish(); // Finish the current activity to prevent going back to it from MainActivity
            }
        });
    }
}
