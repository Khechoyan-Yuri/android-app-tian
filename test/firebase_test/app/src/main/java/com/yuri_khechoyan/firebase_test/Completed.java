package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.icu.text.IDNA;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Completed extends AppCompatActivity {

    Integer completionStage = 0;
    Double price;
    TextView confirmq;
    EditText tip;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //grab the content from the XML
        setContentView(R.layout.activity_completed);
        confirmq = (TextView) findViewById(R.id.confirmq);
        tip = (EditText) findViewById(R.id.tip);
        btn = (Button) findViewById(R.id.btn_completed);

        //TODO: Database get price. For now I'll set a default
        price = 5.01;
    }

    public void Confirm(View v){
        if(completionStage == 0) {//tips
            confirmq.setText("Okay! You offered $" + price + " for this task. You can enter a tip below:");
            tip.setVisibility(View.VISIBLE);
            completionStage++;
        } else if(completionStage == 1) {//final confirmation
            if(tip.getText().toString().equals("")){
                //do nothing if there is no top to add
            } else {
                price = price + Double.parseDouble(tip.getText().toString());
            }

            confirmq.setText("Your total comes to $" + price + ". A receipt will be emailed to you." +
                    " Thank you!");

            //TODO: remove or hide task from database

            //would process prices here & email receipt

            //get rid of the tip EditText
            tip.setVisibility(View.GONE);

            //change the button to let us go back to Main
            btn.setText("Return Home");
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);

                    startActivity(i);
                }
            });//end onClickListener
        }//end else if
    }//end Confirm

}//end class
