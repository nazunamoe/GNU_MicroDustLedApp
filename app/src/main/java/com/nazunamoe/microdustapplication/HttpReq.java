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

    public void RequestStart(){
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

            //Log.d(TAG, result);
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
                    PM10 = 20;
                    PM25 = 30;
                    end = true;
                    int n = 1; // 제일 가까운 측정소만 알면 됨.
                    for (int i=0; i<n; i++){
                        Node NameItem = Name.item(i);
                        Node NameText = NameItem.getFirstChild();
                        String NameValue = NameText.getNodeValue();
                        Log.d("Test",NameValue);

                        Node AddrItem = Addr.item(i);
                        Node AddrText = AddrItem.getFirstChild();
                        String AddrValue = AddrText.getNodeValue();
                        Log.d("Test",AddrValue);

                        Node KiloItem = Kilo.item(i);
                        Node KiloText = KiloItem.getFirstChild();
                        String KiloValue = KiloText.getNodeValue();
                        Log.d("Test",KiloValue);
                        // 디버그용 확인 코드
                    }
                }



            }catch(Exception e){
                Log.d(TAG, "ERROR");
            }

        }catch(Exception e){
            Log.d(TAG, "에러다 이기야"+e.toString());
        }


    }
}
