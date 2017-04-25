package com.yuri_khechoyan.firebase_test;

//##########################################################################
/*	PROGRAM TITLE: DunDeal
    AUTHORS: Yuri Khechoyan, Jessie Wilkins, Leah Perry
    COURSE NUMBER: CIS 472
    COURSE SECTION NUMBER: 01
    INSTRUCTOR NAME: Dr. Tian
    PROJECT NAME: TEAM PROJECT #1 (Final)
    PRODUCTION NUMBER: DD-TP-1F
    DUE DATE: 04/25/2017
//##########################################################################

SUMMARY

    This program is designed to be a Service-based application.
    Application is similar to the Uber platform.
    When app is launched, user will be able to log in to their respective account (future implementation)
    When inside of the account, the end user has the ability to create tasks that
    would be completed by other users (called Dundies).
    The end-user that creates tasks, has the ability to:

        -Set a title for the task
        -Provide details (if title is not self-explanatory)
        -Set location where the task(s) need to take place (University campus)*
        -Set the payment amount for this task that needs to be completed.**


							------WHEN TASK IS SUBMITTED-----

    The Dundie(s) have the ability to either accept or decline the tasks that are posted
    App will also let the end-user know who is currently handling the task (if task is accepted) (future implementation)


        *  This app will mainly be used by University students. Therefore, typical location
           for tasks would be the University at which the student resides/attends. (line 25)

        ** The minimum required payment is $5. The end-user that is creating the task can
           set a higher payment amount (based on task difficulty & end-user's discretion).
           Tips may also be applied (if the end-user so chooses) (line 26) (future implementation?)


//##########################################################################

INPUT

        -Task Name
        -Task Details (if needed)
        -Task Location (Required)
        -Payment Amount for Task (minimum = $5; Tips are optional)

//##########################################################################

OUTPUT

    Application will post newly created tasks to the overall Task board (based on GPS location & radius)
    When Task is completed, it is removed

//##########################################################################

ASSUMPTIONS

- App has to have a stable WiFi or Cellular Connection (3G/4G/LTE, etc.)
- End-user has the ability to fill out the EditText fields properly
- The dundie has the ability to carry out & complete the tasks that they accept
*/
//##########################################################################

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    SharedPreferences pref;

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        intent = getIntent();

        //Get preferences to see if a username exists
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        String uname = pref.getString("username", "");

        Log.d("uname", uname);
        if(uname.equals("")){
            //if the username isn't available, take to account creation
            Intent i = new Intent(getApplicationContext(), AccountCreation.class);

            startActivity(i);
        }
        //We start in MainActivity instead of account creation because most users will usually
        //have usernames saved, so this means less swapping

        View convertView;
        LayoutInflater inflater;
        TextView txt;
        Button btn1;
        Button btn2;

        //essentially search database & populate the page, doing this in a loop for every result
        //we can probably set this up like the ViewAdapter hw if we want more efficiency
        //but the priority right now is completion, not perfection

        for(int i = 0; i < 4; i++) {//do for every task
            if (intent.getStringExtra("taskname") != null) {
                //get task info from database here
                LinearLayout tasks = (LinearLayout) findViewById(R.id.LL_tasks);
                //for (int i = 0; i < options.length; i++) { //for each of the items in the database
                inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.main_task_box, null);
                tasks.addView(convertView);

                //must use convertView.findViewById to fetch dynamically, or else all edits apply to one item
                txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
                //txt.setId(55+1);//for some reason doesn't accept singular numbers?
                txt.setText(intent.getStringExtra("taskname"));//example of changing title as we would when fetching from database
                //We would also place an onclick method here for the buttons, which would take us to appropriate details
                //and add the task to accepted tasks
            }

        }


        //essentially do the same thing for the requests
        //get task info from database here
        LinearLayout requests = (LinearLayout) findViewById(R.id.LL_requests);

        //for (int i = 0; i < options.length; i++) { //TODO: for each of the items in the database
        //inflate the box and add it to our final result
        inflater = getLayoutInflater();
        convertView = inflater.inflate(R.layout.main_request_box, null);
        requests.addView(convertView);

        //edit the content of the boxes
        //must use convertView.findViewById to fetch dynamically, or else all edits apply to one item
        txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
        txt.setText("Example changed title2");//example of changing title as we would when fetching from database
        //We would also place an onclick method here for the buttons, which would take us to appropriate details
        //and add the task to accepted tasks
        btn1 = (Button) convertView.findViewById(R.id.request_btn1text);


        //TODO: Database: if(acceptedbySomeone){
        btn1.setText("User accepted");
        //replace 'user' with username? only that risks messing with button size. Maybe just 'accepted'
        btn2 = (Button) convertView.findViewById(R.id.request_btn2text);


        //} else if (TODO: completed by someone){
        btn1.setVisibility(View.GONE);
        btn2.setText("Completed");
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Completed.class);

                startActivity(i);
            }
        });
        //}


        RelativeLayout.LayoutParams testLP = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

        testLP.addRule(RelativeLayout.CENTER_IN_PARENT);
        testLP.addRule(RelativeLayout.BELOW, txt.getId());

        btn2.setLayoutParams(testLP);
        // }//end for loop

    }//end onCreate

    //small method to take us to requests
    public void RequestForm(View v){
        Intent i = new Intent(getApplicationContext(), RequestFormCreation.class);

        startActivity(i);
    }//end RequestForm

    //small method to take us to details
    public void DetailsLook(View v) {
        Intent i = new Intent(getApplicationContext(), DetailsForm.class);

        startActivity(i);
    }//end DetailsLook

    //small method to take us to completion-version of details
    public void Complete(View v) {
        Intent i = new Intent(getApplicationContext(), DetailsForm.class);
        i.putExtra("completion", true);
        //TODO: set 'completed' in database
        startActivity(i);
    }//end Complete

    //function for editing requests
    public void EditRequest(View v) {
        Intent i = new Intent(getApplicationContext(), RequestFormCreation.class);
        //TODO: connect RequestFormCreation with database to get and change info
        i.putExtra("source", "edit");//basically just an extra to say we want to edit instead of make new
        //put an extra to indicate which task needs to be edited? or something else for database
        startActivity(i);
    }//end EditRequest

    //small method to take us to search
    public void SearchTasks(View v) {
        Intent i = new Intent(getApplicationContext(), SearchTasks.class);

        startActivity(i);
    }//end searchtasks

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void onBackPressed() {//deal with backbutton
        //do nothing, since this is the main page. Prevents
    }//end onBackPressed

}//end class
