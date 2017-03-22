package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/13 13:12
 */
public abstract class BaseRecycleRadioAdapter extends BaseRecycleAdapter {

    public static final int Select_No = -1;
    public static final int Select_First = 0;

    protected int selectPosition = -1;

    public BaseRecycleRadioAdapter(Context context, List list, int selectDefault) {
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
