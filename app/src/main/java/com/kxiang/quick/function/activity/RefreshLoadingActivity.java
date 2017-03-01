package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRecycleRefreshLoadingActivity;
import com.kxiang.quick.function.adapter.TestStringAdapter;
import com.kxiang.quick.function.view.rlv.OnRecycleItemSelectListener;
import com.kxiang.quick.function.view.rlv.decoration.RecycleItemDivider;

import java.util.ArrayList;
import java.util.List;

public class RefreshLoadingActivity extends BaseRecycleRefreshLoadingActivity implements CompoundButton.OnCheckedChangeListener {
    @Override
    protected int getContentView() {
        return R.layout.activity_refresh_loading;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        tv_title_name.setText("下拉刷新上拉加载");
        initRadio();

    }
    RadioButton rb_1;
    RadioButton rb_2;
    private void initRadio(){
        rb_1= (RadioButton) findViewById(R.id.rb_1);
        rb_1.setOnCheckedChangeListener(this);
        rb_1.setChecked(true);

        rb_2= (RadioButton) findViewById(R.id.rb_2);
        rb_2.setOnCheckedChangeListener(this);
    }

    private List<String> allData;

    @Override
    protected void initRecycle() {
        allData = new ArrayList<>();
        allAdapter = new TestStringAdapter(this, allData);
        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {

            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        setRecycleBase(allAdapter, linearLayoutManager,
                new RecycleItemDivider(this,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.color_dddddd)));
    }

    private int pageSize = SIZE;

    @Override
    protected void netWorkGetData(final int type) {

        if (type == RefreshId) {
            page = 1;
        }
        else if (type == LoadMoreId) {
            page++;
        }

        rlv_all.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopRefrsh();
                stopLoad();
                List<String> value = getAllData(pageSize);
                if (type == RefreshId) {

                    noMessage(value.size());
                    allData.clear();
                    allData.addAll(value);
                    allAdapter.notifyDataSetChanged();


                }
                else if (type == LoadMoreId) {
                    noMessage(value.size());
                    allData.addAll(value);
                    allAdapter.notifyDataSetChanged();
                }
            }
        }, 5000);
    }

    private List<String> getAllData(int size) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            data.add("数据：" + (allData.size() + i));
        }
        return data;
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_1:
                    pageSize = SIZE;
                    break;
                case R.id.rb_2:
                    pageSize = SIZE - 1;
                    break;

            }
        }
    }
}
