package com.kxiang.quick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/13 11:22
 */

public abstract class BaseRecycleViewFragment extends BaseFragment {
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
    }

    protected RecyclerView rlv_all;

    protected abstract void initRecycleView();

    //初始化Recycleview
    protected void setRecycle(RecyclerView.Adapter adapter,
                              RecyclerView.LayoutManager manager,
                              RecyclerView.ItemDecoration itemDecoration) {
        rlv_all = (RecyclerView) getView().findViewById(R.id.rlv_all);
        if (manager != null) {
            rlv_all.setLayoutManager(manager);
        }
        else {
            rlv_all.setLayoutManager(new LinearLayoutManager(getActivity()));
        }

        rlv_all.setAdapter(adapter);
        if (itemDecoration != null)
            rlv_all.addItemDecoration(itemDecoration);
    }

    protected RecyclerView.ItemDecoration getItemDefaultDecoration() {

        return new RecycleDividerItemLinear(getActivity(), LinearLayoutManager.VERTICAL,
                getResources().getColor(R.color.color_dddddd));
    }
}
