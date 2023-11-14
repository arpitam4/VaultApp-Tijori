package com.example.tijori;

import androidx.appcompat.app.AppCompatActivity;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.tijori.MainActivity;
import com.example.tijori.NewsModel;
import com.example.tijori.R;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.util.UUID;

public class upload_news extends AppCompatActivity {

    ImageView close, new_image;
    TextView  post;
    Uri ImageUri;
    LinearLayout linearLayout;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    private String currentUserId;
    LinearProgressIndicator progress5;
    ProgressDialog progressDialog;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                if (result.getData() != null) {
                    post.setEnabled(true);
                    ImageUri = result.getData().getData();
                    Glide.with(getApplicationContext()).load(ImageUri).into(new_image);
                }
            } else {
                Toast.makeText(upload_news.this, "Please select an Image", Toast.LENGTH_SHORT).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_news);

        mAuth = FirebaseAuth.getInstance();
        new_image = findViewById(R.id.news_image);

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserId = currentUser.getUid();
        } else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }

        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Image Uploading");
        progressDialog.setCanceledOnTouchOutside(false);

        close = findViewById(R.id.close1);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(upload_news.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


        post = findViewById(R.id.postnews);
        linearLayout = findViewById(R.id.newslinearlayout);

        new_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
                linearLayout.setVisibility(View.VISIBLE);
                new_image.setVisibility(View.GONE);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                uploadNewsAndImage();
            }
        });
    }

    private void uploadNewsAndImage() {
        if (ImageUri == null) {
            Toast.makeText(upload_news.this, "Please select an image", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
            return;
        }

        StorageReference imageReference = firebaseStorage.getReference().child("users").child(currentUserId).child("newsimages").child("Images").child(System.currentTimeMillis() + "");
        UploadTask uploadTask = imageReference.putFile(ImageUri);

        uploadTask.continueWithTask(task -> {
            if (!task.isSuccessful()) {
                throw task.getException();
            }
            return imageReference.getDownloadUrl();
        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Uri downloadUri = task.getResult();
                if (downloadUri != null) {
                    HelperClass model = new HelperClass();
                    model.setNewsimage(downloadUri.toString());

                    database.getReference().child("users").child(currentUserId).child("newsimages").child("Images").push().setValue(model)
                            .addOnSuccessListener(unused -> {
                                Toast.makeText(upload_news.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(upload_news.this, MainActivity.class));
                            })
                            .addOnFailureListener(e -> {
                                progressDialog.dismiss();
                                Toast.makeText(upload_news.this, "Error!", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                progressDialog.dismiss();
                Toast.makeText(upload_news.this, "Error uploading image", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
