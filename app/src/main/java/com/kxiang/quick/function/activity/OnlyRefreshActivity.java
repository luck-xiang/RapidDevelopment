package com.kxiang.quick.function.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRecycleRefreshActivity;
import com.kxiang.quick.bean.ClassBean;
import com.kxiang.quick.function.adapter.OnlyRefreshAdapter;
import com.kxiang.quick.function.view.rlv.OnRecycleItemSelectListener;
import com.kxiang.quick.function.view.rlv.decoration.RecycleItemDivider;

import java.util.ArrayList;
import java.util.List;

public class OnlyRefreshActivity extends BaseRecycleRefreshActivity {

    @Override
    protected int getContentView() {
        return R.layout.quick_activity_only_refresh;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        tv_title_name.setText("下拉刷新上拉加载");
    }

    private List<ClassBean> classBeanList;
    private OnlyRefreshAdapter allAdapter;

    @Override
    protected void initRecycle() {
        classBeanList = new ArrayList<>();
        allAdapter = new OnlyRefreshAdapter(this, classBeanList);
        setRecycleBase(allAdapter, new LinearLayoutManager(this),
                new RecycleItemDivider(this,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.refresh_dddddd)));
        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                startActivity(new Intent(thisActivity, classBeanList.get(position).getTheClass()));
            }
        });

    }

    private void addData() {
        classBeanList.add(getClassBean("只带下拉刷新功能页面", OnlyRefreshActivity.class));
    }

    private ClassBean getClassBean(String name, Class<?> theClass) {
        ClassBean bean = new ClassBean();
        bean.setName(name);
        bean.setTheClass(theClass);
        return bean;
    }

    @Override
    protected void netWorkGetData(int type) {

        rlv_all.postDelayed(new Runnable() {
            @Override
            public void run() {
                addData();
                allAdapter.notifyDataSetChanged();
                stopRefrsh();
            }
        }, 2000);
    }

}
