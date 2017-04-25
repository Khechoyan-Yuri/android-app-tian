package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountCreation extends AppCompatActivity {

    SharedPreferences prefs;

    DatabaseReference myRef;

    int count;

    TextView welcome;

    boolean username_exists;

    EditText username;

    EditText password1;

    EditText password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);

        myRef = FirebaseDatabase.getInstance().getReference("message");



    }//end onCreate

    public void CreateAccount(View v){
        //access preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        welcome = (TextView) findViewById(R.id.welcome);
        username = (EditText) findViewById(R.id.et_UserName);
        password1 = (EditText) findViewById(R.id.et_Password);
        password2 = (EditText) findViewById(R.id.et_Password2);

        username_exists = false;

        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i;

                for( i =1; dataSnapshot.child("User").child("username" + i).getValue(String.class) != null; i++) {

                    if(username.getText().equals(dataSnapshot.child("User").child("username" + i).getValue(String.class))){
                        Toast.makeText(getApplicationContext(), "Please Choose a Different Username.", Toast.LENGTH_LONG).show();
                        username_exists = true;
                    }
                }

                if(username_exists == false && !(username.getText().toString().equals("")) &&
                        !(password1.getText().toString().equals(""))&&
                        password1.getText().toString().equals(password2.getText().toString())) {

                    myRef.child("User").child("username" + i).setValue(username.getText());
                    myRef.child("User").child("username" + i).child("password").setValue(password1.getText());

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Canceled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        myRef.addValueEventListener(userListener);


        //check content
        if(username.getText().toString().equals("")){
            welcome.setText("You must enter a username");
        } else if(password1.getText().toString().equals("")){
            welcome.setText("You must enter a password");
        } else if(!password1.getText().toString().equals(password2.getText().toString())){
            welcome.setText("Passwords must match");
            //TODO: Else if(username exists in database){
            //welcome.setText("User exists, please choose a different name");
        } else {
            //first put the username into shared preferences
            prefs.edit().remove("username");
            prefs.edit().putString("username", username.getText().toString()).apply();

            //then go ahead and take us to the main menu
            Intent i = new Intent(getApplicationContext(), MainActivity.class);

            startActivity(i);
        }

    }//end CreateAccount

    public void onBackPressed() {//deal with backbutton
        //do nothing, that way we can avoid people skipping login
    }
}
