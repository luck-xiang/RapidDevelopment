package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.view.View;

import com.kexiang.function.utils.LogUtils;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/7/28 17:53
 */

public abstract class BaseRecycleCheckAdapter<T> extends BaseRecycleAdapter {


    private boolean[] check;
    private int selectMax;
    public static final int UNDEFINED = -1;
    private int selectNumbers = 0;

    public BaseRecycleCheckAdapter(Context context, List list) {
        super(context, list);
        check = new boolean[list.size()];
        selectMax = UNDEFINED;
    }


    public abstract class BaseChekcViewHolder extends BaseRecycleViewHolder<T> {

        public BaseChekcViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(List<T> data, int position) {
            super.setData(data, position);
            checkView.setSelected(check[position]);
            LogUtils.toE("checkViewsetData", "check:" + check[position] + position);
        }

        private View checkView;

        /**
         * 设置单选或者多选框显示的view
         *
         * @param checkView
         */
        public void setCheckView(View checkView) {
            this.checkView = checkView;
        }


        public void setOnCheck(View v) {


            LogUtils.toE("checkView", "check:" + check[position] + position);


            if (selectMax <= 0) {
                if (checkView.isSelected()) {
                    selectNumbers--;
                }
                else {
                    selectNumbers++;
                }
                checkView.setSelected(!checkView.isSelected());
                check[position] = checkView.isSelected();

                if (onCheckListener != null) {
                    onCheckListener.checkListener(check[position], v, position);
                }
            }
            else if (selectNumbers < selectMax) {
                if (checkView.isSelected()) {
                    selectNumbers--;
                }
                else {
                    selectNumbers++;
                }
                checkView.setSelected(!checkView.isSelected());
                check[position] = checkView.isSelected();


                if (onCheckListener != null) {
                    onCheckListener.checkListener(check[position], v, position);
                }

            }
            else {
                if (checkView.isSelected()) {
                    selectNumbers--;
                    checkView.setSelected(!checkView.isSelected());
                    check[position] = checkView.isSelected();

                    if (onCheckListener != null) {
                        onCheckListener.checkListener(check[position], v, position);
                    }
                }
            }

        }
    }

    public interface OnCheckListener {
        void checkListener(boolean check, View view, int position);
    }

    private OnCheckListener onCheckListener;

    public boolean[] getCheck() {
        return check;
    }


    /**
     * @param onCheckListener
     */
    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    /**
     * 设置最多选择个数，默认为UNDEFINED，即为无无限个
     *
     * @param selectMax
     */
    public void setSelectMax(int selectMax) {
        this.selectMax = selectMax;
    }

    /**
     * 设置已经选中的个数
     *
     * @param selectNumbers
     */
    public void setSelectNumbers(int selectNumbers) {
        this.selectNumbers = selectNumbers;
    }

}
