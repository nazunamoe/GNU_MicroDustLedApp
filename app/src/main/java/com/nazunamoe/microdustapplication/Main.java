package com.nazunamoe.microdustapplication;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Main extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    //MyActivity 시작
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;

    MyData PM10data;
    MyData PM25data;
    MyData Location;
    int PM10Status;
    int PM25Status;
    String PM10String;
    String PM25String;
    int PM10value;
    int PM25value;
    String stationname;
    String stationaddr;
    String Time;

    int PM10image = R.drawable.normal;
    int PM25image = R.drawable.normal;

    DataReq req = new DataReq();

    public final LocationListener mLocationListener = new LocationListener() {
        double longitude;
        double latitude;
        public void onLocationChanged(Location location) {
            //여기서 위치값이 갱신되면 이벤트가 발생한다.
            //값은 Location 형태로 리턴되며 좌표 출력 방법은 다음과 같다.

            Log.d("test", "onLocationChanged, location:" + location);
            longitude = location.getLongitude(); //경도
            latitude = location.getLatitude();   //위도
            double altitude = location.getAltitude();   //고도
            float accuracy = location.getAccuracy();    //정확도
            String provider = location.getProvider();   //위치제공자
            //Gps 위치제공자에 의한 위치변화. 오차범위가 좁다.
            //Network 위치제공자에 의한 위치변화
            //Network 위치는 Gps에 비해 정확도가 많이 떨어진다.
            req.longitude=longitude;
            req.latitude=latitude;
            myDataset = new ArrayList<>();
            mAdapter = new MyAdapter(myDataset);
            mRecyclerView.setAdapter(mAdapter);
            updateData();
            addData();
        }
        public void onProviderDisabled(String provider) {
            // Disabled시
            Log.d("test", "onProviderDisabled, provider:" + provider);
            req.longitude=longitude;
            req.latitude=latitude;
        }

        public void onProviderEnabled(String provider) {
            // Enabled시
            Log.d("test", "onProviderEnabled, provider:" + provider);
            req.longitude=longitude;
            req.latitude=latitude;
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
            // 변경시
            Log.d("test", "onStatusChanged, provider:" + provider + ", status:" + status + " ,Bundle:" + extras);
            req.longitude=longitude;
            req.latitude=latitude;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // LocationManager 객체를 얻어온다
        final LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);
        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, // 등록할 위치제공자
                100, // 통지사이의 최소 시간간격 (miliSecond)
                1, // 통지사이의 최소 변경거리 (m)
                mLocationListener);

        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.my_recycler_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout mSwipeRefreshLayout = findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myDataset = new ArrayList<>();
                mAdapter = new MyAdapter(myDataset);
                mRecyclerView.setAdapter(mAdapter);
                updateData();
                addData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        myDataset = new ArrayList<>();
        mAdapter = new MyAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        InitializeData();
        updateData();
        addData();
    }

    public void InitializeData(){
        // 데이터 초기화에 이용
        PM10data=new MyData(getString(R.string.PM10),getString(R.string.pre),Integer.toString(PM10value)+"㎍/㎥",PM10String,getString(R.string.Time),PM10image);
        PM25data=new MyData(getString(R.string.PM25),getString(R.string.pre),Integer.toString(PM25value)+"㎍/㎥",PM25String,getString(R.string.Time),PM25image);
        Location=new MyData(getString(R.string.Addr),getString(R.string.AddrPre),req.stationname,getString(R.string.update_time)+Time,getString(R.string.Time),R.drawable.station);
    }

    public void updateData(){
        // 이곳에 측정 데이터를 받아오는 메서드 추가
        Thread update = new Thread(){
            public void run(){
                if(req.latitude==0){

                }else{
                req.RequestStart();
                PM10value = req.PM10;
                PM25value = req.PM25;
                stationname = req.stationname;
                stationaddr = req.stationaddr;
                Time = req.Time;
                settingData(PM10value,0);
                settingData(PM25value,1);
                InitializeData();}
            }
        };
        update.start();
    }


    public void updateData2(){
        // 이곳에 측정 데이터를 받아오는 메서드 추가
        Thread update = new Thread(){
            public void run(){
                if(req.latitude==0){

                }else{
                    req.RequestStart();
                    PM10value = req.PM10;
                    PM25value = req.PM25;
                    stationname = req.stationname;
                    stationaddr = req.stationaddr;
                    Time = req.Time;
                    settingData(PM10value,0);
                    settingData(PM25value,1); }
            }
        };
        update.start();
    }
    /*
    미세미세 8단계 적용

    PM10
    최고 : 0~15
    좋음 : 16~30
    양호 : 31-40
    보통 : 41~50
    나쁨 : 51~75
    상당히 나쁨 : 76~100
    매우 나쁨 : 101-150
    최악 : 151~

    PM2.5
    최고 : 0~8
    좋음 : 9~15
    양호 : 16~20
    보통 : 21~25
    나쁨 : 26~37
    상당히 나쁨 : 38~50
    매우 나쁨 : 51~75
    최악 : 76~

    String.xml
    best - verygood - good - normal - poor - fairlybad - verybad - worst
    0 1 2 3 4 5 6 7
     */
    private void settingData(int value, int PM){
        switch(PM){ // 0 : PM10 , 1 : PM2.5
            case 0:{
                if(value <= 15){
                    PM10Status = 0;
                    PM10image = R.drawable.best;
                }else if(value <= 30){
                    PM10Status = 1;
                    PM10image = R.drawable.verygood;
                }else if(value <= 40){
                    PM10Status = 2;
                    PM10image = R.drawable.good;
                }else if(value <= 50){
                    PM10Status = 3;
                    PM10image = R.drawable.normal;
                }else if(value <= 75){
                    PM10Status = 4;
                    PM10image = R.drawable.poor;
                }else if(value <= 100){
                    PM10Status = 5;
                    PM10image = R.drawable.fairlybad;
                }else if(value <= 150){
                    PM10Status = 6;
                    PM10image = R.drawable.verybad;
                }else{
                    PM10Status = 7;
                    PM10image = R.drawable.worst;
                }
                break;
            }
            case 1:{
                if(value <= 8){
                    PM25Status = 0;
                    PM25image = R.drawable.best;
                }else if(value <= 15){
                    PM25Status = 1;
                    PM25image = R.drawable.verygood;
                }else if(value <= 20){
                    PM25Status = 2;
                    PM25image = R.drawable.good;
                }else if(value <= 25){
                    PM25Status = 3;
                    PM25image = R.drawable.normal;
                }else if(value <= 37){
                    PM25Status = 4;
                    PM25image = R.drawable.poor;
                }else if(value <= 50){
                    PM25Status = 5;
                    PM25image = R.drawable.fairlybad;
                }else if(value <= 75){
                    PM25Status = 6;
                    PM25image = R.drawable.verybad;
                }else{
                    PM25Status = 7;
                    PM25image = R.drawable.worst;
                }
                break;
            }
        }
        setGrade(PM10Status,0);
        setGrade(PM25Status,1);
    }

    private void setGrade(int grade, int PM){
        switch(grade){
            case 0:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_best);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_best);
                        break;
                    }
                }
                break;
            }
            case 1:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_verygood);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_verygood);
                        break;
                    }
                }
                break;
            }
            case 2:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_good);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_good);
                        break;
                    }
                }
                break;
            }
            case 3:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_normal);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_normal);
                        break;
                    }
                }
                break;
            }
            case 4:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_poor);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_poor);
                        break;
                    }
                }
                break;
            }
            case 5:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_fairlybad);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_fairlybad);
                        break;
                    }
                }
                break;
            }
            case 6:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_verybad);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_verybad);
                        break;
                    }
                }
                break;
            }
            case 7:{
                switch(PM){
                    case 0:{
                        PM10String = getString(R.string.post_worst);
                        break;
                    }
                    case 1:{
                        PM25String = getString(R.string.post_worst);
                        break;
                    }
                }
                break;
            }
        }
    }

    public void addData(){
        // 데이터 초기화에 이용
        myDataset.add(PM10data);
        myDataset.add(PM25data);
        myDataset.add(Location);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent intent;
        if (id == R.id.normal_led_settings) {
            intent = new Intent(Main.this, SettingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_email) {

        } else if (id == R.id.nav_about) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private ArrayList<MyData> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageView;
        public TextView mTextView;
        public TextView mpreView;
        public TextView mpresentView;
        public TextView mpostView;
        public TextView madditionalView;

        public ViewHolder(View view) {
            super(view);
            mImageView = (ImageView)view.findViewById(R.id.image);
            mTextView = (TextView)view.findViewById(R.id.textview);
            mpreView = (TextView)view.findViewById(R.id.pre);
            mpresentView = (TextView)view.findViewById(R.id.present);
            mpostView = (TextView)view.findViewById(R.id.post);
            madditionalView = (TextView)view.findViewById(R.id.additional);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(ArrayList<MyData> myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_view, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mTextView.setText(mDataset.get(position).text);
        holder.mpreView.setText(mDataset.get(position).pre);
        holder.mpresentView.setText(mDataset.get(position).present);
        holder.mpostView.setText(mDataset.get(position).post);
       // holder.madditionalView.setText(mDataset.get(position).additional);
        holder.mImageView.setImageResource(mDataset.get(position).img);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}

class MyData{
    public String text;
    public String pre;
    public String present;
    public String post;
    public String additional;
    public int img;
    public MyData(String text, String pre, String present, String post, String additional, int img){
        this.text = text;
        this.pre = pre;
        this.present = present;
        this.post = post;
        this.additional = additional;
        this.img = img;
    }
}