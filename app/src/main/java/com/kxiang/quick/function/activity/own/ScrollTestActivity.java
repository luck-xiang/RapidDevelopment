package com.kxiang.quick.function.activity.own;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;

import java.util.ArrayList;
import java.util.List;

public class ScrollTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_test);
        initView();
    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    protected RecyclerView rlv_all;
    RefreshLoadingAdapter recycleAdapter;
    List<String> recycleData;

    private void initRecycleView() {
        recycleData = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            recycleData.add("数据：" + i);
        }
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        recycleAdapter = new RefreshLoadingAdapter(this, recycleData, rlv_all);
        rlv_all.addItemDecoration(new RecycleDividerItemLinear(
                        thisActivity,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.color_dddddd)
                )
        );
        rlv_all.setLayoutManager(new LinearLayoutManager(thisActivity));
        rlv_all.setAdapter(recycleAdapter);
    }
}
