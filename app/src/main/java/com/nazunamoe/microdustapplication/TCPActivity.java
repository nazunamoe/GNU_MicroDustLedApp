package com.nazunamoe.microdustapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TCPActivity extends AppCompatActivity {
    EditText editTextAddress, editTextPort;
    Button connectBtn, saveBtn, IPSave, ExitBtn;
    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TCPActivity.this, SettingActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        setContentView(R.layout.tcp_activity);
        getSupportActionBar().setElevation(0);
        connectBtn = (Button) findViewById(R.id.buttonload);
        saveBtn = (Button) findViewById(R.id.buttonsave);
        ExitBtn = (Button) findViewById(R.id.exitbtn);
        editTextAddress = (EditText) findViewById(R.id.addressText);
        editTextPort = (EditText) findViewById(R.id.portText);
        if(preferences.getString("IP","0,0,0,0")=="0"){
            editor.putString("IP",editTextAddress.getText().toString());
        }

        if(preferences.getInt("port",0)==0){
            editor.putInt("port",Integer.parseInt(editTextPort.getText().toString()));
        }


        editTextAddress.addTextChangedListener(new TextWatcher() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
            SharedPreferences.Editor editor = preferences.edit();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                editor.putString("IP",s.toString());
                Log.d("test",preferences.getString("IP","0")+"");
                editor.commit();
                editor.apply();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editor.putString("IP",s.toString());
                Log.d("test",preferences.getString("IP","0")+"");
                editor.commit();
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
                editor.putString("IP",s.toString());
                Log.d("test",preferences.getString("IP","0")+"");
                editor.commit();
                editor.apply();
            }
        });

        editTextPort.addTextChangedListener(new TextWatcher() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
            SharedPreferences.Editor editor = preferences.edit();
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                try{
                    editor.putInt("port",Integer.parseInt(s.toString()));
                }catch(NumberFormatException e){
                    editor.putInt("port",0);
                    editTextPort.setText("0");
                }
                Log.d("test",preferences.getInt("port",0)+"");
                editor.commit();
                editor.apply();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    editor.putInt("port",Integer.parseInt(s.toString()));
                }catch(NumberFormatException e){
                    editor.putInt("port",0);
                    editTextPort.setText("0");
                }
                Log.d("test",preferences.getInt("port",0)+"");
                editor.commit();
                editor.apply();
            }

            @Override
            public void afterTextChanged(Editable s) {
                try{
                    editor.putInt("port",Integer.parseInt(s.toString()));
                }catch(NumberFormatException e){
                    editor.putInt("port",0);
                    editTextPort.setText("0");
                }
                Log.d("test",preferences.getInt("port",0)+"");
                editor.commit();
                editor.apply();
            }
        });

        connectBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
                Client myClientTask = new Client(preferences.getString("IP","0.0.0.0"), preferences.getInt("port",8888), "START"+","+preferences.getString("stationname","명서동")+","+preferences.getString("stationname2","명서동"),TCPActivity.this.getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.commit();
                myClientTask.execute();
            }
        });

        ExitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
                Client myClientTask = new Client(preferences.getString("IP","0.0.0.0"), preferences.getInt("port",8888), "exit",TCPActivity.this.getApplicationContext());
                myClientTask.execute();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
                Client myClientTask = new Client(preferences.getString("IP","0.0.0.0"), preferences.getInt("port",8888), "save,"+
                        preferences.getInt("pm10_color1-1_red",0)+","+preferences.getInt("pm10_color1-1_green",0)+","+preferences.getInt("pm10_color1-1_blue",0)+","+
                        preferences.getInt("pm10_color1-2_red",0)+","+preferences.getInt("pm10_color1-2_green",0)+","+preferences.getInt("pm10_color1-2_blue",0)+","+
                        preferences.getInt("pm10_color1-3_red",0)+","+preferences.getInt("pm10_color1-3_green",0)+","+preferences.getInt("pm10_color1-3_blue",0)+","+
                        preferences.getInt("pm10_color1-4_red",0)+","+preferences.getInt("pm10_color1-4_green",0)+","+preferences.getInt("pm10_color1-4_blue",0)+","+
                        preferences.getInt("pm10_color1-5_red",0)+","+preferences.getInt("pm10_color1-5_green",0)+","+preferences.getInt("pm10_color1-5_blue",0)+","+
                        preferences.getInt("pm10_color1-6_red",0)+","+preferences.getInt("pm10_color1-6_green",0)+","+preferences.getInt("pm10_color1-6_blue",0)+","+
                        preferences.getInt("pm10_color1-7_red",0)+","+preferences.getInt("pm10_color1-7_green",0)+","+preferences.getInt("pm10_color1-7_blue",0)+","+
                        preferences.getInt("pm10_color1-8_red",0)+","+preferences.getInt("pm10_color1-8_green",0)+","+preferences.getInt("pm10_color1-8_blue",0)+","+

                        preferences.getInt("pm10_color2-1_red",0)+","+preferences.getInt("pm10_color2-1_green",0)+","+preferences.getInt("pm10_color2-1_blue",0)+","+
                        preferences.getInt("pm10_color2-2_red",0)+","+preferences.getInt("pm10_color2-2_green",0)+","+preferences.getInt("pm10_color2-2_blue",0)+","+
                        preferences.getInt("pm10_color2-3_red",0)+","+preferences.getInt("pm10_color2-3_green",0)+","+preferences.getInt("pm10_color2-3_blue",0)+","+
                        preferences.getInt("pm10_color2-4_red",0)+","+preferences.getInt("pm10_color2-4_green",0)+","+preferences.getInt("pm10_color2-4_blue",0)+","+
                        preferences.getInt("pm10_color2-5_red",0)+","+preferences.getInt("pm10_color2-5_green",0)+","+preferences.getInt("pm10_color2-5_blue",0)+","+
                        preferences.getInt("pm10_color2-6_red",0)+","+preferences.getInt("pm10_color2-6_green",0)+","+preferences.getInt("pm10_color2-6_blue",0)+","+
                        preferences.getInt("pm10_color2-7_red",0)+","+preferences.getInt("pm10_color2-7_green",0)+","+preferences.getInt("pm10_color2-7_blue",0)+","+
                        preferences.getInt("pm10_color2-8_red",0)+","+preferences.getInt("pm10_color2-8_green",0)+","+preferences.getInt("pm10_color2-8_blue",0)+","+

                        preferences.getInt("pm25_color1-1_red",0)+","+preferences.getInt("pm25_color1-1_green",0)+","+preferences.getInt("pm25_color1-1_blue",0)+","+
                        preferences.getInt("pm25_color1-2_red",0)+","+preferences.getInt("pm25_color1-2_green",0)+","+preferences.getInt("pm25_color1-2_blue",0)+","+
                        preferences.getInt("pm25_color1-3_red",0)+","+preferences.getInt("pm25_color1-3_green",0)+","+preferences.getInt("pm25_color1-3_blue",0)+","+
                        preferences.getInt("pm25_color1-4_red",0)+","+preferences.getInt("pm25_color1-4_green",0)+","+preferences.getInt("pm25_color1-4_blue",0)+","+
                        preferences.getInt("pm25_color1-5_red",0)+","+preferences.getInt("pm25_color1-5_green",0)+","+preferences.getInt("pm25_color1-5_blue",0)+","+
                        preferences.getInt("pm25_color1-6_red",0)+","+preferences.getInt("pm25_color1-6_green",0)+","+preferences.getInt("pm25_color1-6_blue",0)+","+
                        preferences.getInt("pm25_color1-7_red",0)+","+preferences.getInt("pm25_color1-7_green",0)+","+preferences.getInt("pm25_color1-7_blue",0)+","+
                        preferences.getInt("pm25_color1-8_red",0)+","+preferences.getInt("pm25_color1-8_green",0)+","+preferences.getInt("pm25_color1-8_blue",0)+","+

                        preferences.getInt("pm25_color2-1_red",0)+","+preferences.getInt("pm25_color2-1_green",0)+","+preferences.getInt("pm25_color2-1_blue",0)+","+
                        preferences.getInt("pm25_color2-2_red",0)+","+preferences.getInt("pm25_color2-2_green",0)+","+preferences.getInt("pm25_color2-2_blue",0)+","+
                        preferences.getInt("pm25_color2-3_red",0)+","+preferences.getInt("pm25_color2-3_green",0)+","+preferences.getInt("pm25_color2-3_blue",0)+","+
                        preferences.getInt("pm25_color2-4_red",0)+","+preferences.getInt("pm25_color2-4_green",0)+","+preferences.getInt("pm25_color2-4_blue",0)+","+
                        preferences.getInt("pm25_color2-5_red",0)+","+preferences.getInt("pm25_color2-5_green",0)+","+preferences.getInt("pm25_color2-5_blue",0)+","+
                        preferences.getInt("pm25_color2-6_red",0)+","+preferences.getInt("pm25_color2-6_green",0)+","+preferences.getInt("pm25_color2-6_blue",0)+","+
                        preferences.getInt("pm25_color2-7_red",0)+","+preferences.getInt("pm25_color2-7_green",0)+","+preferences.getInt("pm25_color2-7_blue",0)+","+
                        preferences.getInt("pm25_color2-8_red",0)+","+preferences.getInt("pm25_color2-8_green",0)+","+preferences.getInt("pm25_color2-8_blue",0)+","+

                        preferences.getInt("normal_color1_red",0)+","+preferences.getInt("normal_color1_green",0)+","+preferences.getInt("normal_color1_blue",0)+","+
                        preferences.getInt("normal_color2_red",0)+","+preferences.getInt("normal_color2_green",0)+","+preferences.getInt("normal_color2_blue",0)+","+
                        preferences.getInt("normal_color3_red",0)+","+preferences.getInt("normal_color3_green",0)+","+preferences.getInt("normal_color3_blue",0),
                        TCPActivity.this.getApplicationContext()
                );
                myClientTask.execute();
            }
        });

    }
}