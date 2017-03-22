package com.kxiang.quick.function.view.check;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.kexiang.function.utils.ScreenUtils;
import com.kexiang.function.view.OnDialogBackListener;
import com.kxiang.quick.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/8 14:00
 */

public class DialogCheck extends DialogFragment implements View.OnClickListener {

    /**
     * 限制选择的个数
     */
    private int selectLimit = -1;
    /**
     * 当前已选中的个数
     */
    private int selectLimitChange;


    public static DialogCheck newInstance(CheckBean bean) {
        DialogCheck check = new DialogCheck();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("check", bean);
        check.setArguments(args);
        return check;
    }

    public static DialogCheck newInstance(CheckBean bean, String showMessage) {
        DialogCheck check = new DialogCheck();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("check", bean);
        args.putString("message", showMessage);
        check.setArguments(args);
        return check;
    }


    public static DialogCheck newInstance(CheckBean bean, String showMessage,
                                          int selectLimit, int selectLimitChange) {
        DialogCheck check = new DialogCheck();
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putSerializable("check", bean);
        args.putString("message", showMessage);
        args.putInt("Limit", selectLimit);
        args.putInt("Change", selectLimitChange);
        check.setArguments(args);
        return check;
    }


    private String showMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {

            CheckBean checkBean = (CheckBean) getArguments().getSerializable("check");
            showMessage = getArguments().getString("message");
            selectLimit = getArguments().getInt("Limit", -1);
            selectLimitChange = getArguments().getInt("Change", -1);
            if (checkBean != null) {
                data = checkBean.getItemBeen();
            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.fun_dialog_check, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        layoutParams(getContext(), getView().findViewById(R.id.ll_all));
        initSelectRecycleView();
        if (!TextUtils.isEmpty(showMessage)) {
            ((TextView) getView().findViewById(R.id.tv_show_message)).setText(showMessage);
        }


    }

    private void layoutParams(Context context, View view) {
        int width = (int) (ScreenUtils.getScreenWidth(context) * 0.7f);
        int height = (int) (ScreenUtils.getScreenHeight(context) * 0.5f);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        view.setLayoutParams(params);
    }

    private RecyclerView rlv_select;
    private List<CheckBean.CheckItemBean> data;
    private DialogCheckAdapter<CheckBean.CheckItemBean> allAdapter;


    private void initSelectRecycleView() {

        if (data == null) {
            data = new ArrayList<>();
        }

        rlv_select = (RecyclerView) getView().findViewById(R.id.rlv_select);
        allAdapter = new DialogCheckAdapter<>(getContext(), data, selectLimit, selectLimitChange);
        rlv_select.setLayoutManager(new GridLayoutManager(getContext(), 3));
        rlv_select.setAdapter(allAdapter);

        getView().findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
        getView().findViewById(R.id.tv_dialog_sure).setOnClickListener(this);

    }


    private void back() {
        if (data != null) {
            List<CheckBean.CheckItemBean> itemBackBeen = new ArrayList<>();
            for (CheckBean.CheckItemBean bean : data) {
                if (bean.isSelect()) {
                    itemBackBeen.add(bean);
                }
            }
            onDialogBackListener.onDialogBack(itemBackBeen);
        }
    }


    private OnDialogBackListener<List<CheckBean.CheckItemBean>> onDialogBackListener;

    public void setOnDialogBackListener(OnDialogBackListener<List<CheckBean.CheckItemBean>> onDialogBackListener) {
        this.onDialogBackListener = onDialogBackListener;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_dialog_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_sure:
                back();
                dismiss();
                break;

        }
    }


}
