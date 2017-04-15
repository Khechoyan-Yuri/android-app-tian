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


        username.setText("John Doe");

        taskname.setText("Get me some coffee");

        details.setText("Black, from Starbucks");

        location.setText("Fontbonne University");

        paymentamount.setText("$25");
    }

    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
}
