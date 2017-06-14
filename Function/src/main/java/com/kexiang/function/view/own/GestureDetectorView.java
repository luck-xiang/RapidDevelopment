package com.kexiang.function.view.own;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/6 17:15
 */

public class GestureDetectorView extends View implements View.OnTouchListener{
    private Context mContext;
    private GestureDetector mGestureDetector;
    private static final String TAG = "MyView";
    public GestureDetectorView(Context context) {
        super(context);
        initData(context);
    }

    public GestureDetectorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public GestureDetectorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }


    private void initData(Context context) {
        this.mContext = context;
        super.setOnTouchListener(this);
        super.setClickable(true);
        super.setLongClickable(true);
        super.setFocusable(true);
        mGestureDetector = new GestureDetector(mContext,new MyGestureListener());
        mGestureDetector.setOnDoubleTapListener(new MyGestureListener());
    }

    /*
    * 当该view上的事件被分发到view上时触发该方法的回调
    * 如果这个方法返回false时,该事件就会被传递给Activity中的onTouchEvent方法来处理
    * 如果该方法返回true时，表示该事件已经被onTouch函数处理玩，不会上传到activity中处理
    * 该方法属于View.OnTouchListening接口
    * */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }


    /*
    *
    * 手势监听类
    * */
    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        public MyGestureListener() {
            super();
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.e(TAG, "onDoubleTap");
            return true;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Log.e(TAG, "onDoubleTapEvent");
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Log.e(TAG, "onSingleTapConfirmed");
            return true;
        }

        @Override
        public boolean onContextClick(MotionEvent e) {
            Log.e(TAG, "onContextClick");
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Log.e(TAG, "onDown");
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
            Log.e(TAG, "onShowPress");
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Log.e(TAG, "onSingleTapUp");
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            Log.e(TAG, "onScroll");
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Log.e(TAG, "onLongPress");
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e(TAG, "onFling");
            return true;
        }
    }
}