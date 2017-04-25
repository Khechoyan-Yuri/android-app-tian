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

public class AccountCreation extends AppCompatActivity {

    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
    }//end onCreate

    public void CreateAccount(View v){
        //access preferences
        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        TextView welcome = (TextView) findViewById(R.id.welcome);
        EditText username = (EditText) findViewById(R.id.et_UserName);
        EditText password1 = (EditText) findViewById(R.id.et_Password);
        EditText password2 = (EditText) findViewById(R.id.et_Password2);

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
}
