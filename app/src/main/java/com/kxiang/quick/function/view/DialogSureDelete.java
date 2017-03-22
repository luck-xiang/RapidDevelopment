package com.kxiang.quick.function.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.kexiang.function.base.BaseDialogFragment;
import com.kexiang.function.view.OnDialogBackListener;
import com.kxiang.quick.R;


/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/29 14:26
 */

public class DialogSureDelete extends BaseDialogFragment implements View.OnClickListener {

    @Override
    protected int getContentView() {
        return R.layout.fun_dialog_sure_delete;
    }

    private TextView tv_dialog_content;

    public static DialogSureDelete newInstance(String showText) {
        DialogSureDelete delete = new DialogSureDelete();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("string", showText);
        delete.setArguments(args);

        return delete;
    }

    private String textChange;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            textChange = getArguments().getString("string");
        }

    }

    @Override
    protected void onActivityCreatedBase(Bundle savedInstanceState) {
        layoutParams(getContext(), getView().findViewById(R.id.ll_all), 0.7f, 0.30f);
        getView().findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
        getView().findViewById(R.id.tv_dialog_sure).setOnClickListener(this);
        tv_dialog_content = (TextView) getView().findViewById(R.id.tv_dialog_content);

        if (!TextUtils.isEmpty(textChange)) {
            tv_dialog_content.setText(textChange);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_dialog_cancel:

                dismiss();
                break;
            case R.id.tv_dialog_sure:
                if (onDialogBackListener != null) {
                    onDialogBackListener.onDialogBack("");
                }
                dismiss();
                break;
        }

    }

    private OnDialogBackListener<String> onDialogBackListener;

    public void setOnDialogBackListener(OnDialogBackListener<String> onDialogBackListener) {
        this.onDialogBackListener = onDialogBackListener;
    }
}
