package com.example.eventcountingapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceHelper {

   // private Context context;
    private SharedPreferences sharedPreferences;    //TODO: maybe make final?

    public SharedPreferenceHelper(Context context){
        sharedPreferences = context.getSharedPreferences("settingsFile",Context.MODE_PRIVATE);
    }

    public void saveSettings(String nameA, String nameB, String nameC, String maxCount){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("Event A",nameA);
        editor.putString("Event B",nameB);
        editor.putString("Event C",nameC);
        editor.putString("Count",maxCount);
        editor.apply();
    }

    public String getA(){
        return sharedPreferences.getString("Event A","");
    }
    public String getB(){
        return sharedPreferences.getString("Event B","");
    }
    public String getC(){
        return sharedPreferences.getString("Event C","");
    }
    public String getMaxVal(){ return sharedPreferences.getString("Count","0"); }
}
