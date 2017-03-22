package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.List;

/**
 * 项目名称:growing
 * 创建人:kexiang
 * 创建时间:2016/8/29 16:28
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter {

    protected LayoutInflater inflater;
    protected List<T> list;

    protected OnRecycleItemClickListener onRecycleItemSelectListener;


    public void setOnRecycleItemSelectListener(OnRecycleItemClickListener onRecycleItemSelectListener) {
        this.onRecycleItemSelectListener = onRecycleItemSelectListener;
    }

    public BaseRecycleAdapter(Context context, List<T> list) {
        inflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public List<T> getList() {
        return list;
    }
}
