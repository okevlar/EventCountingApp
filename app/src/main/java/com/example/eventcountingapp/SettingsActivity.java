package com.example.eventcountingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {

    protected Button saveButton;
    protected EditText eventA_editText, eventB_editText, eventC_editText, maxCounts_editText;
    protected Toolbar settingsBar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_activity_modes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.editSettings){
            // toggle edit mode
            editMode(true);
        }
        else if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(SettingsActivity.this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setupUI();
    }

    void setupUI()
    {
        setViewByIDs();
        setSupportActionBar(settingsBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Settings Activity");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // open sharedPref file to save names
        SharedPreferenceHelper sharedPreferenceHelper = new SharedPreferenceHelper(SettingsActivity.this);
        String eventA = sharedPreferenceHelper.getA();
        String eventB = sharedPreferenceHelper.getB();
        String eventC = sharedPreferenceHelper.getC();
        String maxCounts = sharedPreferenceHelper.getMaxVal();

        // if fields are empty edit mode is on
        if(eventA.isEmpty() || eventB.isEmpty() || eventC.isEmpty()){
            editMode(true);
        }
        else {
            eventA_editText.setText(eventA);
            eventB_editText.setText(eventB);
            eventC_editText.setText(eventC);
            maxCounts_editText.setText(maxCounts);
            editMode(false);
        }

        // save names if valid
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String editA = eventA_editText.getText().toString().trim();
                String editB = eventB_editText.getText().toString().trim();
                String editC = eventC_editText.getText().toString().trim();
                String editMax = maxCounts_editText.getText().toString().trim();

                if(hasInvalid(editA) || editA.length() > 20){
                    Toast.makeText(SettingsActivity.this, "First name can only contain alphabetical letters and spaces. \n20 characters maximum",Toast.LENGTH_SHORT).show();
                }
                else if(hasInvalid(editB) || editB.length() > 20){
                    Toast.makeText(SettingsActivity.this, "Second name can only contain alphabetical letters and spaces. \n20 characters maximum",Toast.LENGTH_SHORT).show();
                }
                else if(hasInvalid(editC) || editC.length() > 20){
                    Toast.makeText(SettingsActivity.this, "Third name can only contain alphabetical letters and spaces. \n20 characters maximum",Toast.LENGTH_SHORT).show();
                }
                else if (editMax.length() == 0){
                    Toast.makeText(SettingsActivity.this, "Max must be a number between 5 and 200",Toast.LENGTH_SHORT).show();
                    Log.d("Error","Text field is empty");
                }
                else if(Integer.parseInt(editMax) < 5 || Integer.parseInt(editMax) > 200){
                    Toast.makeText(SettingsActivity.this, "Max must be a number between 5 and 200",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SettingsActivity.this, "Choices Saved",Toast.LENGTH_SHORT).show();
                    sharedPreferenceHelper.saveSettings(editA,editB,editC,editMax);
                    editMode(false);
                }
            }
        });
    }

    // sets the edit texts with IDs from resources
    private void setViewByIDs(){
        eventA_editText = findViewById(R.id.editCounter1);
        eventB_editText = findViewById(R.id.editCounter2);
        eventC_editText = findViewById(R.id.editCounter3);
        maxCounts_editText = findViewById(R.id.maxCountsEditText);
        saveButton = findViewById(R.id.saveButton);
        settingsBar = findViewById(R.id.settings_toolbar);
    }

    // checks the input text for invalid entries (special characters or numbers)
    private boolean hasInvalid(String text){
        //String specialCharactersString = "!@#$%&*()'+,-./:;<=>?[]^_`{|}";
        for(int i =0;i<text.length();i++){
            char c = text.charAt(i);
            if(!Character.isDigit(c) && !Character.isLetter(c) && !Character.isWhitespace(c)
                || Character.isDigit(c)){
                return true;
            }
        }
        return false;
    }

    // used to toggle edit settings option
    private void editMode(boolean edit){
        // if in edit mode --> allow text to be edited and lighten text colour
        if(edit){
            eventA_editText.setTextColor(getResources().getColor(R.color.purple_200));    // to change input colour to purple
            eventB_editText.setTextColor(getResources().getColor(R.color.purple_200));
            eventC_editText.setTextColor(getResources().getColor(R.color.purple_200));
            maxCounts_editText.setTextColor(getResources().getColor(R.color.purple_200));

            eventA_editText.setEnabled(true);
            eventB_editText.setEnabled(true);
            eventC_editText.setEnabled(true);
            maxCounts_editText.setEnabled(true);
            saveButton.setEnabled(true);
        }
        else{
            eventA_editText.setTextColor(getResources().getColor(R.color.purple_500));    // to change input colour to purple
            eventB_editText.setTextColor(getResources().getColor(R.color.purple_500));
            eventC_editText.setTextColor(getResources().getColor(R.color.purple_500));
            maxCounts_editText.setTextColor(getResources().getColor(R.color.purple_500));

            eventA_editText.setEnabled(false);
            eventB_editText.setEnabled(false);
            eventC_editText.setEnabled(false);
            maxCounts_editText.setEnabled(false);
            saveButton.setEnabled(false);
        }
    }
}