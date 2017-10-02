package com.example.recelviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;

import com.example.view.TouchPullView;

/**
 * Created by hanyh on 2017/10/1.
 */

public class ViscosityViewActivity extends Activity implements View.OnTouchListener{

    //记录按下的时候的y轴坐标 以便计算拉动的距离
    private float mTouchModeStartY;
    //Y轴方向最大的移动距离
    private static final float TOUCH_MODE_MAX_Y=600;
    private TouchPullView mTouchPullView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viscosity_layout);
        findViewById(R.id.activity_main).setOnTouchListener(this);
        mTouchPullView=findViewById(R.id.touchPull);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        int action=motionEvent.getActionMasked();

        switch (action){

            case MotionEvent.ACTION_DOWN:

                mTouchModeStartY=motionEvent.getY();
                return true;

            case MotionEvent.ACTION_MOVE:

                float y=motionEvent.getY();
                //往下拉
                if (y>=mTouchModeStartY){

                    float modeSize=y-mTouchModeStartY;
                    //拉动的进度
                    float progress=modeSize>=TOUCH_MODE_MAX_Y?1:modeSize/TOUCH_MODE_MAX_Y;
                    mTouchPullView.setProgress(progress);
                }
                return true;

            default:
                mTouchPullView.release();
                break;
        }
        return false;
    }
}
