package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailsForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);

        //get content from XML
        TextView username = (TextView) findViewById(R.id.UserName);
        TextView taskname = (TextView) findViewById(R.id.TaskName);
        TextView details = (TextView) findViewById(R.id.Details);
        TextView location = (TextView) findViewById(R.id.Location);
        TextView paymentamount = (TextView) findViewById(R.id.PaymentAmount);
        TextView completion = (TextView) findViewById(R.id.tv_Completion);

        Intent intent = getIntent();

        //TODO: get info from database.

        if(intent.hasExtra("username"+0)) {
            taskname.setText(intent.getStringExtra("username"+0));
        }
        username.setText("CoffeeUser");

        if(intent.hasExtra("taskname"+0)) {
           taskname.setText(intent.getStringExtra("taskname"+0));
        }
        taskname.setText("Get me some coffee");

        if(intent.hasExtra("details"+0)){
            details.setText(intent.getStringExtra("details"+0));
        }
        details.setText("Black, from Starbucks");

        if(intent.hasExtra("tasklocation"+0)) {
            location.setText(intent.getStringExtra("location"+0));
        }
        location.setText("Fontbonne University");

        if(intent.hasExtra("payment"+0)) {
            paymentamount.setText(intent.getStringExtra("payment"+0));
        }
        paymentamount.setText("$25");

        if(intent.hasExtra("completion")){//TODO: might be able to replace this with a database check?
            if(intent.getBooleanExtra("completion", false)){
                completion.setText("You have marked this task complete. The requester will be asked" +
                        " to confirm, then your pay will be transfered. Please contact an administrator" +
                        " if your payment isn't transferred within two days.");
                //TODO: replace 'the requester' with the username of the requester (get from database)?
            }
        }
    }

    //if Main is back, then
    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        Intent intent = getIntent();
        if(intent.hasExtra("source")) {
            if(intent.getStringExtra("source").equals("search")){
                i = new Intent(getApplicationContext(), SearchTasks.class);
            } //basically check and see our source and try to return to that
        }

        startActivity(i);
    }

    public void onBackPressed() {//deal with backbutton
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        Intent intent = getIntent();

        if(intent.hasExtra("source")) {
            if(intent.getStringExtra("source").equals("search")){
                i = new Intent(getApplicationContext(), SearchTasks.class);
            } //basically check and see our source and try to return to that
        }

        startActivity(i);
    }//end onBackPressed
}//end class
