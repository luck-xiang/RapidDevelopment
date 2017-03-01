package com.kxiang.quick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kxiang.quick.R;


/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/4 15:13
 */

public abstract class BaseRecycleActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        initRecycle();

    }

    protected RecyclerView rlv_all;

    protected void setRecycleBase(RecyclerView.Adapter allAdapter,
                                  RecyclerView.LayoutManager manager,
                                  RecyclerView.ItemDecoration decoration) {
        if (decoration != null)
            rlv_all.addItemDecoration(
                    decoration
            );
        rlv_all.setLayoutManager(manager);
        rlv_all.setAdapter(allAdapter);
    }

    protected abstract void initRecycle();

    protected TextView tv_internet_show;

    /**
     * 有下拉刷新界面的没网络显示
     */
    protected void initInternetShow() {
        tv_internet_show = (TextView) findViewById(R.id.tv_internet_show);
    }
    /**
     * 网络是否开启图示UI
     * 有网时候返回true
     * 没有网络返回false
     */
    protected boolean showInterNetStatus() {

        boolean status = changInternew();

        if (status) {
            if (tv_internet_show != null) {
                tv_internet_show.setVisibility(View.GONE);
            }
        }
        else {
            if (tv_internet_show != null) {
                tv_internet_show.setVisibility(View.VISIBLE);
            }
        }
        return status;
    }


}
