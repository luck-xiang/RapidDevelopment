package com.kxiang.quick.base;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import com.kxiang.quick.R;


/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/25 14:13
 */

public abstract class BaseRecyclePop extends PopupWindow {
    /**
     * 返回对应Activity的布局文件的id
     *
     * @return
     */
    protected abstract int getContentViewId();

    /**
     * 返回对应Activity的布局文件的id
     *
     * @return
     */
    protected abstract void onCreate(View view,Activity context);

    protected RecyclerView rlv_all;

    public BaseRecyclePop(Activity context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mMenuView = inflater.inflate(getContentViewId(), null);
        //设置RedactPop的View
        this.setContentView(mMenuView);
        rlv_all = (RecyclerView) mMenuView.findViewById(R.id.rlv_all);
        onCreate(mMenuView,context);
    }


    protected void setRecycleBase(RecyclerView.Adapter allAdapter,
                                  RecyclerView.LayoutManager manager,
                                  RecyclerView.ItemDecoration decoration) {
        if (decoration != null) {
            rlv_all.addItemDecoration(decoration);
        }
        rlv_all.setLayoutManager(manager);
        rlv_all.setAdapter(allAdapter);
    }
}
