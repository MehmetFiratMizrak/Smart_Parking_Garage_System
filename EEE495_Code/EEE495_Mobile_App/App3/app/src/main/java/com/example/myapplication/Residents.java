package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Residents extends AppCompatActivity {
    Button button;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public static String allUserinfo = " ";
    DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_residents);
        TextView textView = (TextView) findViewById(R.id.textViewResidents);
        allUserinfo = Register.allUserInfo;
        textView.setText(allUserinfo);
        button = findViewById(R.id.btn_backResidents);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AdminPage.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_addResidents);
        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),Register.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_deleteResidents);
        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();


        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("UserInfo");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    allUserinfo = allUserinfo.substring(31,allUserinfo.length())+ snapshot1.getValue() .toString() + "\n";
                    TextView textView = (TextView) findViewById(R.id.textViewResidents);
                    textView.setText(allUserinfo);
                    Log.d("infoPlate",allUserinfo );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


}