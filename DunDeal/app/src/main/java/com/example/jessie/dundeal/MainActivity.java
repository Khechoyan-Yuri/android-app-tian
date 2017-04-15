package com.example.jessie.dundeal;

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

    }

    public void CreateText() {
        Bundle bundle = getIntent().getExtras();

        if(bundle != null) {
            requestHeader = (TextView) findViewById(R.id.request_header);

            requestHeader.setText(bundle.getString("taskname"));
            requestHeader.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 40);
        }

    }

    public void RequestForm(View v){
        setContentView(R.layout.request_form_creation);

    }
}
