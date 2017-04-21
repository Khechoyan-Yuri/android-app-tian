package com.yuri_khechoyan.firebase_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DataBase extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_data);
    }

    protected void test(View v) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.child("User").setValue("Hello, World! Writing to DB Works!");


        ValueEventListener postListener = new ValueEventListener() {

        // Read from the database
       // myRef.addValueEventListener(new ValueEventListener() {
            public String TAG;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.

                if(dataSnapshot.child("User").getValue(String.class) !=null) {
                    String value = dataSnapshot.child("User").getValue(String.class);
                    Log.d("READ_VALUE", "Value is: " + value);
                }

                else {
                    Log.d("READ_VALUE", "Value is null");
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        };

        myRef.addValueEventListener(postListener);

    }
}
