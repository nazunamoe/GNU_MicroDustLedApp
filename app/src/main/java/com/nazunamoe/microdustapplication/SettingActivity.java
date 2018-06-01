package com.nazunamoe.microdustapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

import com.jaredrummler.android.colorpicker.ColorPreference;

public class SettingActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    SharedPreferences sharedPref;
    Database Dataset = Database.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_setting);
        PreferenceManager.setDefaultValues(this, R.xml.settings_preferences, false);


        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int result = sharedPreferences.getInt(key,0);
        Color resultcolor = Color.valueOf(result);
        int red = (int)(resultcolor.red()*255);
        int green = (int)(resultcolor.green()*255);
        int blue = (int)(resultcolor.blue()*255);
        LEDColor colorled = new LEDColor(red,green,blue);
        Log.d("colorled",red+"");
        switch(key){
            case "pm10_color1-1":{
                Dataset.setpreset(1,1,colorled);
                break;
            }
            case "pm10_color1-2":{
                Dataset.setpreset(1,2,colorled);
                break;
            }
            case "pm10_color1-3":{
                Dataset.setpreset(1,3,colorled);
                break;
            }
            case "pm10_color1-4":{
                Dataset.setpreset(1,4,colorled);
                break;
            }
            case "pm10_color1-5":{
                Dataset.setpreset(1,5,colorled);
                break;
            }
            case "pm10_color1-6":{
                Dataset.setpreset(1,6,colorled);
                break;
            }
            case "pm10_color1-7":{
                Dataset.setpreset(1,7,colorled);
                break;
            }
            case "pm10_color1-8":{
                Dataset.setpreset(1,8,colorled);
                break;
            }
            case "pm10_color2-1":{
                Dataset.setpreset(2,1,colorled);
                break;
            }
            case "pm10_color2-2":{
                Dataset.setpreset(2,2,colorled);
                break;
            }
            case "pm10_color2-3":{
                Dataset.setpreset(2,3,colorled);
                break;
            }
            case "pm10_color2-4":{
                Dataset.setpreset(2,4,colorled);
                break;
            }
            case "pm10_color2-5":{
                Dataset.setpreset(2,5,colorled);
                break;
            }
            case "pm10_color2-6":{
                Dataset.setpreset(2,6,colorled);
                break;
            }
            case "pm10_color2-7":{
                Dataset.setpreset(2,7,colorled);
                break;
            }
            case "pm10_color2-8":{
                Dataset.setpreset(2,8,colorled);
                break;
            }
            case "pm25_color1-1":{
                Dataset.setpreset(3,1,colorled);
                break;
            }
            case "pm25_color1-2":{
                Dataset.setpreset(3,2,colorled);
                break;
            }
            case "pm25_color1-3":{
                Dataset.setpreset(3,3,colorled);
                break;
            }
            case "pm25_color1-4": {
                Dataset.setpreset(3,4,colorled);
                break;
            }
            case "pm25_color1-5":{
                Dataset.setpreset(3,5,colorled);
                break;
            }
            case "pm25_color1-6":{
                Dataset.setpreset(3,6,colorled);
                break;
            }
            case "pm25_color1-7":{
                Dataset.setpreset(3,7,colorled);
                break;
            }
            case "pm25_color1-8":{
                Dataset.setpreset(3,8,colorled);
                break;
            }
            case "pm25_color2-1":{
                Dataset.setpreset(4,1,colorled);
                break;
            }
            case "pm25_color2-2":{
                Dataset.setpreset(4,2,colorled);
                break;
            }
            case "pm25_color2-3":{
                Dataset.setpreset(4,3,colorled);
                break;
            }
            case "pm25_color2-4":{
                Dataset.setpreset(4,4,colorled);
                break;
            }
            case "pm25_color2-5":{
                Dataset.setpreset(4,5,colorled);
                break;
            }
            case "pm25_color2-6":{
                Dataset.setpreset(4,6,colorled);
                break;
            }
            case "pm25_color2-7":{
                Dataset.setpreset(4,7,colorled);
                break;
            }
            case "pm25_color2-8":{
                Dataset.setpreset(4,8,colorled);
                break;
            }
            case "normal_color1":{
                Dataset.setpreset(5,1,colorled);
                break;
            }
            case "normal_color2":{
                Dataset.setpreset(5,2,colorled);
                break;
            }
            case "normal_color3":{
                Dataset.setpreset(5,3,colorled);
                break;
            }
        }
        editor.commit();
    }
    public static class SettingsFragment extends PreferenceFragment
    {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_preferences);
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
                pickPreferenceObject(getPreferenceScreen().getPreference(i));
            }

            Preference save = findPreference("save_settings");
            save.setEnabled(false);
            save.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                @Override
                public boolean onPreferenceClick(Preference preference) {
                    Log.d("Fuck","save!");
                    return false;
                }
            }); //버튼 두개 리스너 설정할것



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

            Database database = Database.getInstance();
            if(database.getpreset(1,1) == null){

            }else{
                pm10_1_1.saveValue(database.getColor(0,0));
                pm10_1_2.saveValue(database.getColor(0,1));
                pm10_1_3.saveValue(database.getColor(0,2));
                pm10_1_4.saveValue(database.getColor(0,3));
                pm10_1_5.saveValue(database.getColor(0,4));
                pm10_1_6.saveValue(database.getColor(0,5));
                pm10_1_7.saveValue(database.getColor(0,6));
                pm10_1_8.saveValue(database.getColor(0,7));

                pm10_2_1.saveValue(database.getColor(1,0));
                pm10_2_2.saveValue(database.getColor(1,1));
                pm10_2_3.saveValue(database.getColor(1,2));
                pm10_2_4.saveValue(database.getColor(1,3));
                pm10_2_5.saveValue(database.getColor(1,4));
                pm10_2_6.saveValue(database.getColor(1,5));
                pm10_2_7.saveValue(database.getColor(1,6));
                pm10_2_8.saveValue(database.getColor(1,7));

                pm25_1_1.saveValue(database.getColor(2,0));
                pm25_1_2.saveValue(database.getColor(2,1));
                pm25_1_3.saveValue(database.getColor(2,2));
                pm25_1_4.saveValue(database.getColor(2,3));
                pm25_1_5.saveValue(database.getColor(2,4));
                pm25_1_6.saveValue(database.getColor(2,5));
                pm25_1_7.saveValue(database.getColor(2,6));
                pm25_1_8.saveValue(database.getColor(2,7));

                pm25_2_1.saveValue(database.getColor(3,0));
                pm25_2_2.saveValue(database.getColor(3,1));
                pm25_2_3.saveValue(database.getColor(3,2));
                pm25_2_4.saveValue(database.getColor(3,3));
                pm25_2_5.saveValue(database.getColor(3,4));
                pm25_2_6.saveValue(database.getColor(3,5));
                pm25_2_7.saveValue(database.getColor(3,6));
                pm25_2_8.saveValue(database.getColor(3,7));

                normal_1.saveValue(database.getColor(4,0));
                normal_2.saveValue(database.getColor(4,1));
                normal_3.saveValue(database.getColor(4,2));
            }
        }
        private void pickPreferenceObject(Preference p) {
            if (p instanceof PreferenceCategory) {
                PreferenceCategory cat = (PreferenceCategory) p;
                for (int i = 0; i < cat.getPreferenceCount(); i++) {
                    pickPreferenceObject(cat.getPreference(i));
                }
            } else {
            }
        }

    }

    public void updatedata(){

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

        Database database = Database.getInstance();

        pm10_1_1.saveValue(database.getColor(1,0));
        pm10_1_2.saveValue(database.getColor(1,1));
        pm10_1_3.saveValue(database.getColor(1,2));
        pm10_1_4.saveValue(database.getColor(1,3));
        pm10_1_5.saveValue(database.getColor(1,4));
        pm10_1_6.saveValue(database.getColor(1,5));
        pm10_1_7.saveValue(database.getColor(1,6));
        pm10_1_8.saveValue(database.getColor(1,7));

        pm10_2_1.saveValue(database.getColor(2,0));
        pm10_2_2.saveValue(database.getColor(2,1));
        pm10_2_3.saveValue(database.getColor(2,2));
        pm10_2_4.saveValue(database.getColor(2,3));
        pm10_2_5.saveValue(database.getColor(2,4));
        pm10_2_6.saveValue(database.getColor(2,5));
        pm10_2_7.saveValue(database.getColor(2,6));
        pm10_2_8.saveValue(database.getColor(2,7));

        pm25_1_1.saveValue(database.getColor(3,0));
        pm25_1_2.saveValue(database.getColor(3,1));
        pm25_1_3.saveValue(database.getColor(3,2));
        pm25_1_4.saveValue(database.getColor(3,3));
        pm25_1_5.saveValue(database.getColor(3,4));
        pm25_1_6.saveValue(database.getColor(3,5));
        pm25_1_7.saveValue(database.getColor(3,6));
        pm25_1_8.saveValue(database.getColor(3,7));

        pm25_2_1.saveValue(database.getColor(4,0));
        pm25_2_2.saveValue(database.getColor(4,1));
        pm25_2_3.saveValue(database.getColor(4,2));
        pm25_2_4.saveValue(database.getColor(4,3));
        pm25_2_5.saveValue(database.getColor(4,4));
        pm25_2_6.saveValue(database.getColor(4,5));
        pm25_2_7.saveValue(database.getColor(4,6));
        pm25_2_8.saveValue(database.getColor(4,7));

        normal_1.saveValue(database.getColor(5,0));
        normal_2.saveValue(database.getColor(5,1));
        normal_3.saveValue(database.getColor(5,2));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPref.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPref.unregisterOnSharedPreferenceChangeListener(this);
    }
}

