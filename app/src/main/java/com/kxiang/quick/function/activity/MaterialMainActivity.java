package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRefreshLoadingActivity;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;

import java.util.ArrayList;
import java.util.List;

public class MaterialMainActivity extends BaseRefreshLoadingActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    RefreshLoadingAdapter recycleAdapter;
    List<String> recycleData;
    private int position = 1;

    @Override
    public void initRecycleView() {
        recycleData = new ArrayList<>();
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        recycleAdapter = new RefreshLoadingAdapter(this, recycleData, rlv_all);
        setRecycle(recycleAdapter, null, null);
        initRecycle(recycleAdapter);
    }


    @Override
    protected void onRefreshLoadingListener(int type) {

        if (type == BaseRefreshLoadingActivity.REFRESH) {
            pl_header.postDelayed(new Runnable() {

                @Override
                public void run() {
                    initRefreshAndLoading();
                    recycleData.clear();

                    for (int i = 0; i < 20; i++) {
                        recycleData.add("数据:" + position);
                        position++;
                    }
                    pl_header.stopRefresh();
                    if (pageSize == PAGE_SIZE) {
                        recycleAdapter.initLoading();
                    }
                    else {
                        recycleAdapter.noMoreData();
                    }
                    recycleAdapter.notifyDataSetChanged();

                }
            }, 5000);
        }
        else if (type == BaseRefreshLoadingActivity.LOADING) {
            rlv_all.postDelayed(new Runnable() {
                @Override
                public void run() {

                    initRefreshAndLoading();
                    Log.e("下拉加载", "结束");
                    if (pageSize != PAGE_SIZE) {
                        recycleAdapter.noMoreData();
                    }
                    else {

                        recycleAdapter.initLoading();
                        for (int i = 0; i < 5; i++) {
                            recycleData.add("数据:" + position);
                            position++;
                        }
                    }
                    recycleAdapter.notifyDataSetChanged();

                }
            }, 2000);
        }

    }

    @Override
    protected void initView() {

    }

}
