package com.example.personalizedlearningexperiencesapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UpgradeActivity extends AppCompatActivity {
    private AppCompatButton purchaseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upgrade);

        purchaseButton = findViewById(R.id.purchaseButton);
        purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPaymentScreen();
            }
        });
    }

    private void loadPaymentScreen() {
        PaymentActivity fragment = new PaymentActivity();

        // Begin the fragment transaction
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Replace the current fragment/container with the new fragment
        transaction.replace(R.id.fragment_container, fragment);

        // Add the transaction to the back stack
        transaction.addToBackStack(null);

        // Commit the transaction
        transaction.commit();
    }
}
