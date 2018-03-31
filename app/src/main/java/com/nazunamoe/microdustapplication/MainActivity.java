package com.nazunamoe.microdustapplication;

import android.app.Activity;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    //MyActivity 시작
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyData> myDataset;


    public MyData PM10data;
    public MyData PM25data;
    public MyData Location;
    public int PM10;
    public int PM25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myDataset.remove(0);
                myDataset = new ArrayList<>();
                mAdapter = new MyAdapter(myDataset);
                mRecyclerView.setAdapter(mAdapter);
                updateData();
                new Thread(){
                    public void run(){
                        HttpReq req = new HttpReq();
                        req.RequestStart();
                        InitializeData();
                        addData();
                    }
                }.start();
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
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
        addData();
    }

    public void InitializeData(){
        // 데이터 초기화에 이용
        PM10data=new MyData("미세먼지 (PM10)","현재 수치는",Integer.toString(PM10),"양호한 수치입니다","업데이트 일시",R.mipmap.yuuki);
        PM25data=new MyData("초미세먼지 (PM2.5)","현재 수치는",Integer.toString(PM25),"양호한 수치입니다","업데이트 일시",R.mipmap.asuka);
        Location=new MyData("측정소 위치","측정소 위치는","진주시 상대동","경남 진주시 동진로 279","업데이트 일시",R.mipmap.chie);
    }

    public void updateData(){
        // 이곳에 측정 데이터를 받아오는 메서드 추가
        PM10+=5;
        PM25-=5;
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
        holder.madditionalView.setText(mDataset.get(position).additional);
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