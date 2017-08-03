package com.kexiang.function.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 10:12
 */

public abstract class BaseRecycleViewHolder<T> extends RecyclerView.ViewHolder implements View.OnClickListener {

    public BaseRecycleViewHolder(View itemView) {
        super(itemView);
    }

    protected T showData;
    protected int position;

    public void setData(List<T> data, int position) {
        this.showData = data.get(position);
        this.position = position;
        showData(showData, position);
    }

    public abstract void showData(T showData, int position);
}
