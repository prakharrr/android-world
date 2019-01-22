package com.example.payment_sample;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText email;
    EditText pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = findViewById(R.id.emailText);
        pass = findViewById(R.id.pwText);

        // Write a message to the database
        FirebaseApp.initializeApp(this);


        Button loginButton = findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivity2();
            }
        });


        loginButton.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference user = database.getReference("User References");
                        user.child(email.getText().toString()).addListenerForSingleValueEvent(
                                new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        Map<String, String> userInfo = dataSnapshot.getValue(Map.class);
                                        if (userInfo == null) {
                                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                                            DatabaseReference user = database.getReference("User References");
                                            Map<String, String> userMap = new HashMap<>();
                                            userMap.put("email", email.getText().toString());
                                            userMap.put("password", pass.getText().toString());
                                            user.child(email.getText().toString()).setValue(userInfo);
                                        }

                                        openActivity2();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                    }
                });

    }

    public void openActivity2(){
        Intent intent = new Intent(this, Activity2.class);
        startActivity(intent);
    }

}
