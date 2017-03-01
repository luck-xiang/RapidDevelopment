package com.kxiang.quick.function.view.rlv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/13 13:12
 */
public abstract class BaseRecycleViewRadioAdapter extends BaseRecycleViewAdapter {

    public static final int Select_No = -1;
    public static final int Select_First = 0;

    protected int selectPosition = -1;

    public BaseRecycleViewRadioAdapter(Context context, List list, int selectDefault) {
        super(context, list);
        selectPosition = selectDefault;
    }

    protected RecyclerView recyclerView;

    public void setRecycleView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public void notifyCleanSelect() {
        selectPosition = -1;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int selectPosition) {
        this.selectPosition = selectPosition;
        notifyDataSetChanged();
    }
}
