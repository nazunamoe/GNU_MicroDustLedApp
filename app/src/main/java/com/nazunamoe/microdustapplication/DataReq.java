package com.nazunamoe.microdustapplication;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.nazunamoe.microdustapplication.Location.GeoPoint;
import com.nazunamoe.microdustapplication.Location.GeoTrans;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import static android.content.ContentValues.TAG;

public class DataReq {

    int PM10;
    int PM25;
    boolean end;
    String Time;
    String stationname;
    String stationname2;
    String stationaddr;

    double longitude;
    double latitude;

    public void RequestStart(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(Splashscreen.getAppContext());
        SharedPreferences.Editor editor = preferences.edit();
        stationname="";
        BufferedReader br = null;
        Log.d("current", latitude + "," + longitude);
        end = false;
        GeoPoint in_pt = new GeoPoint(longitude,latitude);
        Log.d("test","geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());

        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
        Log.d("test","tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());

        // 이곳에서 현재 위경도를 GeoPoint를 이용해서 tmX, tmY좌표로 변환 후 URL요청을 실시한다.
        try {
            String urlstr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?" +
                    "tmX=" + tm_pt.getX() +
                    "&tmY=" +tm_pt.getY() +
                    "&pageNo=1&numOfRows=10&" +
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

            try {
                if (result != null) {
                    DocumentBuilderFactory factory = DocumentBuilderFactory
                            .newInstance();
                    DocumentBuilder documentBuilder = factory.newDocumentBuilder();
                    InputStream is = new ByteArrayInputStream(result.getBytes());
                    Document doc = documentBuilder.parse(is);
                    Element element = doc.getDocumentElement();
                    NodeList Name = element.getElementsByTagName("stationName");
                    NodeList Addr = element.getElementsByTagName("addr");
                    NodeList Kilo = element.getElementsByTagName("tm");
                    // 임시값 저장
                    end = true;
                    int n = 2; // 제일 가까운 측정소만 알면 됨.
                    for (int i=0; i<n; i++){
                        if(i==0){
                            Node NameItem = Name.item(i);
                            Node NameText = NameItem.getFirstChild();
                            String NameValue = NameText.getNodeValue();
                            Log.d("Test","addr"+NameValue);
                            stationname = NameValue;
                            editor.putString("stationname",stationname);
                            // 측정소 이름

                            Node AddrItem = Addr.item(i);
                            Node AddrText = AddrItem.getFirstChild();
                            String AddrValue = AddrText.getNodeValue();
                            Log.d("Test",AddrValue);
                            stationaddr = AddrValue;
                            // 측정소 주소

                            Node KiloItem = Kilo.item(i);
                            Node KiloText = KiloItem.getFirstChild();
                            String KiloValue = KiloText.getNodeValue();
                            Log.d("Test",KiloValue);
                            // 측정소 거리
                            // 디버그용 확인 코드
                        }else  if(i==1){
                            Node NameItem = Name.item(i);
                            Node NameText = NameItem.getFirstChild();
                            String NameValue = NameText.getNodeValue();
                            Log.d("Test","addr"+NameValue);
                            stationname2 = NameValue;
                            editor.putString("stationname2",stationname2);
                            editor.commit();
                            editor.apply();
                        }
                    }
                    try {
                        String urlstr2 = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?" +
                                "stationName=" + preferences.getString("stationname","명서동") +
                                "&dataTerm=month" +
                                "&pageNo=1" +
                                "&numOfRows=1" +
                                "&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D" +
                                "&ver=1.3";
                        String urlstr3 = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?" +
                                "stationName=" + preferences.getString("stationname2","반송로") +
                                "&dataTerm=month" +
                                "&pageNo=1" +
                                "&numOfRows=1" +
                                "&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D" +
                                "&ver=1.3";
                        URL url2 = new URL(urlstr2);
                        URL url3 = new URL(urlstr3);
                        HttpURLConnection urlconnection2 = (HttpURLConnection) url2.openConnection();
                        urlconnection2.setRequestMethod("GET");
                        br = new BufferedReader(new InputStreamReader(urlconnection2.getInputStream(), "UTF-8"));
                        String result2 = "";
                        String line2;
                        while((line2=br.readLine())!=null){
                            result2 = result2 + line2 + "\n";
                        }
                        try{
                            if(result2 != null){
                                // 처음 측정소 값으로 pm10,pm2.5값을 받아오는 설정 부분
                                DocumentBuilderFactory factory2 = DocumentBuilderFactory
                                        .newInstance();
                                DocumentBuilder documentBuilder2 = factory2.newDocumentBuilder();
                                InputStream is2 = new ByteArrayInputStream(result2.getBytes());
                                Document doc2 = documentBuilder2.parse(is2);
                                Element element2 = doc2.getDocumentElement();
                                NodeList PM10Node = element2.getElementsByTagName("pm10Value");
                                NodeList PM25Node = element2.getElementsByTagName("pm25Value");
                                NodeList dataTimeNode = element2.getElementsByTagName("dataTime");
                                // 필요한 노드리스트 선언

                                String temppm10;
                                String temppm25;
                                // 임시값 선언

                                // PM10 데이터 수집
                                Node PM10Item = PM10Node.item(0);
                                Node PM10Text = PM10Item.getFirstChild();
                                String PM10Value="";
                                temppm10= PM10Text.getNodeValue();
                                if(temppm10.equals("-")){
                                    PM10Value = "err";
                                }

                                // PM2.5 데이터 수집
                                Node PM25Item = PM25Node.item(0);
                                Node PM25Text = PM25Item.getFirstChild();
                                String PM25Value="";
                                temppm25= PM25Text.getNodeValue();
                                if(temppm25.equals("-")){
                                    PM25Value = "err";
                                }

                                if(PM10Value.equals("err") || PM25Value.equals("err")){
                                    HttpURLConnection urlconnection3 = (HttpURLConnection) url3.openConnection();
                                    urlconnection3.setRequestMethod("GET");
                                    br = new BufferedReader(new InputStreamReader(urlconnection3.getInputStream(), "UTF-8"));
                                    String result3 = "";
                                    String line3;
                                    while((line3=br.readLine())!=null){
                                        result3 = result3 + line3 + "\n";
                                    }
                                    try{
                                        // 두번째 측정소 값으로 pm10,pm2.5값을 받아오는 설정 부분
                                        DocumentBuilderFactory factory3 = DocumentBuilderFactory
                                                .newInstance();
                                        DocumentBuilder documentBuilder3 = factory3.newDocumentBuilder();
                                        InputStream is3 = new ByteArrayInputStream(result3.getBytes());
                                        Document doc3 = documentBuilder3.parse(is3);
                                        Element element3 = doc3.getDocumentElement();
                                        NodeList PM10Node2 = element3.getElementsByTagName("pm10Value");
                                        NodeList PM25Node2 = element3.getElementsByTagName("pm25Value");
                                        // 필요한 노드리스트 선언

                                        // PM10 데이터 수집
                                        Node PM10Item2 = PM10Node2.item(0);
                                        Node PM10Text2 = PM10Item2.getFirstChild();
                                        if(PM10Value.equals("err")){
                                            temppm10= PM10Text2.getNodeValue();
                                        }

                                        // PM2.5 데이터 수집
                                        Node PM25Item2 = PM25Node2.item(0);
                                        Node PM25Text2 = PM25Item2.getFirstChild();
                                        if(PM25Value.equals("err")){
                                            temppm25= PM25Text2.getNodeValue();
                                        }

                                    }catch(Exception e){
                                        Log.d(TAG, "err"+e.toString());
                                    }
                                }
                                PM10Value = temppm10;
                                PM10 = Integer.parseInt(PM10Value);
                                PM25Value = temppm25;
                                PM25 = Integer.parseInt(PM25Value);

                                Node dataTimeItem = dataTimeNode.item(0);
                                Node dataTimeText = dataTimeItem.getFirstChild();
                                String dataTimeValue = dataTimeText.getNodeValue();
                                Log.d("Test","time"+dataTimeValue);
                                Time = dataTimeValue;
                                // 시간을 받아오는 부분

                                editor.putString("pm10",temppm10);
                                editor.putString("pm25",temppm25);
                                editor.putString("time",dataTimeValue);
                                editor.commit();
                                editor.apply();
                                }
                        }catch(Exception e){
                            Log.d(TAG, "err"+e.toString());
                        }
                    }catch (Exception e){
                        Log.d(TAG, "err"+e.toString());
                    }
                }
            }catch(Exception e){
                Log.d(TAG, "err"+e.toString());
            }
        }catch(Exception e){
            Log.d(TAG, "err"+e.toString());
        }
    }
}
