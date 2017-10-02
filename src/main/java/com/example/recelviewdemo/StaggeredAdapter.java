package com.example.recelviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hanyh on 2017/9/24.
 */

public class StaggeredAdapter extends RecyclerView.Adapter<MyStaggeredViewHolder> {



    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mLayoutInflater;
    private List<Integer> mHeights;

    public StaggeredAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mHeights=new ArrayList<>();
        for (int i=0;i<mDatas.size();i++){
            mHeights.add((int) (100+Math.random()*300));
        }
        this.mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public MyStaggeredViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view=mLayoutInflater.inflate(R.layout.item_simple_layout,viewGroup,false);
        MyStaggeredViewHolder viewHolder=new MyStaggeredViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyStaggeredViewHolder myViewHolder, int position) {

        ViewGroup.LayoutParams params= (ViewGroup.LayoutParams) myViewHolder.itemView.getLayoutParams();
        params.height=mHeights.get(position);
        myViewHolder.itemView.setLayoutParams(params);
        myViewHolder.tv.setText(mDatas.get(position));

    }

    @Override
    public int getItemCount() {

        return mDatas!=null?mDatas.size():0;
    }
}

class MyStaggeredViewHolder extends RecyclerView.ViewHolder{

    TextView tv;
    public MyStaggeredViewHolder(View itemView) {
        super(itemView);
        tv=itemView.findViewById(R.id.id_item);
    }
}
