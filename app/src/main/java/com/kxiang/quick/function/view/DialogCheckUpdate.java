package com.kxiang.quick.function.view;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kexiang.function.base.BaseDialogFragment;
import com.kexiang.function.view.OnDialogBackListener;
import com.kxiang.quick.R;

/**
 * 项目名称:JobCNYZ
 * 创建人:kexiang
 * 创建时间:2017/4/7 15:44
 */

public class DialogCheckUpdate extends BaseDialogFragment {


    @Override
    protected int getContentView() {
        return R.layout.dialog_check_update;
    }


    @Override
    protected void onActivityCreatedBase(Bundle savedInstanceState) {
        layoutParams(0.8f, 0.35f);
        TextView tv_explain = (TextView) getView().findViewById(R.id.tv_explain);
        getView().findViewById(R.id.ll_othre).setVisibility(View.GONE);
        getView().findViewById(R.id.tv_dialog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onDialogBackListener != null) {
                    onDialogBackListener.onDialogBack("back");
                }
            }
        });

    }

    private OnDialogBackListener<String> onDialogBackListener;

    public void setOnDialogBackListener(OnDialogBackListener<String> onDialogBackListener) {
        this.onDialogBackListener = onDialogBackListener;
    }
}
