package com.example.jessie.dundeal;

//Import Statements
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;



import java.util.ArrayList;

import static android.widget.Toast.LENGTH_LONG;



//***************
// MAIN CLASS
//***************

public class RequestFormCreation extends AppCompatActivity {


    //Creating Username variable
    EditText Username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_form_creation);

        //Username, Name of task, Details, Location, Payment Amount

        //Buttons - Clear & Submit


        //Set Username object to array
        Username = (EditText) findViewById(R.id.et_UserName);

    }

    //**********************************************************
    //  Method for CLEARING FIELDS & Setting items to Default
    //**********************************************************

    public void CancelRequest(View v) {
        setContentView(R.layout.activity_main);

        Toast.makeText(this, "Request Canceled", LENGTH_LONG).show();
    }

    //***********************************************************************
    //Checking Validity of User Input (Emails & Passwords after submission)
    //***********************************************************************

    public void Submit(View v) {

        //Initializes and extracts Values to store into a bundle
        //for moving data to another activity
        EditText FirstName = (EditText) findViewById(R.id.et_FirstName);
        EditText LastName = (EditText) findViewById(R.id.et_LastName);
        EditText PhoneNumber = (EditText) findViewById(R.id.et_PhoneNumber);
        EditText Email = (EditText) findViewById(R.id.et_EnterEmail);
        EditText ReEmail = (EditText) findViewById(R.id.et_ReEnterEmail);
        EditText UserName = (EditText) findViewById(R.id.et_UserName);
        EditText Password = (EditText) findViewById(R.id.et_PassWord);
        EditText RePassword = (EditText) findViewById(R.id.et_ReEnterPassWord);

        //Receiving Text input from user, converting toString()
        String verify_username = Username.getText().toString();

        //These booleans will be used to help identify if application
        //can move onto the 2nd Activity
        boolean FNE_confirm_submission;
        boolean LNE_confirm_submission;
        boolean PHE_confirm_submission;
        boolean E_confirm_submission;
        boolean EV_confirm_submission;
        boolean P_confirm_submission;
        boolean PV_confirm_submission;
        boolean U_confirm_submission;

        //Container for an array of values - (i.e. already submitted UserNames) [strings.xml]
        //this will retrieve already used UserNames
        TypedArray convertArray = getResources().obtainTypedArray(R.array.username_array);

        //Create an ArrayList - to store the pre-registered UserNames
        ArrayList<String> name_Username = new ArrayList<>();

        //Cycles through elements inside of String-array & adds UserName to ArrayList
        for (int i = 0; i < convertArray.length(); i++) {
            name_Username.add(convertArray.getString(i));
        }

        //Verifying if First Name is empty or not | FNE = First Name Entry
        if(FirstName.getText().toString().equals("")){
            Toast.makeText(this, "First Name were not Entered", Toast.LENGTH_SHORT).show();
            FNE_confirm_submission = false;
        }
        else{
            FNE_confirm_submission = true;
        }

        //Verifying if Last Name is empty or not | LNE = Last Name Entry
        if(LastName.getText().toString().equals("")){

            Toast.makeText(this, "Last Name were not Entered", Toast.LENGTH_SHORT).show();

            LNE_confirm_submission = false;
        }
        else{
            LNE_confirm_submission = true;
        }

        //Verifying if Phone Number is empty or not | PNE = Phone Number Entry
        if(PhoneNumber.getText().toString().equals("")){
            Toast.makeText(this, "Phone Number were not Entered", Toast.LENGTH_SHORT).show();
            PHE_confirm_submission = false;
        }
        else{
            PHE_confirm_submission = true;
        }

        //Verifying if Email & ReEmail are empty or not | E = Email Entry
        if(email_verify1.getText().toString().equals("") || email_verify2.getText().toString().equals("") ){

            Toast.makeText(this, "Emails were not Entered", Toast.LENGTH_SHORT).show();

            E_confirm_submission = false;
        }
        else{
            E_confirm_submission = true;
        }
        //Verifying if Passwords Match | EV = Email Verification
        if(!email_verify2.getText().toString().trim().equals(email_verify1.getText().toString().trim())){

            EV_confirm_submission = false;

            Email.setText("");
            ReEmail.setText("");

            Toast.makeText(this, "Emails Do Not Match", Toast.LENGTH_SHORT).show();
        }
        else{
            EV_confirm_submission = true;
        }

        //Verifying if Passwords Match | P = Password Match
        if(!password_verify2.getText().toString().trim().equals(password_verify1.getText().toString().trim())){
            Password.setText("");
            RePassword.setText("");
            Toast.makeText(this, "Passwords Do Not Match", Toast.LENGTH_LONG).show();

            P_confirm_submission = false;
        }
        else{
            P_confirm_submission = true;
        }

        //Verifying if Passwords are empty or not | PV = Password Verification
        if(password_verify1.getText().toString().equals("") || password_verify2.getText().toString().equals("")){

            Toast.makeText(this, "Passwords were not Entered", Toast.LENGTH_SHORT).show();

            PV_confirm_submission = false;
        }
        else{
            PV_confirm_submission = true;
        }

        //Verifying that Username DOES NOT exist in database (string.xml file)
        //If username already exists, clear field & ask user to register a different username
        //If ArrayList contains pre-existing UserName (List matches user input) - throw toast
        //U = UserName
        if(name_Username.contains(verify_username.toString().trim()) || verify_username.toString().equals("")) {
            UserName.setText("");
            Toast.makeText(getApplicationContext(), "Username is already Registered or was not Entered",
                    Toast.LENGTH_LONG).show();

            U_confirm_submission = false;
        }
        else{
            U_confirm_submission = true;
        }

        //Create OVERALL Verification Boolean
        boolean final_confirm_submission = FNE_confirm_submission && LNE_confirm_submission &&
                PHE_confirm_submission && E_confirm_submission && EV_confirm_submission &&
                P_confirm_submission && PV_confirm_submission && U_confirm_submission;

        if(final_confirm_submission == false){
        }
        else{
            Toast.makeText(this, "Submission Complete", Toast.LENGTH_SHORT).show();

            //If all matching fields match (emails, passwords - when submit button is pressed)
            //onClick() method is called
            //Initializes Intent and bundle for moving data from one activity to another
            Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
            Bundle bundle = new Bundle();

            //Adding items to the bundle - output to user for SecondActivity (confirmation)
            bundle.putString("fname", FirstName.getText().toString());
            bundle.putString("lname", LastName.getText().toString());
            bundle.putString("phnumber", PhoneNumber.getText().toString());
            bundle.putString("email", Email.getText().toString());
            bundle.putString("username", UserName.getText().toString());

            //Securely Store items into bundle
            intent.putExtras(bundle);
            //Start the Activity
            startActivity(intent);
        }
    }
}