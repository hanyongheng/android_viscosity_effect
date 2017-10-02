package com.example.recelviewdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private SimpleAdapter mSimpleAdapter;
    private static final int LINEARLAYOUT=1;
    private static final int GIRDVIEWLAYOUT=2;
    private static final int STAGGERED_GIRDVIEW_VIEWLAYOUT=3;
    private Button mListViewBtn,mGirdViewBtn,mStaggeredGirdView,mStaggeredActivity,mAddBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
        initListeners();
    }

    private void initListeners() {
        mListViewBtn.setOnClickListener(this);
        mGirdViewBtn.setOnClickListener(this);
        mStaggeredGirdView.setOnClickListener(this);
        mStaggeredActivity.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);
    }

    private RecyclerView.LayoutManager initLayoutManager(int type) {

        RecyclerView.LayoutManager manager=null;
        switch (type){

            case 1:
                manager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

                break;
            case 2:
                manager=new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
                break;
            case 3:
                manager=new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.HORIZONTAL);
                break;
        }
        return manager;
    }

    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.id_recyclerView);
        mListViewBtn=(Button)findViewById(R.id.listViewBtn);
        mGirdViewBtn=(Button)findViewById(R.id.girdViewBtn);
        mStaggeredGirdView=(Button)findViewById(R.id.staggeredGirdView);
        mStaggeredActivity=(Button)findViewById(R.id.staggeredActivity);
        mAddBtn=(Button)findViewById(R.id.addBtn);
        mSimpleAdapter=new SimpleAdapter(this,mDatas);
        mSimpleAdapter.setmOnItemClickListener(new SimpleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this,"click`s position is "+position,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Toast.makeText(MainActivity.this,"long click`s position is "+position,Toast.LENGTH_LONG).show();
                mSimpleAdapter.delItem(position);
            }
        });
        mRecyclerView.setAdapter(mSimpleAdapter);
        mRecyclerView.setLayoutManager(initLayoutManager(LINEARLAYOUT));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
    }

    private void initDatas() {
            mDatas=new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    @Override
    public void onClick(View view) {

        int id=view.getId();

        switch (id){

            case R.id.listViewBtn:
                mRecyclerView.setLayoutManager(initLayoutManager(LINEARLAYOUT));
                break;

            case R.id.girdViewBtn:
                mRecyclerView.setLayoutManager(initLayoutManager(GIRDVIEWLAYOUT));
                break;

            case R.id.staggeredGirdView:
                mRecyclerView.setLayoutManager(initLayoutManager(STAGGERED_GIRDVIEW_VIEWLAYOUT));
                break;
            case R.id.staggeredActivity:
//                startActivity(new Intent(this,StaggeredActivity.class));
                startActivity(new Intent(this,ViscosityViewActivity.class));
                break;
            case R.id.addBtn:
                mSimpleAdapter.addItem(1);
                break;
        }
    }
}
