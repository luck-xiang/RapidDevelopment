package com.kxiang.quick.function.activity.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/25 9:30
 */

public class ViewB extends RelativeLayout {

    public ViewB(Context context) {
        super(context);
    }

    public ViewB(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.toE("ViewB", "dispatchTouchEvent：" + ev.getAction());

        return super.dispatchTouchEvent(ev);
        //return false;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.toE("ViewB", "onInterceptTouchEvent：" + ev.getAction());

//        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
//
//            return true;
//        }
//        else if (ev.getAction() == MotionEvent.ACTION_UP) {
//
//        }

        return super.onInterceptTouchEvent(ev);
//        try {
//            // 得到私有字段
//            Field privateStringField = ViewGroup.class
//                    .getDeclaredField("mGroupFlags");
//
//            // 通過反射設置私有對象可以訪問
//            privateStringField.setAccessible(true);
//
//            // 從父類中得到對象，并強制轉換為想要得到的對象
//
//            int fieldValue = (int) privateStringField.get(this);
//            LogUtils.toE("ViewB", "onInterceptTouchEventmGroupFlags：" + fieldValue);
//
////            // 將私有對象設置新的值
////            String str = "New Hello World !";
////            privateStringField.set(this, str);
////            String newStr = (String) privateStringField.get(this);
////            System.out.println("new fieldValue = " + newStr);
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//
//        return super.onInterceptTouchEvent(ev);
        //return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.toE("ViewB", "onTouchEvent：" + event.getAction());
//        try {
//            // 得到私有字段
//            Field privateStringField = ViewGroup.class
//                    .getDeclaredField("mViewFlags");
//
//            // 通過反射設置私有對象可以訪問
//            privateStringField.setAccessible(true);
//
//            // 從父類中得到對象，并強制轉換為想要得到的對象
//
//            int fieldValue = (int) privateStringField.get(this);
//            LogUtils.toE("ViewB", "onTouchEventmViewFlags：" + fieldValue);
////            LogUtils.toE("ViewB", "onTouchEventmGroupFlags：" + fieldValue);
////            LogUtils.toE("ViewB", "onTouchEventmGroupFlags：" + fieldValue);
////            LogUtils.toE("ViewB", "onTouchEventmGroupFlags：" +((fieldValue & 0x80000) != 0) );
//
//        } catch (NoSuchFieldException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }


        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            return true;
        }
        else if (event.getAction() == MotionEvent.ACTION_UP) {

        }


        return super.onTouchEvent(event);

        //        return super.onTouchEvent(event);
//        return true;
    }
}
