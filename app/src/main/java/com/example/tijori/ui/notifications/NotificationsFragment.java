package com.example.tijori.ui.notifications;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.text.method.HideReturnsTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tijori.HelperClass;
import com.example.tijori.R;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

public class NotificationsFragment extends Fragment {

    TextView profileName, profileEmail, profileUsername, profilePassword,titleName,titleUsername,imagecount;
    Button editProfile;

    String passwordUser, usernameUser, emailUser, nameUser;

    private  FirebaseUser user;
    private DatabaseReference reference,reference1;

    private String userID;
    private boolean isPasswordVisible = false;

    public NotificationsFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

         profileName = view.findViewById(R.id.profileName);
         profileEmail = view.findViewById(R.id.profileEmail);
        profileUsername = view.findViewById(R.id.profileUsername);
        profilePassword = view.findViewById(R.id.profilePassword);
        titleName = view.findViewById(R.id.titleName);
        titleUsername=view.findViewById(R.id.titleUsername);
        imagecount = view.findViewById(R.id.imagecount);



        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users");
        userID = user.getUid();

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HelperClass userProfile = snapshot.getValue(HelperClass.class);

                if(userProfile != null){
                    String name = userProfile.name;
                    String password = userProfile.password;
                    String username = userProfile.username;
                    String email = userProfile.email;

                    profileName.setText(name);
                    profileEmail.setText(email);
                    profilePassword.setText(password);
                    profileUsername.setText(username);
                    titleName.setText(name);
                    titleUsername.setText(username);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error!", Toast.LENGTH_SHORT).show();

            }
        });

        reference.child(userID).child("newsimages").child("Images").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long imageCount = snapshot.getChildrenCount();
                String photoCount = String.valueOf(imageCount);
                imagecount.setText(photoCount);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //fetchAndDisplayUserData();

        return view;
    }

    private void fetchAndDisplayUserData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserId = currentUser != null ? currentUser.getUid() : null;

        if (currentUserId != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            Query checkUserDatabase = reference.orderByChild("uid").equalTo(currentUserId);

            checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        nameUser = snapshot.child(currentUserId).child("name").getValue(String.class);
                        emailUser = snapshot.child(currentUserId).child("email").getValue(String.class);
                        usernameUser = snapshot.child(currentUserId).child("username").getValue(String.class);
                        passwordUser = snapshot.child(currentUserId).child("password").getValue(String.class);

                        Log.d("UserData", "Name: " + nameUser + ", Email: " + emailUser +
                                ", Username: " + usernameUser + ", Password: " + passwordUser);

                        // Set the retrieved user information to the TextViews
                        profileName.setText(nameUser);
                        profileEmail.setText(emailUser);
                        profileUsername.setText(usernameUser);
                        profilePassword.setText(passwordUser);

                        profilePassword.setTransformationMethod(new PasswordTransformationMethod());

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FirebaseData", "Error: " + error.getMessage());
                }
            });
        }
    }


    }

