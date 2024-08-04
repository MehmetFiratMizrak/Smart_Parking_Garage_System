package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button buttonReg;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    public static String allUserInfo = " ";
    DatabaseReference databaseReference;
    FirebaseDatabase db =FirebaseDatabase.getInstance();


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseReference = db.getReference("UserInfo");
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        buttonReg = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.loginNow);
        textView.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),login.class);
            startActivity(intent);
            finish();


        });


        buttonReg.setOnClickListener(view -> {
            String email, password,password2;
            email = String.valueOf(editTextEmail.getText());
            password = String.valueOf(editTextEmail.getText());
            password2 = String.valueOf(editTextPassword.getText());
            allUserInfo =  "Email: "+ email + "  Password: "+ password2 + "\n";
            sendData();
            progressBar.setVisibility(View.VISIBLE);
            if (TextUtils.isEmpty(email))
            {

                Toast.makeText(Register.this, "Enter email.",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password))
            {


                Toast.makeText(Register.this, "Enter password.",
                        Toast.LENGTH_SHORT).show();
                return;

            }




            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        progressBar.setVisibility(View.VISIBLE);
                        if (task.isSuccessful()) {

                            Toast.makeText(Register.this, "Account Created.",
                                    Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),login.class);
                            startActivity(intent);
                            finish();



                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });




        });

    }
    public void sendData(){
        String sendData =allUserInfo;
        String id = databaseReference.push().getKey();
        Data data = new Data(id,sendData);
        databaseReference.child(id).setValue(data);
    }

}