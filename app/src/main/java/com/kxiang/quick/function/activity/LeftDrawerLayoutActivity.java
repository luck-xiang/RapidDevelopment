package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class LeftDrawerLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_drawer_layout);
        initStatusBarColor();
    }

    @Override
    protected void initView() {
        gestureDetector = new GestureDetector(this, new MyGestureListener());
//        gestureDetector.onTouchEvent(ev);
    }

    /**
     * 收拾监听
     */
    private GestureDetector gestureDetector;

    class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        /*
        *每按一下屏幕立即触发
        * */
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        /*
        *用户按下屏幕并且没有移动或松开。主要是提供给用户一个可视化的反馈，告诉用户他们的按下操作已经
        * 被捕捉到了。如果按下的速度很快只会调用onDown(),按下的速度稍慢一点会先调用onDown()再调用onShowPress().
        * */
        public void onShowPress(MotionEvent e) {

        }

        @Override
        /*
        *一次单纯的轻击抬手动作时触发
        * */
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        /*
        *屏幕拖动事件，如果按下的时间过长，调用了onLongPress，再拖动屏幕不会触发onScroll。拖动屏幕会多次触发
        * @param e1 开始拖动的第一次按下down操作,也就是第一个ACTION_DOWN
        * @parem e2 触发当前onScroll方法的ACTION_MOVE
        * @param distanceX 当前的x坐标与最后一次触发scroll方法的x坐标的差值。
        * @param diastancY 当前的y坐标与最后一次触发scroll方法的y坐标的差值。
        * */
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        /*
        * 长按。在down操作之后，过一个特定的时间触发
        * */
        public void onLongPress(MotionEvent e) {

        }

        @Override
        /*
        * 按下屏幕，在屏幕上快速滑动后松开，由一个down,多个move,一个up触发
        * @param e1 开始快速滑动的第一次按下down操作,也就是第一个ACTION_DOWN
        * @parem e2 触发当前onFling方法的move操作,也就是最后一个ACTION_MOVE
        * @param velocityX：X轴上的移动速度，像素/秒
        * @parram velocityY：Y轴上的移动速度，像素/秒
        * */
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.e("DragViewLayout", "onFling");
            return false;
        }
    }
}
