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

            ColorPreference test = (ColorPreference) findPreference("pm10_color1-1");

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

