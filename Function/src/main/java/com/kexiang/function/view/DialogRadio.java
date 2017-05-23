package com.kexiang.function.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kexiang.function.base.BaseDialogFragment;
import com.kexiang.function.view.recycleview.BaseRecycleRadioAdapter;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.function.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/8 14:00
 */

public class DialogRadio extends BaseDialogFragment implements View.OnClickListener {


    public static DialogRadio newInstance() {
        DialogRadio check = new DialogRadio();
        // Supply num input as an argument.
//        Bundle args = new Bundle();
//        args.putSerializable("check", bean);
//        check.setArguments(args);
        return check;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {


        }

    }

    @Override
    protected int getContentView() {
        return R.layout.fun_dialog_radio;
    }

    @Override
    protected void onActivityCreatedBase(Bundle savedInstanceState) {
        layoutParams(0.8f, 0.35f);
        initSelectRecycleView();
    }


    private RecyclerView rlv_select;
    private DialogRadioAdapter dialogRadioAdapter;
    private List<String> data;


    private void initSelectRecycleView() {
        data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add("" + i);
        }
        rlv_select = (RecyclerView) getView().findViewById(R.id.rlv_select);
        dialogRadioAdapter = new DialogRadioAdapter(getContext(), data, rlv_select,
                BaseRecycleRadioAdapter.Radio_Select_No);

        rlv_select.setAdapter(dialogRadioAdapter);
        rlv_select.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv_select.addItemDecoration(
                new RecycleDividerItemLinear(getContext(),
                        RecyclerView.VERTICAL, getResources().getColor(R.color.color_eeeeee))
        );
        getView().findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
        getView().findViewById(R.id.tv_dialog_sure).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.tv_dialog_cancel) {
            dismiss();

        }
        else if (i == R.id.tv_dialog_sure) {

            dismiss();

        }
    }


}
