package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.bean.ClassBean;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/1/17 11:29
 */

public class OnlyRefreshAdapter extends BaseRecycleAdapter<ClassBean> {

    public OnlyRefreshAdapter(Context context, List<ClassBean> list) {
        super(context, list);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(inflater.inflate(R.layout.quick_adapter_only_refresh, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.notyfyDataItem(list.get(position), position);
    }

    private class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name_show;

        BaseHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.ll_all).setOnClickListener(this);
            tv_name_show = (TextView) itemView.findViewById(R.id.tv_name_show);

        }

        private int position;

        void notyfyDataItem(ClassBean bean, int position) {
            this.position = position;
            tv_name_show.setText(bean.getName());
        }

        @Override
        public void onClick(View v) {
            if (onRecycleItemSelectListener != null) {
                onRecycleItemSelectListener.OnRecycleItemSelect(v, position);
            }
        }


    }
}
