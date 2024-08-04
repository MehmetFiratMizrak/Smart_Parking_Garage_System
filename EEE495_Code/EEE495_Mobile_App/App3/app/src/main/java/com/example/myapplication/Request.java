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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Request extends AppCompatActivity {
    Button button;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    FirebaseDatabase db =FirebaseDatabase.getInstance();
    public static String request = "";
    public static String allowedPlates = ""; //// This string will go to firebase to process
    public static String plateHistory = "";
    UserPage a = new UserPage();
    DatabaseReference mDatabase;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    // Access the variable x from ClassA

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);

        databaseReference = db.getReference("PlateRequest");
        databaseReference2 = db.getReference("PlateHistory");
        setContentView(R.layout.activity_request);
        //TextView textView = (TextView) findViewById(R.id.textViewRequest);
        //textView.setText(request);
        button = findViewById(R.id.btn_backRequest);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AdminPage.class);
            startActivity(intent);
            finish();


        });
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Requests");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snapshot1 :snapshot.getChildren() ){
                    request = snapshot1.getValue() .toString() + "\n";
                    request = request.substring(31,request.length());
                    TextView textView = (TextView) findViewById(R.id.textViewRequest);
                    textView.setText(request);
                    Log.d("infoPlate",request );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
    public void sendData(){
        String sendData =allowedPlates;
        String id = databaseReference.push().getKey();
        Data data = new Data(id,sendData);
        databaseReference.child(id).setValue(data);
    }
    public void sendData2(){
        String sendData2 =plateHistory;
        String id2 = databaseReference2.push().getKey();
        Data data2 = new Data(id2,sendData2);
        databaseReference2.child(id2).setValue(data2);
    }


    public String handleText6(View v){
        TextView t = (TextView) findViewById(R.id.textViewRequest);
        String inputToFirebase = t.getText().toString();
        allowedPlates =  inputToFirebase + " is Accepted";
        sendData();
        //inputToFirebase = inputToFirebase + " is Accepted";
        Log.d("infoFirebase",allowedPlates );
        plateHistory = plateHistory + " " + inputToFirebase + " is Accepted" + "---";
        sendData2();
        return inputToFirebase;
    }
    public String handleText7(View v){
        TextView t = (TextView) findViewById(R.id.textViewRequest);
        String inputToFirebase = t.getText().toString();
        inputToFirebase = inputToFirebase + " is Denied";
        Log.d("infoFirebase",inputToFirebase );
        plateHistory = plateHistory + " " + inputToFirebase + "---";
        sendData2();
        return inputToFirebase;
    }

}