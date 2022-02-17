package com.example.eventcountingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    protected SharedPreferenceHelper sharedPreferenceHelper;
    protected Button settingsButton, eventA_Button, eventB_Button, eventC_Button, dataButton;
    protected TextView updatedCount;
    protected int eventA_count, eventB_count, eventC_count, totalEventCount = 0;

    private ArrayList<String> eventsArray ;
    private ArrayList<String> eventCountsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       setupUI();
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        sharedPreferenceHelper = new SharedPreferenceHelper(MainActivity.this);
        String event1name = sharedPreferenceHelper.getA();
        String event2name = sharedPreferenceHelper.getB();
        String event3name = sharedPreferenceHelper.getC();
        String count_val = sharedPreferenceHelper.getMaxVal();

        if (event1name.equals("")){
            goToSettingsActivity();
        }
        if (event2name.equals("")){
            goToSettingsActivity();
        }
        if (event3name.equals("")){
            goToSettingsActivity();
        }
        else{
            eventA_Button.setText(event1name);
            eventB_Button.setText(event2name);
            eventC_Button.setText(event3name);
            updatedCount.setText(String.format("Max Count: %s", count_val));
        }
    }

    protected void setupUI()
    {
        setViewByIDs();
        eventsArray = new ArrayList<>();
        eventCountsArray = new ArrayList<>();

        sharedPreferenceHelper = new SharedPreferenceHelper(MainActivity.this);
        String count_val = sharedPreferenceHelper.getMaxVal();

        // SETTINGS ACTIVITY
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { goToSettingsActivity(); }
        });

        // DATA ACTIVITY
        dataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDataActivity();
            }
        });

        // EVENT BUTTONS
            // EVENT A
        eventA_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalEventCount+1 > Integer.parseInt(count_val))
                {
                    Toast.makeText(MainActivity.this, "Reached Max Count!", Toast.LENGTH_SHORT).show();
                }
                else{
                    eventsArray.add("Event A");
                    eventCountsArray.add("1");
                    eventA_count++;
                    totalEventCount++;
                    updatedCount.setText(String.format("Total Count: %s", Integer.toString(totalEventCount)));
                }
            }
        });
            // EVENT B
        eventB_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalEventCount+1 > Integer.parseInt(count_val))
                {
                    Toast.makeText(MainActivity.this, "Reached Max Count!", Toast.LENGTH_SHORT).show();
                }
                else {
                    eventsArray.add("Event B");
                    eventCountsArray.add("2");
                    eventB_count++;
                    totalEventCount++;
                    updatedCount.setText(String.format("Total Count: %s", Integer.toString(totalEventCount)));
                }
            }
        });
            // EVENT C
        eventC_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(totalEventCount+1 > Integer.parseInt(count_val))
                {
                    Toast.makeText(MainActivity.this, "Maximum count reached", Toast.LENGTH_SHORT).show();
                }
                else {
                    eventsArray.add("Event C");
                    eventCountsArray.add("3");
                    eventC_count++;
                    totalEventCount++;
                    updatedCount.setText(String.format("Total Count: %s", Integer.toString(totalEventCount)));
                }
            }
        });
    }

    // sets the edit texts with IDs from resources
    private void setViewByIDs(){
        settingsButton = findViewById(R.id.settings_button);
        dataButton = findViewById(R.id.showCounts_button);
        eventA_Button = findViewById(R.id.eventA_button);
        eventB_Button = findViewById(R.id.eventB_button);
        eventC_Button = findViewById(R.id.eventC_button);
        updatedCount = (TextView) findViewById(R.id.totalCount_changing_textView);
    }

    protected void goToSettingsActivity()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    protected void goToDataActivity()
    {
        Intent intent = new Intent(this, DataActivity.class);
        // pass along arrayLists and all counts to DataActivity
        intent.putStringArrayListExtra(Integer.toString(R.string.event_list),eventsArray);
        intent.putStringArrayListExtra(Integer.toString(R.string.count_list),eventCountsArray);
        intent.putExtra("countA",String.valueOf(eventA_count));
        intent.putExtra("countB",String.valueOf(eventB_count));
        intent.putExtra("countC",String.valueOf(eventC_count));
        intent.putExtra("totalCount",String.valueOf(totalEventCount));
        startActivity(intent);
    }

}