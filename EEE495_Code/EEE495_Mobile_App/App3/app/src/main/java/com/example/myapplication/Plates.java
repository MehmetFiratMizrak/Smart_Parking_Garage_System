package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Plates extends AppCompatActivity {
    Button button;
    //Request a = new Request();
    public static String allowed = "";
    DatabaseReference mDatabase;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plates);
        //allowed = allowed + " " + Request.plateHistory;
        //TextView textView = (TextView) findViewById(R.id.textViewPlates);
        //textView.setText(allowed);
        button = findViewById(R.id.btn_Plates);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AdminPage.class);
            startActivity(intent);
            finish();


        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("PlateHistory");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    allowed = snapshot1.getValue() .toString() + "\n";
                    allowed = allowed.substring(31,allowed.length());
                    TextView textView = (TextView) findViewById(R.id.textViewPlates);
                    textView.setText(allowed);
                    Log.d("infoPlate",allowed );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}