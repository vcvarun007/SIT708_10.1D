package com.example.personalizedlearningexperiencesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    ImageView userPhoto;
    Button getImageButton, registerButton;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    TextInputEditText nameEditText, passwordEditText, phoneEditText, emailEditText;
    TextView loginTextView;

    String imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
        getImageButton = findViewById(R.id.hidButton);
        userPhoto = findViewById(R.id.userDP);
        nameEditText = findViewById(R.id.usernameEditText2);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        phoneEditText = findViewById(R.id.phoneEditText);
        registerButton = findViewById(R.id.createAccButton);
        loginTextView = findViewById(R.id.resgisterTV);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

        // Initialize Firebase authentication
        auth = FirebaseAuth.getInstance();
        // Initialize Firebase Firestore
        firestore = FirebaseFirestore.getInstance();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        final int PICK_IMAGE = 100;
        getImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) {
            final Uri imageUri = data.getData();
            this.imageUri = imageUri.toString();
            final InputStream imageStream;
            try {
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                this.imageUri = selectedImage.toString();
                userPhoto.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void signUpUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("Name", nameEditText.getText().toString().trim());
                user.put("Email", email);
                user.put("Password", password);
                user.put("Phone", phoneEditText.getText().toString().trim());
                user.put("UID", auth.getCurrentUser().getUid());
                user.put("URI", imageUri);

                firestore.collection("users")
                        .document(auth.getCurrentUser().getUid())
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(RegisterActivity.this, "Data Stored Successfully", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, InterestActivity.class));
                                finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(RegisterActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
