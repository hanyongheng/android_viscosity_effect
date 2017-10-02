package com.example.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;

import com.example.recelviewdemo.R;

/**
 * Created by hanyh on 2017/10/1.
 */

public class TouchPullView extends View {

    //圆的画笔
    private Paint mCirclePaint;
    //圆的半径
    private int mCircleRadius = 40;
    //上层控件传递过来的拉动进度
    private float mProgress;
    //圆心的横纵坐标
    private float mCirclePointX, mCirclePointY;
    //拖动的高度
    private int mDragHeight = 300;
    //目标宽度跟起始点相关的一个参数值
    private int mTargetWidth=300;
    //贝塞尔路径
    private Path mBezirePath = new Path();
    //贝塞尔画笔
    private Paint mBezirePaint;
    //重心点的坐标，决定控制点的最终y坐标
    private int mTargetGravityHeight=10;
    //角度变换 0~135
    private int mTangentAngle = 105;
    //释放后的复位的属性动画
    private ValueAnimator mValueAnimator;
    //进度差值器给拉动的进度mProgress加上一个差值动画
    private Interpolator mProgressInterpolator=new DecelerateInterpolator();
    //圆的背景drawable
    private Drawable mContentDrawable=null;
    //圆和bezire曲线之间的间距 默认为0
    private int mContentDrawableMargin=0;

    //设置上层view传递过来的进度
    public void setProgress(float progress) {
        this.mProgress = progress;
        //重新进行测量
        requestLayout();
    }

    public TouchPullView(Context context) {

        this(context,null);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        Context context=getContext();
        TypedArray a=context.obtainStyledAttributes(attrs, R.styleable.TouchPullView);

        int color=a.getColor(R.styleable.TouchPullView_pColor,Color.parseColor("#FF6EB4"));
        mCircleRadius= (int) a.getDimension(R.styleable.TouchPullView_pRadius,mCircleRadius);
        mDragHeight=a.getDimensionPixelOffset(R.styleable.TouchPullView_pDragHeight,mDragHeight);
        mTangentAngle=a.getInteger(R.styleable.TouchPullView_pTangentAngle,100);
        mTargetWidth=a.getDimensionPixelOffset(R.styleable.TouchPullView_pTargetWidth,mTargetWidth);
        mTargetGravityHeight=a.getDimensionPixelOffset(R.styleable.
                TouchPullView_pTargetGravityHeight,mTargetGravityHeight);
        mContentDrawable=a.getDrawable(R.styleable.TouchPullView_pContentDrawable);
        mContentDrawableMargin=a.getDimensionPixelOffset(R.styleable.TouchPullView_pContentDrawableMargin,0);
        a.recycle();

        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setDither(true);
        //设置防抖动
        p.setAntiAlias(true);
        //设置画笔的填充方式
        p.setStyle(Paint.Style.FILL);
        //设置画笔颜色
        p.setColor(color);
        mCirclePaint = p;

        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //抗锯齿
        p.setDither(true);
        //设置防抖动
        p.setAntiAlias(true);
        //设置画笔的填充方式
        p.setStyle(Paint.Style.FILL);
        //设置画笔颜色
        p.setColor(color);
        mBezirePaint = p;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count=canvas.save();
        int transX= (int) ((getWidth()-getValueByLine(getWidth(),mTargetWidth,mProgress))/2);
        canvas.translate(transX,0);
        //画bezire曲线
        canvas.drawPath(mBezirePath, mBezirePaint);
        //画圆
        canvas.drawCircle(mCirclePointX, mCirclePointY, mCircleRadius, mCirclePaint);
        //绘制drawable
        Drawable drawable=mContentDrawable;

        if (drawable!=null){
            canvas.save();
            canvas.clipRect(drawable.getBounds());
            drawable.draw(canvas);
            canvas.restore();
        }
        canvas.restoreToCount(count);
    }


