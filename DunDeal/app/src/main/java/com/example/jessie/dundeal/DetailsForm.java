package com.example.jessie.dundeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);

        TextView username = (TextView) findViewById(R.id.UserName);
        TextView taskname = (TextView) findViewById(R.id.TaskName);
        TextView details = (TextView) findViewById(R.id.Details);
        TextView location = (TextView) findViewById(R.id.Location);
        TextView paymentamount = (TextView) findViewById(R.id.PaymentAmount);

        Intent intent = getIntent();

        if(intent.hasExtra("username"+0)) {
            taskname.setText(intent.getStringExtra("username"+0));
        }
        username.setText("Coffee");

        if(intent.hasExtra("taskname"+0)) {
           details.setText(intent.getStringExtra("taskname"+0));
        }
        details.setText("Get me some coffee");

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
    }

    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
}
