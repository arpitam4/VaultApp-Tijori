package com.example.tijori;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity {
    TextView login;
    String currentUserId;
    EditText signupName, signupUsername, signupEmail, signupPassword,signuppin;
    Button signupButton;
    FirebaseDatabase database;

    FirebaseAuth mAuth;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        signupName = findViewById(R.id.signup_name);
        signupEmail = findViewById(R.id.signup_email);
        signupUsername = findViewById(R.id.signup_username);
        signupPassword = findViewById(R.id.signup_password);
        signupButton = findViewById(R.id.signup_button);
        signuppin = findViewById(R.id.signup_pin);
        login = findViewById(R.id.loginTxt);
        mAuth = FirebaseAuth.getInstance();
        //FirebaseUser currentUser = mAuth.getCurrentUser();
       // currentUserId = currentUser.getUid();

        /*if(mAuth.getCurrentUser() != null){
            Toast.makeText(this, "User Already Exist! Please Login", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login.class));
        }*/

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = signupName.getText().toString().trim();
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String email = signupEmail.getText().toString().trim();
                String password = signupPassword.getText().toString().trim();
                String username = signupUsername.getText().toString().trim();
                String pin = signuppin.getText().toString().trim();
                String images = null;
                HelperClass helperClass = new HelperClass(name, email, username, password, images,pin);

                if (TextUtils.isEmpty(email)) {
                    signupEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    signupPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 8) {
                    signupPassword.setError("Password must have 8 Characters");
                    return;
                }

                if (TextUtils.isEmpty(name)) {
                    signupName.setError("Name is Required");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    signupUsername.setError("Username is Required");
                    return;
                }
                if (TextUtils.isEmpty(pin)) {
                    signupUsername.setError("Pin is Required");
                    return;
                }
                if (pin.length()>4 && pin.length()<4) {
                    signupUsername.setError("Pin Must have 4 Numbers");
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                currentUserId = currentUser.getUid();
                                reference.child(currentUserId).setValue(helperClass);
                            }

                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Login.class));
                            finish();
                        } else {
                            // Handle registration failure
                            Toast.makeText(Register.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        /*signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = FirebaseDatabase.getInstance();
                reference = database.getReference("users");
                String name = String.valueOf(signupName.getText());
                String email =  String.valueOf(signupEmail.getText());
                String username =  String.valueOf(signupUsername.getText());
                String password =  String.valueOf(signupPassword.getText());
                HelperClass helperClass = new HelperClass(name, email, username, password);

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(username)){
                    Toast.makeText(Register.this,"Enter Email",Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull com.google.android.gms.tasks.Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Register.this, "Account Created.",
                                            Toast.LENGTH_SHORT).show();

                                } else {
                                    // If sign in fails, display a message to the user.
                                    Toast.makeText(Register.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                reference.child(username).setValue(helperClass);
                Toast.makeText(Register.this, "You have signup successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
                finish();
            }
        });*/

    }
}