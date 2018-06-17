package com.nazunamoe.microdustapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jaredrummler.android.colorpicker.ColorPreference;

public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
}

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = sharedPref.edit();
        int result = sharedPref.getInt(key,0);
        Color resultcolor = Color.valueOf(result);
        int red = (int)(resultcolor.red()*255);
        int green = (int)(resultcolor.green()*255);
        int blue = (int)(resultcolor.blue()*255);
        LEDColor colorled = new LEDColor(red,green,blue);
        switch(key){
            case "pm10_color1-1":{
                editor.putInt("pm10_color1-0_red",red);
                editor.putInt("pm10_color1-0_green",green);
                editor.putInt("pm10_color1-0_blue",blue);
                break;
            }
            case "pm10_color1-2":{
                editor.putInt("pm10_color1-1_red",red);
                editor.putInt("pm10_color1-1_green",green);
                editor.putInt("pm10_color1-1_blue",blue);
                break;
            }
            case "pm10_color1-3":{
                editor.putInt("pm10_color1-2_red",red);
                editor.putInt("pm10_color1-2_green",green);
                editor.putInt("pm10_color1-2_blue",blue);
                break;
            }
            case "pm10_color1-4":{
                editor.putInt("pm10_color1-3_red",red);
                editor.putInt("pm10_color1-3_green",green);
                editor.putInt("pm10_color1-3_blue",blue);
                break;
            }
            case "pm10_color1-5":{
                editor.putInt("pm10_color1-4_red",red);
                editor.putInt("pm10_color1-4_green",green);
                editor.putInt("pm10_color1-4_blue",blue);
                break;
            }
            case "pm10_color1-6":{
                editor.putInt("pm10_color1-5_red",red);
                editor.putInt("pm10_color1-5_green",green);
                editor.putInt("pm10_color1-5_blue",blue);
                break;
            }
            case "pm10_color1-7":{
                editor.putInt("pm10_color1-6_red",red);
                editor.putInt("pm10_color1-6_green",green);
                editor.putInt("pm10_color1-6_blue",blue);
                break;
            }
            case "pm10_color1-8":{
                editor.putInt("pm10_color1-7_red",red);
                editor.putInt("pm10_color1-7_green",green);
                editor.putInt("pm10_color1-7_blue",blue);
                break;
            }
            case "pm10_color2-1":{
                editor.putInt("pm10_color2-0_red",red);
                editor.putInt("pm10_color2-0_green",green);
                editor.putInt("pm10_color2-0_blue",blue);
                break;
            }
            case "pm10_color2-2":{
                editor.putInt("pm10_color2-1_red",red);
                editor.putInt("pm10_color2-1_green",green);
                editor.putInt("pm10_color2-1_blue",blue);
                break;
            }
            case "pm10_color2-3":{
                editor.putInt("pm10_color2-2_red",red);
                editor.putInt("pm10_color2-2_green",green);
                editor.putInt("pm10_color2-2_blue",blue);
                break;
            }
            case "pm10_color2-4":{
                editor.putInt("pm10_color2-3_red",red);
                editor.putInt("pm10_color2-3_green",green);
                editor.putInt("pm10_color2-3_blue",blue);
                break;
            }
            case "pm10_color2-5":{
                editor.putInt("pm10_color2-4_red",red);
                editor.putInt("pm10_color2-4_green",green);
                editor.putInt("pm10_color2-4_blue",blue);
                break;
            }
            case "pm10_color2-6":{
                editor.putInt("pm10_color2-5_red",red);
                editor.putInt("pm10_color2-5_green",green);
                editor.putInt("pm10_color2-5_blue",blue);
                break;
            }
            case "pm10_color2-7":{
                editor.putInt("pm10_color2-6_red",red);
                editor.putInt("pm10_color2-6_green",green);
                editor.putInt("pm10_color2-6_blue",blue);
                break;
            }
            case "pm10_color2-8":{
                editor.putInt("pm10_color2-7_red",red);
                editor.putInt("pm10_color2-7_green",green);
                editor.putInt("pm10_color2-7_blue",blue);
                break;
            }
            case "pm25_color1-1":{
                editor.putInt("pm25_color1-0_red",red);
                editor.putInt("pm25_color1-0_green",green);
                editor.putInt("pm25_color1-0_blue",blue);
                break;
            }
            case "pm25_color1-2":{
                editor.putInt("pm25_color1-1_red",red);
                editor.putInt("pm25_color1-1_green",green);
                editor.putInt("pm25_color1-1_blue",blue);
                break;
            }
            case "pm25_color1-3":{
                editor.putInt("pm25_color1-2_red",red);
                editor.putInt("pm25_color1-2_green",green);
                editor.putInt("pm25_color1-2_blue",blue);
                break;
            }
            case "pm25_color1-4": {
                editor.putInt("pm25_color1-3_red",red);
                editor.putInt("pm25_color1-3_green",green);
                editor.putInt("pm25_color1-3_blue",blue);
                break;
            }
            case "pm25_color1-5":{ ;
                editor.putInt("pm25_color1-4_red",red);
                editor.putInt("pm25_color1-4_green",green);
                editor.putInt("pm25_color1-4_blue",blue);
                break;
            }
            case "pm25_color1-6":{
                editor.putInt("pm25_color1-5_red",red);
                editor.putInt("pm25_color1-5_green",green);
                editor.putInt("pm25_color1-5_blue",blue);
                break;
            }
            case "pm25_color1-7":{
                editor.putInt("pm25_color1-6_red",red);
                editor.putInt("pm25_color1-6_green",green);
                editor.putInt("pm25_color1-6_blue",blue);
                break;
            }
            case "pm25_color1-8":{
                editor.putInt("pm25_color1-7_red",red);
                editor.putInt("pm25_color1-7_green",green);
                editor.putInt("pm25_color1-7_blue",blue);
                break;
            }
            case "pm25_color2-1":{
                editor.putInt("pm25_color2-0_red",red);
                editor.putInt("pm25_color2-0_green",green);
                editor.putInt("pm25_color2-0_blue",blue);
                break;
            }
            case "pm25_color2-2":{
                editor.putInt("pm25_color2-1_red",red);
                editor.putInt("pm25_color2-1_green",green);
                editor.putInt("pm25_color2-1_blue",blue);
                break;
            }
            case "pm25_color2-3":{
                editor.putInt("pm25_color2-2_red",red);
                editor.putInt("pm25_color2-2_green",green);
                editor.putInt("pm25_color2-2_blue",blue);
                break;
            }
            case "pm25_color2-4":{
                editor.putInt("pm25_color2-3_red",red);
                editor.putInt("pm25_color2-3_green",green);
                editor.putInt("pm25_color2-3_blue",blue);
                break;
            }
            case "pm25_color2-5":{
                editor.putInt("pm25_color2-4_red",red);
                editor.putInt("pm25_color2-4_green",green);
                editor.putInt("pm25_color2-4_blue",blue);
                break;
            }
            case "pm25_color2-6":{
                editor.putInt("pm25_color2-5_red",red);
                editor.putInt("pm25_color2-5_green",green);
                editor.putInt("pm25_color2-5_blue",blue);
                break;
            }
            case "pm25_color2-7":{
                editor.putInt("pm25_color2-6_red",red);
                editor.putInt("pm25_color2-6_green",green);
                editor.putInt("pm25_color2-6_blue",blue);
                break;
            }
            case "pm25_color2-8":{ ;
                editor.putInt("pm25_color2-7_red",red);
                editor.putInt("pm25_color2-7_green",green);
                editor.putInt("pm25_color2-7_blue",blue);
                break;
            }
            case "normal_color_1":{
                editor.putInt("normal_color0_red",red);
                editor.putInt("normal_color0_green",green);
                editor.putInt("normal_color0_blue",blue);
                break;
            }
            case "normal_color_2":{
                editor.putInt("normal_color1_red",red);
                editor.putInt("normal_color1_green",green);
                editor.putInt("normal_color1_blue",blue);
                break;
            }
            case "normal_color_3":{
                editor.putInt("normal_color2_red",red);
                editor.putInt("normal_color2_green",green);
                editor.putInt("normal_color2_blue",blue);
                break;
            }
            case "normal_color_4":{
                editor.putInt("normal_color3_red",red);
                editor.putInt("normal_color3_green",green);
                editor.putInt("normal_color3_blue",blue);
                break;
            }
            case "normal_color_5":{
                editor.putInt("normal_color4_red",red);
                editor.putInt("normal_color4_green",green);
                editor.putInt("normal_color4_blue",blue);
                break;
            }
        }
        editor.commit();
        editor.apply();
    }
    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
            addPreferencesFromResource(R.xml.settings_preferences);

            ColorPreference pm10_1_1 = (ColorPreference) findPreference("pm10_color1-1");
            ColorPreference pm10_1_2 = (ColorPreference) findPreference("pm10_color1-2");
            ColorPreference pm10_1_3 = (ColorPreference) findPreference("pm10_color1-3");
            ColorPreference pm10_1_4 = (ColorPreference) findPreference("pm10_color1-4");
            ColorPreference pm10_1_5 = (ColorPreference) findPreference("pm10_color1-5");
            ColorPreference pm10_1_6 = (ColorPreference) findPreference("pm10_color1-6");
            ColorPreference pm10_1_7 = (ColorPreference) findPreference("pm10_color1-7");
            ColorPreference pm10_1_8 = (ColorPreference) findPreference("pm10_color1-8");
            ColorPreference pm10_2_1 = (ColorPreference) findPreference("pm10_color2-1");
            ColorPreference pm10_2_2 = (ColorPreference) findPreference("pm10_color2-2");
            ColorPreference pm10_2_3 = (ColorPreference) findPreference("pm10_color2-3");
            ColorPreference pm10_2_4 = (ColorPreference) findPreference("pm10_color2-4");
            ColorPreference pm10_2_5 = (ColorPreference) findPreference("pm10_color2-5");
            ColorPreference pm10_2_6 = (ColorPreference) findPreference("pm10_color2-6");
            ColorPreference pm10_2_7 = (ColorPreference) findPreference("pm10_color2-7");
            ColorPreference pm10_2_8 = (ColorPreference) findPreference("pm10_color2-8");

            ColorPreference pm25_1_1 = (ColorPreference) findPreference("pm25_color1-1");
            ColorPreference pm25_1_2 = (ColorPreference) findPreference("pm25_color1-2");
            ColorPreference pm25_1_3 = (ColorPreference) findPreference("pm25_color1-3");
            ColorPreference pm25_1_4 = (ColorPreference) findPreference("pm25_color1-4");
            ColorPreference pm25_1_5 = (ColorPreference) findPreference("pm25_color1-5");
            ColorPreference pm25_1_6 = (ColorPreference) findPreference("pm25_color1-6");
            ColorPreference pm25_1_7 = (ColorPreference) findPreference("pm25_color1-7");
            ColorPreference pm25_1_8 = (ColorPreference) findPreference("pm25_color1-8");
            ColorPreference pm25_2_1 = (ColorPreference) findPreference("pm25_color2-1");
            ColorPreference pm25_2_2 = (ColorPreference) findPreference("pm25_color2-2");
            ColorPreference pm25_2_3 = (ColorPreference) findPreference("pm25_color2-3");
            ColorPreference pm25_2_4 = (ColorPreference) findPreference("pm25_color2-4");
            ColorPreference pm25_2_5 = (ColorPreference) findPreference("pm25_color2-5");
            ColorPreference pm25_2_6 = (ColorPreference) findPreference("pm25_color2-6");
            ColorPreference pm25_2_7 = (ColorPreference) findPreference("pm25_color2-7");
            ColorPreference pm25_2_8 = (ColorPreference) findPreference("pm25_color2-8");

            ColorPreference normal_1 = (ColorPreference) findPreference("normal_color_1");
            ColorPreference normal_2 = (ColorPreference) findPreference("normal_color_2");
            ColorPreference normal_3 = (ColorPreference) findPreference("normal_color_3");
            ColorPreference normal_4 = (ColorPreference) findPreference("normal_color_4");
            ColorPreference normal_5 = (ColorPreference) findPreference("normal_color_5");


            pm10_1_1.saveValue(Color.rgb(preferences.getInt("pm10_color1-0_red",0),preferences.getInt("pm10_color1-0_green",0),preferences.getInt("pm10_color1-0_blue",0)));
            pm10_1_2.saveValue(Color.rgb(preferences.getInt("pm10_color1-1_red",0),preferences.getInt("pm10_color1-1_green",0),preferences.getInt("pm10_color1-1_blue",0)));
            pm10_1_3.saveValue(Color.rgb(preferences.getInt("pm10_color1-2_red",0),preferences.getInt("pm10_color1-2_green",0),preferences.getInt("pm10_color1-2_blue",0)));
            pm10_1_4.saveValue(Color.rgb(preferences.getInt("pm10_color1-3_red",0),preferences.getInt("pm10_color1-3_green",0),preferences.getInt("pm10_color1-3_blue",0)));
            pm10_1_5.saveValue(Color.rgb(preferences.getInt("pm10_color1-4_red",0),preferences.getInt("pm10_color1-4_green",0),preferences.getInt("pm10_color1-4_blue",0)));
            pm10_1_6.saveValue(Color.rgb(preferences.getInt("pm10_color1-5_red",0),preferences.getInt("pm10_color1-5_green",0),preferences.getInt("pm10_color1-5_blue",0)));
            pm10_1_7.saveValue(Color.rgb(preferences.getInt("pm10_color1-6_red",0),preferences.getInt("pm10_color1-6_green",0),preferences.getInt("pm10_color1-6_blue",0)));
            pm10_1_8.saveValue(Color.rgb(preferences.getInt("pm10_color1-7_red",0),preferences.getInt("pm10_color1-7_green",0),preferences.getInt("pm10_color1-7_blue",0)));

            pm10_2_1.saveValue(Color.rgb(preferences.getInt("pm10_color2-0_red",0),preferences.getInt("pm10_color2-0_green",0),preferences.getInt("pm10_color2-0_blue",0)));
            pm10_2_2.saveValue(Color.rgb(preferences.getInt("pm10_color2-1_red",0),preferences.getInt("pm10_color2-1_green",0),preferences.getInt("pm10_color2-1_blue",0)));
            pm10_2_3.saveValue(Color.rgb(preferences.getInt("pm10_color2-2_red",0),preferences.getInt("pm10_color2-2_green",0),preferences.getInt("pm10_color2-2_blue",0)));
            pm10_2_4.saveValue(Color.rgb(preferences.getInt("pm10_color2-3_red",0),preferences.getInt("pm10_color2-3_green",0),preferences.getInt("pm10_color2-3_blue",0)));
            pm10_2_5.saveValue(Color.rgb(preferences.getInt("pm10_color2-4_red",0),preferences.getInt("pm10_color2-4_green",0),preferences.getInt("pm10_color2-4_blue",0)));
            pm10_2_6.saveValue(Color.rgb(preferences.getInt("pm10_color2-5_red",0),preferences.getInt("pm10_color2-5_green",0),preferences.getInt("pm10_color2-5_blue",0)));
            pm10_2_7.saveValue(Color.rgb(preferences.getInt("pm10_color2-6_red",0),preferences.getInt("pm10_color2-6_green",0),preferences.getInt("pm10_color2-6_blue",0)));
            pm10_2_8.saveValue(Color.rgb(preferences.getInt("pm10_color2-7_red",0),preferences.getInt("pm10_color2-7_green",0),preferences.getInt("pm10_color2-7_blue",0)));

            pm25_1_1.saveValue(Color.rgb(preferences.getInt("pm25_color1-0_red",0),preferences.getInt("pm25_color1-0_green",0),preferences.getInt("pm25_color1-0_blue",0)));
            pm25_1_2.saveValue(Color.rgb(preferences.getInt("pm25_color1-1_red",0),preferences.getInt("pm25_color1-1_green",0),preferences.getInt("pm25_color1-1_blue",0)));
            pm25_1_3.saveValue(Color.rgb(preferences.getInt("pm25_color1-2_red",0),preferences.getInt("pm25_color1-2_green",0),preferences.getInt("pm25_color1-2_blue",0)));
            pm25_1_4.saveValue(Color.rgb(preferences.getInt("pm25_color1-3_red",0),preferences.getInt("pm25_color1-3_green",0),preferences.getInt("pm25_color1-3_blue",0)));
            pm25_1_5.saveValue(Color.rgb(preferences.getInt("pm25_color1-4_red",0),preferences.getInt("pm25_color1-4_green",0),preferences.getInt("pm25_color1-4_blue",0)));
            pm25_1_6.saveValue(Color.rgb(preferences.getInt("pm25_color1-5_red",0),preferences.getInt("pm25_color1-5_green",0),preferences.getInt("pm25_color1-5_blue",0)));
            pm25_1_7.saveValue(Color.rgb(preferences.getInt("pm25_color1-6_red",0),preferences.getInt("pm25_color1-6_green",0),preferences.getInt("pm25_color1-6_blue",0)));
            pm25_1_8.saveValue(Color.rgb(preferences.getInt("pm25_color1-7_red",0),preferences.getInt("pm25_color1-7_green",0),preferences.getInt("pm25_color1-7_blue",0)));

            pm25_2_1.saveValue(Color.rgb(preferences.getInt("pm25_color2-0_red",0),preferences.getInt("pm25_color2-0_green",0),preferences.getInt("pm25_color2-0_blue",0)));
            pm25_2_2.saveValue(Color.rgb(preferences.getInt("pm25_color2-1_red",0),preferences.getInt("pm25_color2-1_green",0),preferences.getInt("pm25_color2-1_blue",0)));
            pm25_2_3.saveValue(Color.rgb(preferences.getInt("pm25_color2-2_red",0),preferences.getInt("pm25_color2-2_green",0),preferences.getInt("pm25_color2-2_blue",0)));
            pm25_2_4.saveValue(Color.rgb(preferences.getInt("pm25_color2-3_red",0),preferences.getInt("pm25_color2-3_green",0),preferences.getInt("pm25_color2-3_blue",0)));
            pm25_2_5.saveValue(Color.rgb(preferences.getInt("pm25_color2-4_red",0),preferences.getInt("pm25_color2-4_green",0),preferences.getInt("pm25_color2-4_blue",0)));
            pm25_2_6.saveValue(Color.rgb(preferences.getInt("pm25_color2-5_red",0),preferences.getInt("pm25_color2-5_green",0),preferences.getInt("pm25_color2-5_blue",0)));
            pm25_2_7.saveValue(Color.rgb(preferences.getInt("pm25_color2-6_red",0),preferences.getInt("pm25_color2-6_green",0),preferences.getInt("pm25_color2-6_blue",0)));
            pm25_2_8.saveValue(Color.rgb(preferences.getInt("pm25_color2-7_red",0),preferences.getInt("pm25_color2-7_green",0),preferences.getInt("pm25_color2-7_blue",0)));

            normal_1.saveValue(Color.rgb(preferences.getInt("normal_color0_red",0),preferences.getInt("normal_color0_green",0),preferences.getInt("normal_color0_blue",0)));
            normal_2.saveValue(Color.rgb(preferences.getInt("normal_color1_red",0),preferences.getInt("normal_color1_green",0),preferences.getInt("normal_color1_blue",0)));
            normal_3.saveValue(Color.rgb(preferences.getInt("normal_color2_red",0),preferences.getInt("normal_color2_green",0),preferences.getInt("normal_color2_blue",0)));
            normal_4.saveValue(Color.rgb(preferences.getInt("normal_color3_red",0),preferences.getInt("normal_color3_green",0),preferences.getInt("normal_color3_blue",0)));
            normal_5.saveValue(Color.rgb(preferences.getInt("normal_color4_red",0),preferences.getInt("normal_color4_green",0),preferences.getInt("normal_color4_blue",0)));

        }
    }


    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        sharedPref.registerOnSharedPreferenceChangeListener(this);
        super.onResume();
    }

    @Override
    protected void onPause() {
        sharedPref.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }
}

