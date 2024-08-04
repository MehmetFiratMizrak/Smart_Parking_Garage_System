package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class UserPage extends AppCompatActivity {
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseDatabase db =FirebaseDatabase.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    DatabaseReference mDatabase;
    DatabaseReference mDatabase2;
    Button button;
    public static String inputToSend = " ";
    public static String inputToAllowedPlates = " ";
    public static String openGateManually = " "; // This string will go to firebase to process
    public static String garageInfo = " ";
    public static String warningInfo = " ";
    public static int counter = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_page);

        databaseReference = db.getReference("ManualGate");
        databaseReference2 = db.getReference("Requests");



        button = findViewById(R.id.logout);
        button.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), login.class);
            startActivity(intent);
            finish();

        });
        button = findViewById(R.id.btn_openGate);
        button.setOnClickListener(view -> {
            String s = Integer.toString(counter);
            openGateManually = "There is an open gate manually request "+ "s" ; // This string will go to firebase to process
            sendData();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("ManualGate");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                        appleSnapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            counter = counter + 1;
            Log.d("info",openGateManually );


        });
        button = findViewById(R.id.btn_deleteAccount);
        button.setOnClickListener(view -> {
            user.delete();

        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Garage");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    garageInfo = snapshot1.getValue() .toString() + "\n";
                    //request = request.substring(31,request.length());
                    TextView textView = (TextView) findViewById(R.id.textViewGarage);
                    textView.setText(garageInfo);
                    Log.d("infoPlate",garageInfo );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
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

    public void sendData(){
        String sendData =openGateManually;
        String id = databaseReference.push().getKey();
        Data data = new Data(id,sendData);
        databaseReference.child(id).setValue(data);
    }
    public void sendData2(){
        String sendData2 =inputToSend;
        String id2 = databaseReference2.push().getKey();
        Data data2 = new Data(id2,sendData2);
        databaseReference2.child(id2).setValue(data2);
    }

    public String handleText(View v){
        EditText t = findViewById(R.id.plateNo);
        String input = t.getText().toString();
        //inputToAllowedPlates = inputToAllowedPlates + input;
        //Log.d("allowed",inputToAllowedPlates );
        input = input + " Has a request for Adding";
        inputToSend = input;
        sendData2();
        Log.d("info",input );
        return input;
    }
    public String handleText2(View v){
        EditText t = findViewById(R.id.plateNo);
        String input = t.getText().toString();
        input = input + " Has a request for Deleting";
        inputToSend = input;
        sendData2();
        Log.d("info",input );
        return input;
    }
    public String handleText3(View v){
        EditText t = findViewById(R.id.plateNo);
        String input = t.getText().toString();
        //inputToAllowedPlates = inputToAllowedPlates + input;
        //Log.d("allowed",inputToAllowedPlates );
        input = input + " Has a request for Temporarily Adding";
        inputToSend = input;
        sendData2();
        Log.d("info",input );
        return input;
    }



}