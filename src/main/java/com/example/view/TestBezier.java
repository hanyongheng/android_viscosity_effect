package com.example.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by hanyh on 2017/10/1.
 */

public class TestBezier extends View {

    public TestBezier(Context context) {
        super(context);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public TestBezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    //画笔
    private final Paint mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path mPath=new Path();
    private void init(){



        Paint paint=mPaint;

        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        //draw first order bezier
        Path path=mPath;
        path.moveTo(100,100);
        path.lineTo(300,300);

        // draw second order bezier

        path.quadTo(500,100,700,300);

        path.moveTo(100,400);
        // draw third order bezier
        path.cubicTo(200,200,300,500,700,300);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
