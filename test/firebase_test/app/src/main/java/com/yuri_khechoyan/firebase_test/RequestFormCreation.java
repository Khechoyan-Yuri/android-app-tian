package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

//***************
// MAIN CLASS
//***************

public class RequestFormCreation extends AppCompatActivity {

    int user_count;

    int task_count;

    boolean U_confirm_submission;

    SharedPreferences tracker;

    DatabaseReference myRef;

    DatabaseReference testRef;

    boolean username_exists;

    EditText name_Username;

    String verify_username;

    EditText Sub_UserName;

    EditText Sub_TaskName;

    EditText Sub_TaskDetails;

    EditText Sub_TaskLocation;

    EditText Sub_TaskPayment;

    SharedPreferences.Editor editor;

    Bundle bundle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_form_creation);

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
        Sub_TaskName = (EditText) findViewById(R.id.et_TaskName);
        Sub_TaskDetails = (EditText) findViewById(R.id.et_Details);
        Sub_TaskLocation = (EditText) findViewById(R.id.et_Location);
        Sub_TaskPayment = (EditText) findViewById(R.id.et_PaymentAmount);


        //Receive Text input from user, converting toString()
        String verify_taskName = Sub_TaskName.getText().toString();
        String verify_taskDetails = Sub_TaskDetails.getText().toString();
        String verify_taskLocation = Sub_TaskLocation.getText().toString();
        String verify_taskPayment = Sub_TaskPayment.getText().toString();

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

        //Verifying if Task Name is empty or not | TN = Task Name Entry
        if (verify_taskName.equals("")) {
            Toast.makeText(this, "Task Name was not Entered", Toast.LENGTH_SHORT).show();
            TN_confirm_submission = false;
        } else {
            TN_confirm_submission = true;
        }

        //Verifying if Task Details is empty or not | TD = Task Details Entry
        if (verify_taskDetails.equals("")) {
            Toast.makeText(this, "Task Details were not Entered", Toast.LENGTH_SHORT).show();
            TD_confirm_submission = false;
        } else {
            TD_confirm_submission = true;
        }

        //Verifying if Task Location was empty or not | TL = Task Location Entry
        if (verify_taskLocation.equals("")) {
            Toast.makeText(this, "Task Location was not Entered", Toast.LENGTH_SHORT).show();
            TL_confirm_submission = false;
        } else {
            TL_confirm_submission = true;
        }

        //Verifying if Task Payment was empty or not | TP = Task Payment Entry
        if (verify_taskPayment.equals("")) {
            Toast.makeText(this, "Task Payment was not Entered", Toast.LENGTH_SHORT).show();
            TP_confirm_submission = false;
        } else if (Float.parseFloat(verify_taskPayment) < 5) {
            Toast.makeText(this, "Minimum payment is $5", Toast.LENGTH_SHORT).show();
            TP_confirm_submission = false;
        } else {
            TP_confirm_submission = true;
        }

        //**************************************
        //EditText Fields Verification - STOP
        //**************************************


        //************************************************
        // BUNDLE LOGIC - ---Needs Modification---
        //************************************************


        //Initializes database reference to database "message"
        myRef = FirebaseDatabase.getInstance().getReference("message");

        //Tests whether the username exists or not in the database
        username_exists = false;



            //Create OVERALL Verification Boolean
            boolean final_confirm_submission = TN_confirm_submission &&
                    TD_confirm_submission && TL_confirm_submission && TP_confirm_submission;

            if (final_confirm_submission == false) {
            } else {
                Toast.makeText(this, "Submission Complete", Toast.LENGTH_SHORT).show();

                //If all matching fields match (emails, passwords - when submit button is pressed)
                //onClick() method is called
                //Initializes Intent and bundle for moving data from one activity to another
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                bundle = new Bundle();

                tracker = PreferenceManager.getDefaultSharedPreferences(this);


                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //Determines if the username exists

                editor = tracker.edit();

                ValueEventListener userListener = new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for( int i =1; dataSnapshot.child("User").child("UserDetails" + i).child("username").getValue(String.class) != null; i++) {
                            if ( tracker.getString("username", null).equals(dataSnapshot.child("User").child("username" + i).getValue(String.class)) &&
                                    dataSnapshot.child("User").child("username" + i).getValue(String.class) != null) {

                                user_count = i;
                            }

                        }

                        for(int i = 1; dataSnapshot.child("User").child("UserDetails" + user_count).child("taskname"+i).getValue(String.class) != null; i++) {
                            task_count = i;
                        }

                        if (username_exists == false) {

                            myRef.child("User").child("UserDetails"+user_count).child("Tasks").child("taskname" + task_count).setValue(Sub_TaskName.getText().toString());
                            myRef.child("User").child("UserDetails"+user_count).child("Tasks").child("tasklocation" + task_count).setValue(Sub_TaskLocation.getText().toString());
                            myRef.child("User").child("UserDetails"+user_count).child("Tasks").child("details" + task_count).setValue(Sub_TaskDetails.getText().toString());
                            myRef.child("User").child("UserDetails"+user_count).child("Tasks").child("payment" + task_count).setValue(Sub_TaskPayment.getText().toString());
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

                if(username_exists) {
                    Toast.makeText(getApplicationContext(), "Please enter a new name", Toast.LENGTH_LONG);
                }

                else {
                    editor.putInt("requestTracker", tracker.getInt("requestTracker", 0)+1);
                    editor.putString("TaskName"+tracker.getInt("requestTracker", 0)+1, verify_taskName);
                    editor.putString("TaskLocation"+tracker.getInt("requestTracker", 0)+1, verify_taskLocation);
                    editor.putString("Details"+tracker.getInt("requestTracker", 0)+1, verify_taskDetails);
                    editor.putString("Payment"+tracker.getInt("requestTracker", 0)+1, verify_taskPayment);

                    editor.commit();

                    //Securely Store items into bundle
                    intent.putExtras(bundle);
                    //Start the Activity
                    startActivity(intent);
                }
            }
        }
}
