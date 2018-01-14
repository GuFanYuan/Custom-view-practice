package com.example.gufan.myview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.util.jar.Attributes;

/**
 * Created by gufan on 2018/1/14.
 */

public class CircleView extends View {
    private final static String TAG = "CircleView";
    private int mColor = Color.RED;
    private int lastX;
    private int lastY;
    private Scroller mScroller;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public CircleView(Context context){
        super(context);
        init(context);
    }

    public CircleView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public CircleView(Context context,AttributeSet attrs,int defStyleAttr){
        super(context,attrs,defStyleAttr);
        Log.e(TAG,"CircleView(Context context,AttributeSet attrs,int defStyleAttr)");
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircleView);
        mColor = a.getColor(R.styleable.CircleView_circle_color,Color.RED);
        a.recycle();
        init(context);
    }

    private void init(Context context){
        Log.e(TAG,"mColor = "+mColor);
        mPaint.setColor(mColor);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int widthMeasureMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMeasureMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthMeasureSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMeasureSize = MeasureSpec.getSize(heightMeasureSpec);
        if(widthMeasureMode == MeasureSpec.AT_MOST && heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200);
        }else if(widthMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,heightMeasureSize);
        }else if(heightMeasureMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthMeasureSize,200);
        }
    }

    @Override
    protected void onDraw(Canvas  canvas){
        super.onDraw(canvas);
        final int paddingLeft = getPaddingLeft();
        final int paddingRight = getPaddingRight();
        final int paddingTop = getPaddingTop();
        final int paddingBottom = getPaddingBottom();
        int width = getWidth()-paddingLeft-paddingRight;
        int hight = getHeight()-paddingBottom-paddingTop;
        int radius = Math.min(width,hight)/2;
        canvas.drawCircle(paddingLeft+width/2,paddingTop+hight/2,radius,mPaint);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        // 判断Scroller是否执行完毕
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(
                    mScroller.getCurrX(),
                    mScroller.getCurrY());
            // 通过重绘来不断调用computeScroll
            invalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                ((View) getParent()).scrollBy(-offsetX, -offsetY);
                break;
            case MotionEvent.ACTION_UP:
                // 手指离开时，执行滑动过程
               View viewGroup = ((View) getParent());
                mScroller.startScroll(
                        viewGroup.getScrollX(),
                        viewGroup.getScrollY(),
                        -viewGroup.getScrollX(),
                        -viewGroup.getScrollY());
                invalidate();
                // 在当前left、top、right、bottom的基础上加上偏移量
                break;
        }
        return true;
    }
}
