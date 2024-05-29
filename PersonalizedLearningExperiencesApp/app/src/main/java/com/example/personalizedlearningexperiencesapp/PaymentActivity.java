package com.example.personalizedlearningexperiencesapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Card;
import com.stripe.android.model.Token;
import com.stripe.android.view.CardInputWidget;


public class PaymentActivity extends Fragment {
    private CardInputWidget cardInputWidget;
    private Stripe stripe;
    private AppCompatButton payButton;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize Stripe with the publishable key
        PaymentConfiguration.init(requireContext(), "pk_test_51PCKi0KL7fioSjeEEyqZLIGuARqfzEinVJVjwMzsIU01uY6hNHIVLYMnl1RGQSQgfII4Emx7fNcPZyzJ3L0HRzfh008QvHsxNl");

        // Create a Stripe instance
        stripe = new Stripe(requireContext(), PaymentConfiguration.getInstance(requireContext()).getPublishableKey());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        // Get reference to CardInputWidget
        cardInputWidget = view.findViewById(R.id.cardDetailsInput);
        payButton = view.findViewById(R.id.buyNowButton);
        // Set OnClickListener for payButton
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                purchase();
            }
        });

        return view;
    }

    private void purchase() {
        // Create a Card object from the input
        Card card = cardInputWidget.getCard();
        if (card != null) {
            // Create a token asynchronously
            stripe.createToken(
                    card,
                    new ApiResultCallback<Token>() {
                        @Override
                        public void onSuccess(@NonNull Token token) {
                            // Handle token creation success
                            Toast.makeText(requireContext(), "Token: " + token.getId(), Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull Exception e) {
                            // Handle token creation error
                            Toast.makeText(requireContext(), "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
            );
        }
    }
}
