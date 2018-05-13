package com.nazunamoe.microdustapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

public class SettingPreferenceFragment extends PreferenceFragment {

    SharedPreferences prefs;

    Preference connect;
    Preference btstatus;

    Preference pm10status;
    ListPreference pm10custom;

    Preference pm25status;
    ListPreference pm25custom;

    Preference normalstatus;
    ListPreference normalcustom;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preferences);

        connect = findPreference("connect_bluetooth");
        btstatus = findPreference("status_bluetooth");

        pm10status = findPreference("status_pm10");
        pm10custom = (ListPreference) findPreference("pm10mode");

        pm25status = findPreference("status_pm25");
        pm25custom = (ListPreference) findPreference("pm25mode");

        normalstatus = findPreference("status_normal");
        normalcustom = (ListPreference) findPreference("normalmode");

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());



    }

    SharedPreferences.OnSharedPreferenceChangeListener prefListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        }
    };
}