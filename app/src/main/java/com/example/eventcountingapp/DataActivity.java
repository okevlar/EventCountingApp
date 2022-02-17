package com.example.eventcountingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class DataActivity extends AppCompatActivity {

    protected Toolbar dataBar;
    protected TextView eventA_count, eventB_count, eventC_count, totalEventCount;
    protected ListView listView;
    protected boolean toggle = false;

    private ArrayList<String> eventsArray, eventCountsArray;
    ArrayAdapter<String> adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.data_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.dataMenu){
            // toggle event names view
            toggle = !toggle;   // switch toggle on/off
            toggleDisplayMode(toggle);
        }
        else if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(DataActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        setupUI();
    }

    void setupUI() {
        // set up views
        setViewByIDs();
        // set up action bar
        setSupportActionBar(dataBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Data Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // fetch arrays from main activity
        eventsArray = getIntent().getStringArrayListExtra(Integer.toString(R.string.event_list));
        eventCountsArray = getIntent().getStringArrayListExtra(Integer.toString(R.string.count_list));
        adapter = new ArrayAdapter<>(DataActivity.this,android.R.layout.simple_list_item_1,eventsArray);

        String countA = getIntent().getStringExtra("countA");
        String countB = getIntent().getStringExtra("countB");
        String countC = getIntent().getStringExtra("countC");
        String total_count = getIntent().getStringExtra("totalCount");
        eventA_count.setText(String.format("%s%s", countA, getString(R.string.eventsDisplay)));
        eventB_count.setText(String.format("%s%s", countB, getString(R.string.eventsDisplay)));
        eventC_count.setText(String.format("%s%s", countC, getString(R.string.eventsDisplay)));
        totalEventCount.setText(total_count);

        listView.setAdapter(adapter);
    }

    // toggle name/number display
    private void toggleDisplayMode(boolean toggle){
        if(toggle){
            adapter = new ArrayAdapter<>(DataActivity.this, android.R.layout.simple_list_item_1,eventCountsArray);
        }else{
            adapter = new ArrayAdapter<>(DataActivity.this, android.R.layout.simple_list_item_1,eventsArray);
        }
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    // sets the edit texts with IDs from resources
    private void setViewByIDs(){
        dataBar = findViewById(R.id.data_toolbar);
        eventA_count = findViewById(R.id.data_activity_EventA_count_TextView);
        eventB_count = findViewById(R.id.data_activity_EventB_count_TextView);
        eventC_count = findViewById(R.id.data_activity_EventC_count_TextView);
        totalEventCount = findViewById(R.id.data_activity_EventTotal_count_TextView);
        listView = findViewById(R.id.data_activity_ListView1);
    }
}
