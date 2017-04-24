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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SearchTasks extends AppCompatActivity {

    DatabaseReference myRef;

    LinearLayout layout;

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
        txt.setText("Example changed title");//example of changing title as we would when fetching from database
        //We would also place an onclick method here for the buttons, which would take us to appropriate details
        //and add the task to accepted tasks
        Button accept = (Button) convertView.findViewById(R.id.box_task_btn2);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: in Database, set 'accepted', set Dundee to the current user
            }
        });

        myRef = FirebaseDatabase.getInstance().getReference("message");

        ValueEventListener userListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                layout = (LinearLayout) findViewById(R.id.ST_Layout);
                for(int i =1; dataSnapshot.child("User").child("user" + i).getValue(String.class) != null; i++)
                    if(dataSnapshot.child("User").child("user" + i).getValue(String.class) != null) {
                        Button btn = new Button(getApplicationContext());

                        btn.setText("User: "+dataSnapshot.child("User").child("user" + i).getValue(String.class)
                        +"\nTask: "+dataSnapshot.child("TaskName").child("taskname" + i).getValue(String.class)
                        +"\nLocation: "+dataSnapshot.child("TaskLocation").child("tasklocation" + i).getValue(String.class)
                        +"\nPayment: "+dataSnapshot.child("Payment").child("payment" + i).getValue(String.class));

                        layout.addView(btn);

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
