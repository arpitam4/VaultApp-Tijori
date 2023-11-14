package com.example.tijori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PinConfirmation extends AppCompatActivity {

    Button button;
    EditText enterpin;
    String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_confirmation);

        button = findViewById(R.id.pin_button);
        enterpin = findViewById(R.id.pin_pin);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUserId = currentUser.getUid();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pin = enterpin.getText().toString().trim();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String storedPin = snapshot.child("pin").getValue(String.class);
                            if (pin.equals(storedPin)) {
                                // PIN is correct, start the MainActivity
                                Intent intent = new Intent(PinConfirmation.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Close the PinConfirmation activity
                            } else {
                                enterpin.setError("PIN does not match");
                                enterpin.requestFocus();
                            }
                        } else {
                            // Handle the case where user data doesn't exist
                            Toast.makeText(PinConfirmation.this, "User data not found.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(PinConfirmation.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
