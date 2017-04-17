package com.example.jessie.dundeal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class  MainActivity extends AppCompatActivity {

    TextView requestHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CreateText();
        
         View convertView;
        LayoutInflater inflater;
        TextView txt;

        //essentially search database & populate the page, doing this in a loop for every result
        //we can probably set this up like the ViewAdapter hw if we want more efficiency
        //but the priority right now is completion, not perfection
        for(int i = 0; i < 4; i++) {//do for every task
            //get task info from database here
            LinearLayout tasks = (LinearLayout) findViewById(R.id.LL_tasks);
            //for (int i = 0; i < options.length; i++) { //for each of the items in the database
            inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.box, null);
            tasks.addView(convertView);

            //must use convertView.findViewById to fetch dynamically, or else all edits apply to one item
            txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
            //txt.setId(55+1);//for some reason doesn't accept singular numbers?
            txt.setText("Example changed title" + i);//example of changing title as we would when fetching from database
            //We would also place an onclick method here for the buttons, which would take us to appropriate details
            //and add the task to accepted tasks
         }//end for loop

        //essentially do the same thing for the requests
        //for(int i = 0; i < number_of_tasks; i++) {//do for every task
        //get task info from database here
        LinearLayout requests = (LinearLayout) findViewById(R.id.LL_requests);
        //for (int i = 0; i < options.length; i++) { //for each of the items in the database
            inflater = getLayoutInflater();
            convertView = inflater.inflate(R.layout.box, null);
            requests.addView(convertView);

            //must use convertView.findViewById to fetch dynamically, or else all edits apply to one item
            txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
            txt.setText("Example changed title2");//example of changing title as we would when fetching from database
            //We would also place an onclick method here for the buttons, which would take us to appropriate details
            //and add the task to accepted tasks
        // }//end for loop

    }

    public void CreateText() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            //requestHeader = (TextView) findViewById(R.id.request_header);
            requestHeader = (TextView) findViewById(R.id.subtitle2);
            requestHeader.setText(bundle.getString("taskname"));
            //requestHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
        }

    }

    public void RequestForm(View v){
        Intent i = new Intent(getApplicationContext(), RequestFormCreation.class);

        startActivity(i);

    }

    public void DetailsLook(View v) {
        Intent i = new Intent(getApplicationContext(), DetailsForm.class);

        startActivity(i);
    }

    public void EditRequest(View v) {
        Intent i = new Intent(getApplicationContext(), RequestFormCreation.class);

        startActivity(i);
    }
    
    public void SearchTasks(View v) {
        Intent i = new Intent(getApplicationContext(), SearchTasks.class);

        startActivity(i);
    }
}
