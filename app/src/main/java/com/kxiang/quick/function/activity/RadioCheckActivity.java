package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kexiang.function.view.DialogRadio;
import com.kexiang.function.view.OnDialogBackListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.function.view.check.CheckBean;
import com.kxiang.quick.function.view.check.DialogCheck;

import java.util.ArrayList;
import java.util.List;

public class RadioCheckActivity extends BaseActivity implements View.OnClickListener {


    private TextView et_number;
    private List<String> checkList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_check);
        initStatusBarColor();
        initTitle();
        initView();
        title_name.setText("单选,多选,定项选择");
        et_number = (TextView) findViewById(R.id.et_number);
        checkList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            checkList.add("数据：" + i);
        }
    }

    @Override
    protected void initView() {

    }

    /**
     * 限制选择的个数
     */
    private int selectLimit = 1;
    /**
     * 当前已选中的个数
     */
    private int selectLimitChange = 0;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                selectLimit = Integer.parseInt(et_number.getText().toString());
                DialogCheck dialogCheck = DialogCheck.newInstance(getCheckBean(checkList), "店铺选择", selectLimit, selectLimitChange);
                dialogCheck.show(getSupportFragmentManager(), "check");

                dialogCheck.setOnDialogBackListener(new OnDialogBackListener<
                        List<CheckBean.CheckItemBean>>() {
                    @Override
                    public void onDialogBack(List<CheckBean.CheckItemBean> back) {

                    }
                });

                break;
            case R.id.btn_2:
                DialogRadio DialogRadio=new DialogRadio();
                DialogRadio.show(getSupportFragmentManager(),"radio");
                break;

        }
    }


    private CheckBean getCheckBean(List<String> checkList) {
        CheckBean checkBean = new CheckBean();
        checkBean.setTitle("");
        List<CheckBean.CheckItemBean> checkItemBeanList = new ArrayList<>();

        for (int i = 0; i < checkList.size(); i++) {
            CheckBean.CheckItemBean bean = new CheckBean.CheckItemBean();
            bean.setType("" + i);
            bean.setSelect(false);
            bean.setName(checkList.get(i));
            checkItemBeanList.add(bean);

        }
        checkBean.setItemBeen(checkItemBeanList);


        return checkBean;
    }

}
