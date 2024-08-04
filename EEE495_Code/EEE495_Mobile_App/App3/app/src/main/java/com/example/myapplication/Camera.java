package com.example.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Camera extends AppCompatActivity {
    Button button;
    DatabaseReference databaseReference;
    FirebaseDatabase db =FirebaseDatabase.getInstance();
    public static String cameraRequest = " ";
    private StorageReference mStorageReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mStorageReference = FirebaseStorage.getInstance().getReference().child("image.jpg");
        try {
            final File localFile = File.createTempFile("image","jpg");
            mStorageReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        databaseReference = db.getReference("Camera");
        button = findViewById(R.id.btn_backRequest);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(),AdminPage.class);
            startActivity(intent);
            finish();


        });
        button = findViewById(R.id.btn_camera_request);
        button.setOnClickListener(view -> {
            cameraRequest = "There is a camera request"; // This string will go to firebase to process
            sendData();
            Log.d("info",cameraRequest );
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            Query applesQuery = ref.child("Camera");
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


        });
    }
    public void sendData(){
        String sendData =cameraRequest;
        String id = databaseReference.push().getKey();
        Data data = new Data(id,sendData);
        databaseReference.child(id).setValue(data);
    }
}