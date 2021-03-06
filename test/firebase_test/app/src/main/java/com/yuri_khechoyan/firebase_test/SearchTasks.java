package com.yuri_khechoyan.firebase_test;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SearchTasks extends AppCompatActivity {

    DatabaseReference myRef;

    View convertView;

    LayoutInflater inflater;

    LinearLayout activity_search_tasks;

    Button accept;

    Button details;

    ArrayList<String> taskname;

    int arraylist_count;

    int user_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tasks);

        arraylist_count=0;
        user_count =0;



        //essentially search database & populate the page, doing this in a loop for every result
        //we can probably set this up like the ViewAdapter hw if we want more efficiency
        //but the priority right now is completion, not perfection
        activity_search_tasks = (LinearLayout) findViewById(R.id.RL_activity_search_tasks);
        //for (int i = 0; i < options.length; i++) { //for each of the items in the database


       /* TextView txt = (TextView) convertView.get(0).findViewById(R.id.box_subtitle1);
        txt.setText("Example changed title");//example of changing title as we would when fetching from database
        //We would also place an onclick method here for the buttons, which would take us to appropriate details
        //and add the task to accepted tasks
        accept.add((Button) convertView.get(0).findViewById(R.id.box_task_btn2));
        accept.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //in Database, set 'accepted', set Dundee to the current user
            }
        });*/

        //access messages part of database
        myRef = FirebaseDatabase.getInstance().getReference("message");

        myRef.child("Dummy").setValue("");

        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                taskname = new ArrayList<>();

                Log.d("DATABASE_USER",dataSnapshot.child("User").child("UserDetails" + 1).child("username").getValue(String.class));

                Log.d("DATABASE_USER",""+dataSnapshot.child("User").child("UserDetails" + 20).child("Tasks").child("taskname"+1).getValue(String.class));

                //for all users
                for (int i = 1; dataSnapshot.child("User").child("UserDetails" + i).child("username").getValue(String.class) != null; i++) {
                    user_count =i;
                    //for all tasks that the user has
                   for(int j = 1; dataSnapshot.child("User").child("UserDetails" + i).child("Tasks").child("taskname"+j).getValue(String.class) != null; j++) {

                        //Log.d("USER_DETAILS", dataSnapshot.child("User").child("UserDetails" + i).child("Tasks").child("taskname"+j).getValue(String.class));

                       inflater = getLayoutInflater();

                        arraylist_count = j;

                        //inflate the box
                        convertView = inflater.inflate(R.layout.box, null);
                        activity_search_tasks.addView(convertView);

                       //set title as task title
                        TextView txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
                        txt.setText(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("taskname"+j).getValue(String.class));//example of changing title as we would when fetching from database
                        //We would also place an onclick method here for the buttons, which would take us to appropriate details
                        //and add the task to accepted tasks
                        accept = (Button) convertView.findViewById(R.id.box_task_btn2);

                        details = (Button) convertView.findViewById(R.id.box_task_btn1);

                       accept.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View v) {
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                               intent.putExtra("arraylist_count",arraylist_count);

                               intent.putExtra("user_count", user_count);

                               startActivity(intent);
                           }
                       });


                       details.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //interface with database

                                //for now show basic details
                                //go to details
                                Intent i = new Intent(getApplicationContext(), DetailsForm.class);

                                //but tell it we're on the search page, so back functionality works properly
                                i.putExtra("source", "search");
                                startActivity(i);
                            }
                        });
                    }//end tasks


                }


            }//end users

            @Override
            public void onCancelled (DatabaseError databaseError){
                // Getting Post failed, log a message
                Log.w("Canceled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        }; //end event listener

        myRef.addValueEventListener(userListener);

                                //if accepted, accept and go to main
        // end onDataChange


    }//end onCreate

    //default details onClick
    public void DetailsLook(View v) {
        //go to details
        Intent i = new Intent(getApplicationContext(), DetailsForm.class);
        
        //but tell it we're on the search page, so back functionality works properly
        i.putExtra("source", "search");
        startActivity(i);
    }//end DetailsLooks

    
    //function for back button within layout
    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }//end goBack
    
   
}//end class
