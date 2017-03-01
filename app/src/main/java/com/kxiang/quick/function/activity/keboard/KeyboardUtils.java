package com.kxiang.quick.function.activity.keboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.widget.EditText;

import com.kxiang.quick.R;
import com.kxiang.quick.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/1 11:15
 */

public class KeyboardUtils {
    private Keyboard symbols;// 数字键盘
    private EditText showNumber;
    private KeyboardView keyboardView;
    private boolean point = false;
    private int pointNumber = 0;

    public KeyboardUtils(Activity activity, EditText showNumber) {
        this.showNumber = showNumber;
        symbols = new Keyboard(activity, R.xml.symbols);
        keyboardView = (KeyboardView) activity.findViewById(R.id.keyboardview);
        keyboardView.setKeyboard(symbols);
        keyboardView.setEnabled(true);
        keyboardView.setPreviewEnabled(false);
        keyboardView.setOnKeyboardActionListener(keyboardListener);
    }

    private KeyboardView.OnKeyboardActionListener keyboardListener = new KeyboardView.OnKeyboardActionListener() {
        private final int NUMBER = 2;

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
}
