package com.kexiang.function.view;

import android.app.Activity;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/1 11:15
 */

public class KeyboardCustomUtils implements View.OnClickListener {
    private EditText showNumber;

    private boolean point = false;
    private int pointNumber = 0;
    private int maxLength = 10;

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength - 1;
    }

    public void initData() {
        point = false;
        pointNumber = 0;
    }

    /**
     * 1
     */
    private TextView tv_1;
    /**
     * 2
     */
    private TextView tv_2;
    /**
     * 3
     */
    private TextView tv_3;
    /**
     * 4
     */
    private TextView tv_4;
    /**
     * 5
     */
    private TextView tv_5;
    /**
     * 6
     */
    private TextView tv_6;
    /**
     * 7
     */
    private TextView tv_7;
    /**
     * 8
     */
    private TextView tv_8;
    /**
     * 9
     */
    private TextView tv_9;
    /**
     * .
     */
    private TextView tv_point;
    /**
     * 0
     */
    private TextView tv_0;
    private TextView tv_delete;
    /**
     * 确定
     */
    private TextView tv_sure;

    public KeyboardCustomUtils(Activity activity, EditText showNumber) {
        this.showNumber = showNumber;

        tv_1 = (TextView) activity.findViewById(R.id.tv_1);
        tv_1.setOnClickListener(this);
        tv_2 = (TextView) activity.findViewById(R.id.tv_2);
        tv_2.setOnClickListener(this);
        tv_3 = (TextView) activity.findViewById(R.id.tv_3);
        tv_3.setOnClickListener(this);
        tv_4 = (TextView) activity.findViewById(R.id.tv_4);
        tv_4.setOnClickListener(this);
        tv_5 = (TextView) activity.findViewById(R.id.tv_5);
        tv_5.setOnClickListener(this);
        tv_6 = (TextView) activity.findViewById(R.id.tv_6);
        tv_6.setOnClickListener(this);
        tv_7 = (TextView) activity.findViewById(R.id.tv_7);
        tv_7.setOnClickListener(this);
        tv_8 = (TextView) activity.findViewById(R.id.tv_8);
        tv_8.setOnClickListener(this);
        tv_9 = (TextView) activity.findViewById(R.id.tv_9);
        tv_9.setOnClickListener(this);
        tv_point = (TextView) activity.findViewById(R.id.tv_point);
        tv_point.setOnClickListener(this);
        tv_0 = (TextView) activity.findViewById(R.id.tv_0);
        tv_0.setOnClickListener(this);
        tv_delete = (TextView) activity.findViewById(R.id.tv_delete);
        tv_delete.setOnClickListener(this);

        tv_sure = (TextView) activity.findViewById(R.id.tv_sure);
        tv_sure.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_1) {
            onKeyboardViewListener.OnKeyboardView(49);
        }
        else if (i == R.id.tv_2) {
            onKeyboardViewListener.OnKeyboardView(50);
        }
        else if (i == R.id.tv_3) {
            onKeyboardViewListener.OnKeyboardView(51);
        }
        else if (i == R.id.tv_4) {
            onKeyboardViewListener.OnKeyboardView(52);
        }
        else if (i == R.id.tv_5) {
            onKeyboardViewListener.OnKeyboardView(53);
        }
        else if (i == R.id.tv_6) {
            onKeyboardViewListener.OnKeyboardView(54);
        }
        else if (i == R.id.tv_7) {
            onKeyboardViewListener.OnKeyboardView(55);
        }
        else if (i == R.id.tv_8) {
            onKeyboardViewListener.OnKeyboardView(56);
        }
        else if (i == R.id.tv_9) {
            onKeyboardViewListener.OnKeyboardView(57);
        }
        else if (i == R.id.tv_point) {
            onKeyboardViewListener.OnKeyboardView(46);
        }
        else if (i == R.id.tv_0) {
            onKeyboardViewListener.OnKeyboardView(48);
        }
        else if (i == R.id.tv_delete) {
            onKeyboardViewListener.OnKeyboardView(-5);
        }
        else if (i == R.id.tv_sure) {
        }
    }


    private interface OnKeyboardViewListener {
        void OnKeyboardView(int primaryCode);
    }


    private OnKeyboardViewListener onKeyboardViewListener = new OnKeyboardViewListener() {
        @Override
        public void OnKeyboardView(int primaryCode) {
            inputControl(primaryCode);
        }
    };

    /**
     * 数字键盘
     *
     * @param primaryCode
     */
    private void inputControl(int primaryCode) {
        Editable editable = showNumber.getText();
        int start = showNumber.getSelectionStart();
        int stringLength = editable.length();
        if (primaryCode == -5) {
            if (editable != null && start > 0) {
                editable.delete(start - 1, start);
            }
        }
        else {

            if (stringLength > maxLength) {
                return;
            }

            int posDot = editable.toString().indexOf(".");
            String select = Character.toString((char) primaryCode);
            if (posDot >= 0) {
                if (!select.equals(".")) {
                    if (start - posDot <= 0) {
                        editable.insert(start, select);
                    }
                    else {
                        if (stringLength - posDot < 3)
                            editable.insert(start, select);
                    }

                }
            }
            else {
                editable.insert(start, select);
            }
            //允许输入的最多值为小于1000
//            if (Double.parseDouble(editable.toString())>10000) {
//                editable.delete(editable.length()-1, editable.length());
//            }

        }
    }

    private final int NUMBER = 2;

    private void inputControlOther(int primaryCode, int[] keyCodes) {
        LogUtils.toE("onKey:" + primaryCode);
        Editable editable = showNumber.getText();
        int start = showNumber.getSelectionStart();
        int stringLength = editable.length();
        LogUtils.toE("kaishi", "start:" + start);
        if (primaryCode == -5) {
            if (point && ((stringLength - pointNumber - start) <= 0)) {
                pointNumber--;
            }
            if (start > 0) {
                String atString = editable.charAt(start - 1) + "";
                LogUtils.toE("kaishi", "atString:" + atString);
                if (atString.equals(".")) {
                    point = false;
                    pointNumber = 0;
                }
            }

            if (editable != null && start > 0)
                editable.delete(start - 1, start);
        }
        else {

            if (stringLength == maxLength) {
                return;
            }
            String select = Character.toString((char) primaryCode);
            LogUtils.toE("pointNumber:" + pointNumber);
            if (select.equals(".")) {
                if (!point) {
                    editable.insert(start, select);
                }
                point = true;
            }
            else {
                if (point) {

                    int spiltNumber = stringLength - pointNumber - start;
                    LogUtils.toE("kaishi", "spiltNumber:" + spiltNumber);
                    LogUtils.toE("kaishi", "spiltNumber:" + pointNumber);
                    LogUtils.toE("kaishi", "spiltNumber:" + start);
                    if (spiltNumber > 0) {
                        editable.insert(start, select);
                    }
                    else {
                        if (pointNumber < NUMBER) {
                            editable.insert(start, select);

                            if (pointNumber == NUMBER) {
                                pointNumber = NUMBER;
                            }
                            else {
                                pointNumber++;
                            }

                        }
                    }
                }
                else {
                    editable.insert(start, select);
                }

            }

        }
    }


    // 隐藏系统键盘并且让光标存在
    public void hideSoftInputMethod(EditText ed, Activity activity) {
        activity
                .getWindow()
                .setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

        int currentVersion = android.os.Build.VERSION.SDK_INT;
        String methodName = null;
        if (currentVersion >= 16) {
            // 4.2
            methodName = "setShowSoftInputOnFocus";
        }
        else if (currentVersion >= 14) {
            // 4.0
            methodName = "setSoftInputShownOnFocus";
        }

        if (methodName == null) {
            ed.setInputType(InputType.TYPE_NULL);
        }
        else {
            Class<EditText> cls = EditText.class;
            Method setShowSoftInputOnFocus;
            try {
                setShowSoftInputOnFocus = cls.getMethod(methodName, boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(ed, false);
            } catch (NoSuchMethodException e) {
                ed.setInputType(InputType.TYPE_NULL);
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
