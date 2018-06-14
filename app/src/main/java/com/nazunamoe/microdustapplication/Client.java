package com.nazunamoe.microdustapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Created by nazunamoe on 2018-05-30.
 */

public class Client extends AsyncTask<Void, Void, Void> {
    String dstAddress;
    int dstPort;
    String response = "";
    String myMessage = "";
    Context context;
    long Start;
    //constructor
    Client(String addr, int port, String message, Context context) {
        Start = System.currentTimeMillis();
        dstAddress = addr;
        dstPort = port;
        myMessage = message;
        this.context = context;
    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        myMessage = myMessage.toString();
        try {
            socket = new Socket(dstAddress, dstPort);
            //송신
            OutputStream out = socket.getOutputStream();
            out.write(myMessage.getBytes());

            //수신
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];
            int bytesRead;
            InputStream inputStream = socket.getInputStream();
            /*
             * notice:
             * inputStream.read() will block if no data return
             */
            while ((bytesRead = inputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
                Log.d("test!!!",""+response);
            }

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        // pm10 유저설정 1 - pm10 유저설정 2 - pm25 유저설정 1 - pm25 유저설정 2 - 일반 유저설정 1~3 순서, RGB,

        if(response!=null){

            StringTokenizer st = new StringTokenizer(response,",");
            String temp = st.nextToken();
            switch(temp){
                // 받아온 명령어의 첫 부분으로 명령어의 종류를 구분한다.
                case "LED":{
                    int a = 0;
                    int b = 0;
                    int c = 0;
                    String tempred = "";
                    String tempblue = "";
                    String tempgreen = "";
                    while(st.hasMoreTokens()){
                        a = a +1;
                        if(a==0){
                            continue;
                        }else{
                            Log.d("d",a+"");
                            if(a<25){
                                b=0;
                            }else if(a<49){
                                b=1;
                            }else if(a<73){
                                b=2;
                            }else if(a<97){
                                b=3;
                            }else if(a==106) {
                                Toast.makeText(context,R.string.error_on_gps,Toast.LENGTH_LONG).show();
                            }else{
                                b=4;
                            }

                            if(a%3 ==0){
                                if(c==8){
                                    c=0;
                                }
                                tempblue = st.nextToken();
                                Log.d("test",b+","+c);
                                switch(b){
                                    case 0:{
                                        editor.putInt("pm10_color1-"+c+"_red",Integer.valueOf(tempred));
                                        editor.putInt("pm10_color1-"+c+"_blue",Integer.valueOf(tempblue));
                                        editor.putInt("pm10_color1-"+c+"_green",Integer.valueOf(tempgreen));
                                        break;
                                    }
                                    case 1:{
                                        editor.putInt("pm10_color2-"+c+"_red",Integer.valueOf(tempred));
                                        editor.putInt("pm10_color2-"+c+"_blue",Integer.valueOf(tempblue));
                                        editor.putInt("pm10_color2-"+c+"_green",Integer.valueOf(tempgreen));
                                        break;
                                    }
                                    case 2:{
                                        editor.putInt("pm25_color1-"+c+"_red",Integer.valueOf(tempred));
                                        editor.putInt("pm25_color1-"+c+"_blue",Integer.valueOf(tempblue));
                                        editor.putInt("pm25_color1-"+c+"_green",Integer.valueOf(tempgreen));
                                        break;
                                    }
                                    case 3:{
                                        editor.putInt("pm25_color2-"+c+"_red",Integer.valueOf(tempred));
                                        editor.putInt("pm25_color2-"+c+"_blue",Integer.valueOf(tempblue));
                                        editor.putInt("pm25_color2-"+c+"_green",Integer.valueOf(tempgreen));
                                        break;
                                    }
                                    case 4:{
                                        editor.putInt("normal_color"+c+"_red",Integer.valueOf(tempred));
                                        editor.putInt("normal_color"+c+"_blue",Integer.valueOf(tempblue));
                                        editor.putInt("normal_color"+c+"_green",Integer.valueOf(tempgreen));
                                        break;
                                    }

                                }
                                c=c+1;
                                editor.commit();
                                editor.apply();
                            }else if(a%3 == 1){
                                tempred = st.nextToken();
                                //pm10-1 : 2~9
                                //pm10-2 : 10~17
                                //pm25-1 : 18~25
                                //pm25-2 : 26~33
                                //normal : 34~36
                            }else if(a%3 == 2){
                                tempgreen = st.nextToken();
                            }
                        }
                    }
                    break;
                }
            }
            super.onPostExecute(result);
            long end = System.currentTimeMillis();
        }
    }
}