package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SearchTasks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tasks);

        View convertView;

        //essentially search database & populate the page, doing this in a loop for every result
        //we can probably set this up like the ViewAdapter hw if we want more efficiency
        //but the priority right now is completion, not perfection
        LayoutInflater inflater;
        LinearLayout activity_search_tasks = (LinearLayout) findViewById(R.id.RL_activity_search_tasks);
        //for (int i = 0; i < options.length; i++) { //for each of the items in the database
        inflater = getLayoutInflater();
        convertView = inflater.inflate(R.layout.box, null);
        activity_search_tasks.addView(convertView);

        TextView txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
        txt.setText("Example changed title");//example of changin title as we would when fetching from database
        //We would also place an onclick method here for the buttons, which would take us to appropriate details
        //and add the task to accepted tasks

    }


    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
}
