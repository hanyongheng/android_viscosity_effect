package com.example.recelviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class StaggeredActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private List<String> mDatas;
    private StaggeredAdapter mSimpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDatas();
        initView();
    }


    private void initView() {
        mRecyclerView= (RecyclerView) findViewById(R.id.id_recyclerView);
        mSimpleAdapter=new StaggeredAdapter(this,mDatas);
        mRecyclerView.setAdapter(mSimpleAdapter);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(5,StaggeredGridLayoutManager.VERTICAL));
    }

    private void initDatas() {
            mDatas=new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }
}