    /**
     * 当控件开始测量时候会掉次方法
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minCircleWidth = 2 * mCircleRadius + getPaddingLeft() + getPaddingRight();
        int minCircleHeight = (int) ((mDragHeight * mProgress + 0.5f) + getPaddingTop() + getPaddingBottom());
        int measureWidth, measureHeight;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        //宽度的测量 给个精确值
        if (widthMode == MeasureSpec.EXACTLY) {
            measureWidth = width;

            //最多
        } else if (widthMode == MeasureSpec.AT_MOST) {
            measureWidth = Math.min(minCircleWidth, width);
        } else {
            measureWidth = minCircleWidth;
        }
        //高度的测量
        if (heightMode == MeasureSpec.EXACTLY) {
            measureHeight = height;

            //最多
        } else if (heightMode == MeasureSpec.AT_MOST) {
            measureHeight = Math.min(minCircleHeight, height);

        } else {
            measureHeight = minCircleHeight;
        }
        //设置测量值
        setMeasuredDimension(measureWidth, measureHeight);

    }

    /**
     * 当控件的宽度或者高度发生改变的时候会触发次方法
     * 没有必要在onDraw方法中去时时的动态改变一个控件的宽度和高度因为比较消耗cpu
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        upDatePathLayout();
    }

    private void upDatePathLayout() {

        //获取进度值
//        final float progress=mProgress;
        final float progress=mProgressInterpolator.getInterpolation(mProgress);

        //获取可绘制区域的高度和宽度
        final float w=getValueByLine(getWidth(),mTargetWidth,mProgress);
        final float h=getValueByLine(0,mDragHeight,mProgress);
        Log.i("test", "current p is " + this.mProgress);
        Log.i("test", "current getWidth is " + getWidth());
        Log.i("test", "current w is " + w);
        Log.i("test", "current h is " + h);
        //圆心的x坐标 这里暂时认为没有必要搞这么复杂
        // 只需要取当前view的中心坐标getWidth/2为圆心坐标即可，就不需要调整canvas的坐标了
        final float cPointX=w/2;
        //圆的半径
        final float cRadius=mCircleRadius;
        //圆心的y轴坐标
        final float cPointY=h-cRadius;
        //控制点结束的y的坐标
        final float endControlY=mTargetGravityHeight;
        //更新圆的坐标
        mCirclePointX=cPointX;
        mCirclePointY=cPointY;


        final Path path=mBezirePath;
        //path的复位
        path.reset();
        path.moveTo(0,0);

        //左边部分的结束点和控制点
        float lEndPointX,lEndPointY;
        float lControlPointX,lControlPointY;

        //获取当前切线进度的弧度
        double radian=Math.toRadians(getValueByLine(0,mTangentAngle,progress));
        float x= (float) (Math.sin(radian)*cRadius);
        float y= (float) (Math.cos(radian)*cRadius);
//        Log.i("test", "current mTangentAngle is " + mTangentAngle);
//        Log.i("test", "current x is " + x);
//        Log.i("test", "current y is " + y);
        lEndPointX=cPointX-x;
        lEndPointY=cPointY+y;

        lControlPointY=getValueByLine(0,endControlY,progress);
        //结束点和控制点之间的高度差值
        float tHeight=lEndPointY-lControlPointY;

        float tWidth= (float) (tHeight/Math.tan(radian));
        lControlPointX=lEndPointX-tWidth;

        //画左边bezire曲线
        path.quadTo(lControlPointX,lControlPointY,lEndPointX,lEndPointY);
        //链接到右边
        path.lineTo(cPointX+(cPointX-lEndPointX),lEndPointY);
        //画右边bezire曲线
        path.quadTo(cPointX+(cPointX-lControlPointX),lControlPointY,w,0);
        //更新内容部分drawable
        upDateContentLayout(cPointX,cPointY,cRadius);
    }

    /**
     * 对内容部分进行测量和绘制
     * @param cx 圆心x
     * @param cy 圆心x
     * @param radius 半径
     */
    private void upDateContentLayout(float cx,float cy,float radius){

        Drawable drawable=mContentDrawable;
        if (drawable!=null){

            int margin=mContentDrawableMargin;
            int l= (int) (cx-radius+margin);
            int r= (int) (cx+radius-margin);
            int t= (int) (cy-radius+margin);
            int b= (int) (cy+radius-margin);
            drawable.setBounds(l,t,r,b);
        }

    }
    private float getValueByLine(float start, float end, float progress) {

        return start + (end - start) * progress;
    }

    /**
     * 释放的动画
     */
    public void release(){

        if (mValueAnimator==null){
            final ValueAnimator animator=ValueAnimator.ofFloat(mProgress,0f);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(400);
            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    Object val=animator.getAnimatedValue();
                    if (val instanceof Float){
                        setProgress((Float) val);
                    }

                }
            });

            mValueAnimator=animator;
        }else {
            mValueAnimator.cancel();
            mValueAnimator.setFloatValues(mProgress,0f);
        }
        mValueAnimator.start();
    }
}
