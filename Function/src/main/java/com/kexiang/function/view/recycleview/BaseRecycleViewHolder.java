package com.kexiang.function.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 10:12
 */

public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }

    private T data;
    private int position;

    protected abstract void setData(T data, int position);


}
