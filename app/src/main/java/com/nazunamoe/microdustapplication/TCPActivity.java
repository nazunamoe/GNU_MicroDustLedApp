package com.nazunamoe.microdustapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;


public class TCPActivity extends AppCompatActivity {
    TextView recieveText;
    EditText editTextAddress, editTextPort, messageText;
    Button connectBtn, clearBtn;
    Database database = Database.getInstance();
    Socket socket = null;

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(TCPActivity.this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tcp_activity);
        //앱 기본 스타일 설정
        getSupportActionBar().setElevation(0);

        connectBtn = (Button) findViewById(R.id.buttonConnect);
        editTextAddress = (EditText) findViewById(R.id.addressText);
        editTextPort = (EditText) findViewById(R.id.portText);

        //connect 버튼 클릭
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), "START"+database.stationname);
                myClientTask.execute();
            }
        });
    }

    public class MyClientTask extends AsyncTask<Void, Void, Void> {
        String dstAddress;
        int dstPort;
        String response = "";
        String myMessage = "";

        //constructor
        MyClientTask(String addr, int port, String message){
            dstAddress = addr;
            dstPort = port;
            myMessage = message;
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
                }

            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            }finally{
                if(socket != null){
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
            if(response == null){
                Toast.makeText(TCPActivity.this, R.string.error_on_tcp, Toast.LENGTH_SHORT).show();
            }else{
                Log.d("test",""+response);
                // pm10 유저설정 1 - pm10 유저설정 2 - pm25 유저설정 1 - pm25 유저설정 2 - 일반 유저설정 1~3 순서, RGB,
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
                                }else{
                                    b=4;
                                }

                                if(a%3 ==0){
                                    if(c==8){
                                        c=0;
                                    }
                                    tempblue = st.nextToken();
                                    database.setpreset(b,c,new LEDColor(Integer.valueOf(tempred),Integer.valueOf(tempblue),Integer.valueOf(tempgreen)));
                                    c=c+1;

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
            }
            super.onPostExecute(result);
            Client myClientTask = new Client(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), "exit");
            myClientTask.execute();
        }
    }
}