package com.kxiang.quick.function.activity.own;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kexiang.function.view.refresh.HeaderView;
import com.kexiang.function.view.refresh.OnRefreshListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;

import java.util.ArrayList;
import java.util.List;

public class View1Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view1);
        initStatusBarColor();


    }

    @Override
    protected void initView() {
        initRecycleView();
    }

    protected RecyclerView rlv_all;
    protected RecyclerView rlv_other;
    RefreshLoadingAdapter recycleAdapter;
    List<String> recycleData;

    private HeaderView prl_header;


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

        rlv_other = (RecyclerView) findViewById(R.id.rlv_other);
        rlv_other.addItemDecoration(new RecycleDividerItemLinear(
                        thisActivity,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.color_dddddd)
                )
        );
        rlv_other.setLayoutManager(new LinearLayoutManager(thisActivity));
        rlv_other.setAdapter(recycleAdapter);
        prl_header = (HeaderView) findViewById(R.id.prl_header);
        prl_header.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                prl_header.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        prl_header.stopRefreshSuccess();
                    }
                }, 2000);
            }
        });
//        prl_header.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                prl_header.startRefresh();
//            }
//        }, 2000);


    }
}
