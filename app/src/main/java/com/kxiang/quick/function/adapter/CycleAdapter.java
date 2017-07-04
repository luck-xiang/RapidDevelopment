package com.kxiang.quick.function.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.kexiang.function.view.cycle.LoopCycleLayout;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/29 10:46
 */

public class CycleAdapter extends LoopCycleLayout.Adapter {
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public LoopCycleLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(LoopCycleLayout.ViewHolder holder, int position) {

    }

    public class ViewHolder extends LoopCycleLayout.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }


}
