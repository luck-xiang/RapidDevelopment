package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.kexiang.function.utils.LogUtils;
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
        title_name.setText("下拉刷新上拉加载");
        initRadio();
        initView();

    }


    @Override
    protected void initView() {


    }

    protected RecyclerView rlv_all;
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
        rlv_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                LogUtils.toE("onTouch", "onTouch:" + event.getAction());

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    LogUtils.toE("onTouch",
                            "getChildCount:" + rlv_all.getLayoutManager().getChildCount());
                }

                return false;
            }
        });
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
                    recycleAdapter.notifyData();

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
                    recycleAdapter.notifyData();

                }
            }, 2000);
        }

    }


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
//Rxjava写法
//    Gson gson = new Gson();
//    @Override
//    protected void onRefreshLoadingListener(final int type) {
//
//        Observable.create(new ObservableOnSubscribe<List<OrderSingleShowBean>>() {
//            @Override
//            public void subscribe(ObservableEmitter<List<OrderSingleShowBean>> e)
//                    throws Exception {
//
//                List<OrderSingleShowBean> singleBeenList = null;
//                if (type == REFRESH) {
//                    page = 1;
//                    singleBeenList = daoOrderSingle.select(page, PAGE_SIZE, gson);
//                }
//                else if (type == LOADING) {
//                    page++;
//                    singleBeenList = daoOrderSingle.select(page, PAGE_SIZE, gson);
//                }
//
//                e.onNext(singleBeenList);
//            }
//        })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new DefaultObserver<List<OrderSingleShowBean>>() {
//                    @Override
//                    public void onNext(List<OrderSingleShowBean> value) {
//                        initRefreshAndLoading();
//                        if (type == BaseRefreshLoadingFragment.REFRESH) {
//                            pl_header.stopRefresh();
//                            recycleData.clear();
//                            recycleData.addAll(value);
//                        }
//                        else if (type == BaseRefreshLoadingFragment.LOADING) {
//                            recycleData.addAll(value);
//                        }
//                        if (value.size() != PAGE_SIZE) {
//                            recycleAdapter.noMoreData();
//                        }
//                        else {
//                            recycleAdapter.initLoading();
//                        }
//
//                        recycleAdapter.notifyData();
//                        LogUtils.toE("recycleData", "recycleData:" + recycleData.size());
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
//                        pl_header.stopRefresh();
//                        recycleAdapter.initLoading();
//                        if (type == LOADING) {
//                            page--;
//                        }
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
//
//    }


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
//                            recycleAdapter.notifyData();
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
//                                recycleAdapter.notifyData();
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


}
