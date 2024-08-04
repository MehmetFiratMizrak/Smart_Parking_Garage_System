package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminPage extends AppCompatActivity {
    Button button;
    UserPage value = new UserPage();
    DatabaseReference mDatabase2;
    public static String warningInfo = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        button = findViewById(R.id.logout);
        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();

        });
        button = findViewById(R.id.btn_entranceHistory);
        button.setOnClickListener(view -> {
             Intent intent = new Intent(getApplicationContext(),History.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_allowedPlates);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Plates.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_requests);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Request.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_addDeleteResidents);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Residents.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_camera);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),Camera.class);
            startActivity(intent);
            finish();


        });
        mDatabase2 = FirebaseDatabase.getInstance().getReference().child("Fire");
        mDatabase2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    warningInfo = snapshot1.getValue() .toString() + "\n";
                    //request = request.substring(31,request.length());
                    TextView textView = (TextView) findViewById(R.id.textViewWarning);
                    textView.setText(warningInfo);
                    Log.d("infoPlate",warningInfo );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }




}