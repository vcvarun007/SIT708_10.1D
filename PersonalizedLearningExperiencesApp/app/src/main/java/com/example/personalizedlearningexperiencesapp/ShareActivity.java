package com.example.personalizedlearningexperiencesapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Button shareButton = findViewById(R.id.shareButton);

        shareButton.setOnClickListener(view -> shareProfile());
    }

    private void shareProfile() {
        View rootView = getWindow().getDecorView().findViewById(android.R.id.content);
        Bitmap bitmap = takeScreenshot(rootView);
        if (bitmap != null) {
            saveBitmapAndShare(bitmap, "profile.png");
        }
    }

    private Bitmap takeScreenshot(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void saveBitmapAndShare(Bitmap bitmap, String fileName) {
        try {
            File imagePath = new File(getExternalCacheDir(), fileName);
            FileOutputStream fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

            Uri uri = FileProvider.getUriForFile(this, "com.example.personalizedlearningexperiencesapp.fileprovider", imagePath);
            if (uri != null) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/png");
                intent.putExtra(Intent.EXTRA_STREAM, uri);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
