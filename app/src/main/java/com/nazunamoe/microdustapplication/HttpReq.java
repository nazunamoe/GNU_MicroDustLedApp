package com.nazunamoe.microdustapplication;

import android.util.Log;

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

public class HttpReq {

    int PM10;
    int PM25;
    boolean end;
    String stationname;
    String stationaddr;
    public void RequestStart(){
        stationname="";
        BufferedReader br = null;
        Log.d(TAG, "접속");
        end = false;
        GeoPoint in_pt = new GeoPoint(128.639329, 35.262084);
        Log.d("test","geo in : xGeo="  + in_pt.getX() + ", yGeo=" + in_pt.getY());

        GeoPoint tm_pt = GeoTrans.convert(GeoTrans.GEO, GeoTrans.TM, in_pt);
        Log.d("test","tm : xTM=" + tm_pt.getX() + ", yTM=" + tm_pt.getY());

        // 이곳에서 현재 위경도를 GeoPoint를 이용해서 tmX, tmY좌표로 변환 후 URL요청을 실시한다.
        try {
            String urlstr = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList?" +
                    "tmX=349107.0004417976" +
                    "&tmY=197097.48881956766" +
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
                    int n = 1; // 제일 가까운 측정소만 알면 됨.
                    for (int i=0; i<n; i++){
                        Node NameItem = Name.item(i);
                        Node NameText = NameItem.getFirstChild();
                        String NameValue = NameText.getNodeValue();
                        Log.d("Test","addr"+NameValue);
                        stationname = NameValue;
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
                    }
                    try {
                        String urlstr2 = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty?" +
                                "stationName=" + stationname +
                                "&dataTerm=month" +
                                "&pageNo=1" +
                                "&numOfRows=1" +
                                "&ServiceKey=cHGmDMDNmdqGMEoyXUfL8f%2BT6%2FUilLRNvHCotJvVNodlMct%2BhWD1uG%2FyOorJ7wnYRPA5myrolanrcoy1GE2%2BWg%3D%3D" +
                                "&ver=1.3";
                        URL url2 = new URL(urlstr2);
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
                                DocumentBuilderFactory factory2 = DocumentBuilderFactory
                                        .newInstance();
                                DocumentBuilder documentBuilder2 = factory2.newDocumentBuilder();
                                InputStream is2 = new ByteArrayInputStream(result2.getBytes());
                                Document doc2 = documentBuilder2.parse(is2);
                                Element element2 = doc2.getDocumentElement();
                                NodeList PM10Node = element2.getElementsByTagName("pm10Value");
                                NodeList PM25Node = element2.getElementsByTagName("pm25Value");
                                int n2 = 1; // 제일 가까운 측정소만 알면 됨.
                                for (int i=0; i<n2; i++){
                                    Node PM10Item = PM10Node.item(i);
                                    Node PM10Text = PM10Item.getFirstChild();
                                    String PM10Value = PM10Text.getNodeValue();
                                    Log.d("Test",PM10Value);
                                    PM10 = Integer.parseInt(PM10Value);

                                    Node PM25Item = PM25Node.item(i);
                                    Node PM25Text = PM25Item.getFirstChild();
                                    String PM25Value = PM25Text.getNodeValue();
                                    Log.d("Test",PM25Value);
                                    PM25 = Integer.parseInt(PM25Value);
                                }
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
