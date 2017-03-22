package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/20 14:20
 */

public class RefreshLoadingAdapter extends BaseRecycleRefreshOrLoadingMoreAdapter<String> {
    public RefreshLoadingAdapter(Context context, List<String> list, RecyclerView rlv_all) {
        super(context, list, rlv_all);
    }

    @Override
    protected void onBindViewHolderLoadingMore(RecyclerView.ViewHolder holder, int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.tv_data.setText(list.get(position));
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolderLoadingMore(ViewGroup parent, int viewType) {
        return new BaseHolder(inflater.inflate(R.layout.adapter_refresh_loading, parent, false));
    }


    public class BaseHolder extends RecyclerView.ViewHolder {

        TextView tv_data;

        public BaseHolder(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
        }
    }

}
