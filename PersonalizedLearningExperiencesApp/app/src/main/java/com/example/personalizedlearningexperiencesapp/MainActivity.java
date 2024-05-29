package com.example.personalizedlearningexperiencesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    private TextView userNameTextView;
    private TextView taskTextView;
    private ImageView userImageView;
    private Button startButton;
    private Button showProfileButton;
    private Button upgradeButton;

    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        userNameTextView = findViewById(R.id.text_view_name);
        userImageView = findViewById(R.id.profilePic);
        startButton = findViewById(R.id.startButton);
        taskTextView = findViewById(R.id.taskDesc);
        upgradeButton = findViewById(R.id.upgradeButton);
        showProfileButton = findViewById(R.id.profileButton);

        firestore.collection("users")
                .document(auth.getCurrentUser().getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        String userName = documentSnapshot.getString("Name");
                        if (userName != null) {
                            userNameTextView.setText(userName.toUpperCase());
                        }
                    }
                });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QuizActivity.class));
            }
        });

        upgradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UpgradeActivity.class));
            }
        });

        showProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ShareActivity.class));
            }
        });
    }
}
