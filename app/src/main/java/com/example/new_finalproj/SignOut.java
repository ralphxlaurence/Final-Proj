package com.example.new_finalproj;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignOut extends AppCompatActivity {

    Button Logout;
    TextView tvName, tvEmail, tvBday, tvGender, tvPhone, tvUsername;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signout);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        user = fAuth.getCurrentUser();

        Logout = findViewById(R.id.logout);
        tvName = findViewById(R.id.fullname);
        tvEmail = findViewById(R.id.email);
        tvBday = findViewById(R.id.bday);
        tvGender = findViewById(R.id.gender);
        tvPhone = findViewById(R.id.mobile);
        tvUsername = findViewById(R.id.username);

        if (user != null) {
            DocumentReference df = fStore.collection("Users").document(user.getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    tvName.setText("" + documentSnapshot.getString("Name"));
                    tvEmail.setText(documentSnapshot.getString("UserEmail"));
                    tvBday.setText(documentSnapshot.getString("Birthday"));
                    tvGender.setText(documentSnapshot.getString("Gender"));
                    tvPhone.setText(documentSnapshot.getString("Phone"));
                    tvUsername.setText(documentSnapshot.getString("Username"));
                }
            });
        }

        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            DocumentReference df = FirebaseFirestore.getInstance().collection("Users")
                    .document(FirebaseAuth.getInstance().getUid());
        }
    }
}
