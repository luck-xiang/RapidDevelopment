package com.kxiang.quick.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.R;


/**
 * 项目名称:CallWristWatch
 * 创建人:kexiang
 * 创建时间:2017/6/13 11:03
 */

public abstract class BaseRecycleViewActivity extends BaseActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initRecycleView();
    }

    protected RecyclerView rlv_all;

    protected abstract void initRecycleView();

    /**
     * 初始化Recycleview
     */
    protected void setRecycle(RecyclerView.Adapter adapter,
                              RecyclerView.LayoutManager manager,
                              RecyclerView.ItemDecoration itemDecoration) {
        if (rlv_all == null)
            rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        if (manager != null) {
            rlv_all.setLayoutManager(manager);
        }
        else {
            rlv_all.setLayoutManager(new LinearLayoutManager(this));
        }

        rlv_all.setAdapter(adapter);
        if (itemDecoration != null)
            rlv_all.addItemDecoration(itemDecoration);
    }

    protected RecyclerView.ItemDecoration getItemDefaultDecoration() {

        return new RecycleDividerItemLinear(this, LinearLayoutManager.VERTICAL,
                getResources().getColor(R.color.color_dddddd));
    }
}
