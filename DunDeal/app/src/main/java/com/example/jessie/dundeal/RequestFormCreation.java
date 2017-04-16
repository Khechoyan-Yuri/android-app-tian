package com.example.jessie.dundeal;

//Import Statements
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import java.util.ArrayList;



//***************
// MAIN CLASS
//***************

public class RequestFormCreation extends AppCompatActivity {

    boolean U_confirm_submission;

    SharedPreferences tracker;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_form_creation);

        //UI Elements Needed: Username, Task Name, Details, Location, Payment Amount
        //Buttons: Clear & Submit


        /*
            //Set Username object to array
            Username = (EditText) findViewById(R.id.et_UserName);
        */


    }

    //***************************************************************
    //  Method for CANCELING REQUEST FORM & Going back to Main Menu
    //**************************************************************

    public void CancelRequest(View v) {
        //Initializes and extracts Values to store into a bundle
        //for moving data to another activity
        EditText UserName = (EditText) findViewById(R.id.et_UserName);
        EditText TaskName = (EditText) findViewById(R.id.et_TaskName);
        EditText TaskDetails = (EditText) findViewById(R.id.et_Details);
        EditText TaskLocation = (EditText) findViewById(R.id.et_Location);
        EditText TaskPayment = (EditText) findViewById(R.id.et_PaymentAmount);

        //Clear EditText Fields (Convenience Protocol)
        UserName.setText("");
        TaskName.setText("");
        TaskDetails.setText("");
        TaskLocation.setText("");
        TaskPayment.setText("");

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        //Direct App back to Main Menu
        startActivity(intent);

        //Throw toast verifying of cancellation request
        Toast.makeText(this, "Request Canceled", Toast.LENGTH_SHORT).show();
    }

    //***********************************************************************************
    //Checking Validity of User Input (Username, Task name, Details, Location, Payment)
    //***********************************************************************************

    public void Submit(View v1) {

        //**************************************
        //EditText Fields Verification - START
        //**************************************

        //Initializes and extracts Values to store into a bundle
        //for moving data to another activity
        EditText UserName = (EditText) findViewById(R.id.et_UserName);
        EditText TaskName = (EditText) findViewById(R.id.et_TaskName);
        EditText TaskDetails = (EditText) findViewById(R.id.et_Details);
        EditText TaskLocation = (EditText) findViewById(R.id.et_Location);
        EditText TaskPayment = (EditText) findViewById(R.id.et_PaymentAmount);


        //Receive Text input from user, converting toString()
        String verify_username = UserName.getText().toString();
        String verify_taskName = TaskName.getText().toString();
        String verify_taskDetails = TaskDetails.getText().toString();
        String verify_taskLocation = TaskLocation.getText().toString();
        String verify_taskPayment = TaskPayment.getText().toString();

        //These booleans will be used to help identify if application
        //can move onto the 2nd Activity
        boolean UN_confirm_submission;
        boolean TN_confirm_submission;
        boolean TD_confirm_submission;
        boolean TL_confirm_submission;
        boolean TP_confirm_submission;

        //Container for an array of values - (i.e. already submitted UserNames) [strings.xml]
        //this will retrieve already used UserNames
        TypedArray convertArray = getResources().obtainTypedArray(R.array.username_array);

        //Create an ArrayList - to store the pre-registered UserNames
        ArrayList<String> name_Username = new ArrayList<>();

        //Cycles through elements inside of String-array & adds UserName to ArrayList
        for (int i = 0; i < convertArray.length(); i++) {
            name_Username.add(convertArray.getString(i));
        }

        //Verifying if Username is empty or not | UN = Username Entry
        if(verify_username.equals("")){
            Toast.makeText(this, "Username was not Entered", Toast.LENGTH_SHORT).show();
            UN_confirm_submission = false;
        }
        else{
            UN_confirm_submission = true;
        }

        //Verifying if Task Name is empty or not | TN = Task Name Entry
        if(verify_taskName.equals("")){
            Toast.makeText(this, "Task Name was not Entered", Toast.LENGTH_SHORT).show();
            TN_confirm_submission = false;
        }
        else{
            TN_confirm_submission = true;
        }

        //Verifying if Task Details is empty or not | TD = Task Details Entry
        if(verify_taskDetails.equals("")){
            Toast.makeText(this, "Task Details were not Entered", Toast.LENGTH_SHORT).show();
            TD_confirm_submission = false;
        }
        else{
            TD_confirm_submission = true;
        }

        //Verifying if Task Location was empty or not | TL = Task Location Entry
        if(verify_taskLocation.equals("")){
            Toast.makeText(this, "Task Location was not Entered", Toast.LENGTH_SHORT).show();
            TL_confirm_submission = false;
        }
        else{
            TL_confirm_submission = true;
        }

        //Verifying if Task Payment was empty or not | TP = Task Payment Entry
        if(verify_taskPayment.equals("")){
            Toast.makeText(this, "Task Payment was not Entered", Toast.LENGTH_SHORT).show();
            TP_confirm_submission = false;
        }
        else{
            TP_confirm_submission = true;
        }

        //**************************************
        //EditText Fields Verification - STOP
        //**************************************



        //************************************************
        // BUNDLE LOGIC - ---Needs Modification---
        //************************************************


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
        boolean final_confirm_submission = UN_confirm_submission && TN_confirm_submission &&
                TD_confirm_submission && TL_confirm_submission && TP_confirm_submission;

        if(final_confirm_submission == false){
        }
        else{
            Toast.makeText(this, "Submission Complete", Toast.LENGTH_SHORT).show();

            //If all matching fields match (emails, passwords - when submit button is pressed)
            //onClick() method is called
            //Initializes Intent and bundle for moving data from one activity to another
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            Bundle bundle = new Bundle();

            tracker = PreferenceManager.getDefaultSharedPreferences(this);

            int i = tracker.getInt("bundleNumber", 0);

            //Adding items to the bundle - output to user for SecondActivity (confirmation)
            bundle.putString("taskname"+i, TaskName.getText().toString());
            bundle.putString("tasklocation"+i,TaskLocation.getText().toString());
            bundle.putString("details"+i, TaskDetails.getText().toString());
            bundle.putString("payment"+i, TaskPayment.getText().toString());
            bundle.putString("username"+i, UserName.getText().toString());

            SharedPreferences.Editor editor = tracker.edit();

            editor.putInt("requestTracker", i);

            //Securely Store items into bundle
            intent.putExtras(bundle);
            //Start the Activity
            startActivity(intent);
        }
    }
}