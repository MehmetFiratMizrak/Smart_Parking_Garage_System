package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class History extends AppCompatActivity {
    Button button;
    DatabaseReference mDatabase;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public static String plates = " ";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        button = findViewById(R.id.btn_backHistory);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(plates);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), AdminPage.class);
            startActivity(intent);
            finish();


        });

        mDatabase = FirebaseDatabase.getInstance().getReference().child("ResidentInfo");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    plates = plates + snapshot1.getValue().toString() + "\n";
                    TextView textView = (TextView) findViewById(R.id.textView);
                    textView.setText(plates);
                    Log.d("infoPlate",plates );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}