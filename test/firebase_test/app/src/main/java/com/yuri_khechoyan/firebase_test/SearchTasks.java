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

    ArrayList<String> username;

    ArrayList<String> taskname;

    ArrayList<String> taskdetails;

    ArrayList<String> tasklocation;

    ArrayList<String> payment;

    int arraylist_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tasks);

        arraylist_count=-1;

        taskname = new ArrayList<String>();
        taskdetails = new ArrayList<String>();
        tasklocation = new ArrayList<String>();
        payment = new ArrayList<String>();


        //essentially search database & populate the page, doing this in a loop for every result
        //we can probably set this up like the ViewAdapter hw if we want more efficiency
        //but the priority right now is completion, not perfection
        activity_search_tasks = (LinearLayout) findViewById(R.id.RL_activity_search_tasks);
        //for (int i = 0; i < options.length; i++) { //for each of the items in the database
        inflater = getLayoutInflater();

       /* TextView txt = (TextView) convertView.get(0).findViewById(R.id.box_subtitle1);
        txt.setText("Example changed title");//example of changing title as we would when fetching from database
        //We would also place an onclick method here for the buttons, which would take us to appropriate details
        //and add the task to accepted tasks
        accept.add((Button) convertView.get(0).findViewById(R.id.box_task_btn2));
        accept.get(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: in Database, set 'accepted', set Dundee to the current user
            }
        });*/

        myRef = FirebaseDatabase.getInstance().getReference("message");

        myRef.child("Dummy").setValue("");

        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                Log.d("DATABASE_USER",dataSnapshot.child("User").child("UserDetails" + 1).child("username").getValue(String.class));

                Log.d("DATABASE_USER",""+dataSnapshot.child("User").child("UserDetails" + 20).child("Tasks").child("taskname"+1).getValue(String.class));


                for (int i = 1; dataSnapshot.child("User").child("UserDetails" + i).child("username").getValue(String.class) != null; i++) {

                   for(int j = 1; dataSnapshot.child("User").child("UserDetails" + i).child("Tasks").child("taskname"+j).getValue(String.class) != null; j++) {

                        //Log.d("USER_DETAILS", dataSnapshot.child("User").child("UserDetails" + i).child("Tasks").child("taskname"+j).getValue(String.class));

                       arraylist_count++;

                        convertView = inflater.inflate(R.layout.box, null);
                        activity_search_tasks.addView(convertView);

                        TextView txt = (TextView) convertView.findViewById(R.id.box_subtitle1);
                        txt.setText(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("taskname"+j).getValue(String.class));//example of changing title as we would when fetching from database
                        //We would also place an onclick method here for the buttons, which would take us to appropriate details
                        //and add the task to accepted tasks
                        accept = (Button) convertView.findViewById(R.id.box_task_btn2);

                        details = (Button) convertView.findViewById(R.id.box_task_btn1);

                        username.add(dataSnapshot.child("User").child("UserDetails"+i).child("username").getValue(String.class));

                       taskname.add(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("taskname"+j).getValue(String.class));
                       taskdetails.add(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("details"+j).getValue(String.class));
                       tasklocation.add(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("tasklocation"+j).getValue(String.class));
                       payment.add(dataSnapshot.child("User").child("UserDetails"+i).child("Tasks").child("payment"+j).getValue(String.class));



                       accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                                intent.putExtra("taskname",taskname.get(arraylist_count));
                                intent.putExtra("taskdetails",taskdetails.get(arraylist_count));
                                intent.putExtra("tasklocation",tasklocation.get(arraylist_count));
                                intent.putExtra("payment",payment.get(arraylist_count));

                                startActivity(intent);
                            }
                         });

                        details.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }
                }

            }

            @Override
            public void onCancelled (DatabaseError databaseError){
                // Getting Post failed, log a message
                Log.w("Canceled", "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        };

        myRef.addValueEventListener(userListener);
    }

    public void DetailsLook(View v) {
        Intent i = new Intent(getApplicationContext(), DetailsForm.class);
        i.putExtra("source", "search");
        startActivity(i);
    }


    public void GoBack(View v) {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

        startActivity(i);
    }
}
