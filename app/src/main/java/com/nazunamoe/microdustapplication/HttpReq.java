package com.nazunamoe.microdustapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

public class HttpReq {
    public void RequestStart(){
        BufferedReader br = null;
        Log.d(TAG, "접속");
        try {
            String urlstr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?" +
                    "tmX=349166.4075" +
                    "&tmY=297482.7886&" +
                    "pageNo=1&numOfRows=10&" +
                    "ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D";
            URL url = new URL(urlstr);
            HttpURLConnection urlconnection = (HttpURLConnection) url.openConnection();
            urlconnection.setRequestMethod("GET");
            br = new BufferedReader(new InputStreamReader(urlconnection.getInputStream(), "UTF-8"));
            String result = "";
            String line;
            while((line=br.readLine())!=null){
                result = result + line + "\n";
            }
            Log.d(TAG, result);
        }catch(Exception e){
            Log.d(TAG, "에러다 이기야"+e.toString());
        }
    }
}
