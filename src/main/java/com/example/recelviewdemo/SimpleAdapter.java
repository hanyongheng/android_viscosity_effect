package com.example.recelviewdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hanyh on 2017/9/2.
 * 继承recyclerview的内部类adapter
 */

public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder>{

    private Context mContext;
    private List<String> mDatas;
    private LayoutInflater mLayoutInflater;
    protected OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public SimpleAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        this.mLayoutInflater=LayoutInflater.from(context);
    }


    protected interface OnItemClickListener{

        void onItemClick(View view,int position);

        void onItemLongClick(View view,int position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view=mLayoutInflater.inflate(R.layout.item_simple_layout,viewGroup,false);
        MyViewHolder viewHolder=new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, int position) {

        myViewHolder.tv.setText(mDatas.get(position));
        if (mOnItemClickListener!=null){
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    mOnItemClickListener.onItemClick(myViewHolder.itemView,myViewHolder.getLayoutPosition());
                }
            });

            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    mOnItemClickListener.onItemLongClick(myViewHolder.itemView,myViewHolder.getLayoutPosition());
                    return false;
                }
            });
        }
    }

    @Override
    public int getItemCount() {

        return mDatas!=null?mDatas.size():0;
    }

    public void addItem(int pos){
        mDatas.add(pos,"insert one");
        notifyItemInserted(pos);
    }

    public void delItem(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView tv;
    public MyViewHolder(View itemView) {
        super(itemView);
        tv=itemView.findViewById(R.id.id_item);
    }
}