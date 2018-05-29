package com.nazunamoe.microdustapplication;

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


public class TCPActivity extends AppCompatActivity {
    TextView recieveText;
    EditText editTextAddress, editTextPort, messageText;
    Button connectBtn, clearBtn;

    Socket socket = null;
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
                MyClientTask myClientTask = new MyClientTask(editTextAddress.getText().toString(), Integer.parseInt(editTextPort.getText().toString()), "SEX!!!");
                myClientTask.execute();
                //messageText.setText("");
            }
        });

    }

    //
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
                response = "서버의 응답: " + response;

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

            if(recieveText == null){
                Toast.makeText(TCPActivity.this, R.string.error_on_tcp, Toast.LENGTH_SHORT).show();
            }else{
                // 파이로부터 받아온 정보를 처리하는 부분을 이쪽에 추가한다.
            }

            super.onPostExecute(result);
        }
    }

}