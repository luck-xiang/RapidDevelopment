package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRefreshLoadingActivity;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;

import java.util.ArrayList;
import java.util.List;

public class RefreshLoadingActivity extends BaseRefreshLoadingActivity implements CompoundButton.OnCheckedChangeListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_loading);
        initStatusBarColor();
        initTitle();
        tv_title_name.setText("下拉刷新上拉加载");
        initRadio();
        initRecycleView();
        initView();

    }


    @Override
    protected void initView() {


    }

    protected RecyclerView rlv_all;
    RefreshLoadingAdapter recycleAdapter;
    List<String> recycleData;
    private int position = 1;

    private void initRecycleView() {
        recycleData = new ArrayList<>();
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        recycleAdapter = new RefreshLoadingAdapter(this, recycleData, rlv_all);
        initRecycle(recycleAdapter);
        rlv_all.setLayoutManager(new LinearLayoutManager(thisActivity));
        rlv_all.setAdapter(recycleAdapter);
    }


    @Override
    protected void onRefreshLoadingListener(int type) {

        if (type == BaseRefreshLoadingActivity.REFRESH) {
            pl_header.postDelayed(new Runnable() {

                @Override
                public void run() {
                    initRefreshAndLoading();
                    recycleData.clear();

                    for (int i = 0; i < 3; i++) {
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



//    private void setRefresh() {
//        pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
//            @Override
//            public void onRefresh(BaseHeaderView baseHeaderView) {
//                stopLoadingMore();
//                if (refreshStatus) {
//                    pl_header.postDelayed(new Runnable() {
//
//                        @Override
//                        public void run() {
//                            initRefreshAndLoading();
//                            recycleData.clear();
//
//                            for (int i = 0; i < 20; i++) {
//                                recycleData.add("数据:" + position);
//                                position++;
//                            }
//                            pl_header.stopRefresh();
//                            if (pageSize == PAGE_SIZE) {
//                                recycleAdapter.initLoading();
//                            }
//                            else {
//                                recycleAdapter.noMoreData();
//                            }
//                            recycleAdapter.notifyDataSetChanged();
//
//                        }
//                    }, 5000);
//
//                }
//                else {
//                    pl_header.stopRefresh();
//                }
//
//            }
//        });
//    }
//
//    private void setLoading() {
//
//        recycleAdapter.setOnLoadingMoreListener(new RefreshLoadingAdapter.OnLoadingMoreListener() {
//            @Override
//            public void onLoadingMore() {
//
//                Log.e("下拉加载", "开始:" + loadingMoreStatus);
//                if (loadingMoreStatus) {
//                    stopRefresh();
//                    rlv_all.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                            initRefreshAndLoading();
//                            Log.e("下拉加载", "结束");
//                            if (pageSize != PAGE_SIZE) {
//                                recycleAdapter.noMoreData();
//                            }
//                            else {
//
//                                recycleAdapter.initLoading();
//                                for (int i = 0; i < 5; i++) {
//                                    recycleData.add("数据:" + position);
//                                    position++;
//                                }
//                                recycleAdapter.notifyDataSetChanged();
//                            }
//
//
//                        }
//                    }, 2000);
//
//
//                }
//                else {
//                    recycleAdapter.initLoading();
//                    Log.e("下拉加载", "结束1");
//
//                }
//
//
//            }
//        });
//
//    }

    RadioButton rb_1;
    RadioButton rb_2;

    private void initRadio() {
        rb_1 = (RadioButton) findViewById(R.id.rb_1);
        rb_1.setOnCheckedChangeListener(this);
        rb_1.setChecked(true);

        rb_2 = (RadioButton) findViewById(R.id.rb_2);
        rb_2.setOnCheckedChangeListener(this);
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            switch (buttonView.getId()) {
                case R.id.rb_1:
                    pageSize = PAGE_SIZE;
                    break;
                case R.id.rb_2:
                    pageSize = PAGE_SIZE - 1;
                    break;

            }
        }
    }
}
