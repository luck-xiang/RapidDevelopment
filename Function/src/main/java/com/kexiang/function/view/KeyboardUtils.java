package com.kexiang.function.view;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/1 11:15
 */

public class KeyboardUtils {
    private Keyboard symbols;// 数字键盘
    private EditText showNumber;
    public KeyboardView keyboardView;
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

    public KeyboardUtils(Activity activity, EditText showNumber) {
        this.showNumber = showNumber;
        symbols = new Keyboard(activity, R.xml.symbols);
        keyboardView = (KeyboardView) activity.findViewById(R.id.keyboardview);
        keyboardView.setKeyboard(symbols);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(keyboardListener);

    }
    private final int NUMBER = 2;

    private KeyboardView.OnKeyboardActionListener keyboardListener = new KeyboardView.OnKeyboardActionListener() {


        @Override
        public void onPress(int primaryCode) {

            LogUtils.toE("onPress:" + primaryCode);
        }

        @Override
        public void onRelease(int primaryCode) {
            LogUtils.toE("onRelease:" + primaryCode);
        }

        //这个方法是对只能输入数字切小数后面只能有两位小数做判断并且显示到界面上
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            inputControl(primaryCode,keyCodes);

        }

        @Override
        public void onText(CharSequence text) {
            LogUtils.toE("onText:" + text);
        }

        @Override
        public void swipeLeft() {
            LogUtils.toE("swipeLeft:");
        }

        @Override
        public void swipeRight() {
            LogUtils.toE("swipeRight:");
        }

        @Override
        public void swipeDown() {
            LogUtils.toE("swipeDown:");
        }

        @Override
        public void swipeUp() {
            LogUtils.toE("swipeUp:");
        }
    };


    /**
     * 数字键盘
     * @param primaryCode
     * @param keyCodes
     */
    private void inputControl(int primaryCode, int[] keyCodes) {
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
