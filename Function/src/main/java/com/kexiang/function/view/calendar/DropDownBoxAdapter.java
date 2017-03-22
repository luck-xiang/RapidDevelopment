package com.kexiang.function.view.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleAdapter;
import com.kxiang.function.R;

import java.util.List;


/**
 * 项目名称:Reader
 * 创建人:kexiang
 * 创建时间:2016/9/26 11:39
 */
public class DropDownBoxAdapter extends BaseRecycleAdapter {


    public DropDownBoxAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RadioHolder(inflater.inflate(R.layout.fun_adapter_drop_down_box, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final RadioHolder item = (RadioHolder) holder;

        item.tv_name.setText(list.get(position) + "");
        item.ll_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRecycleItemSelectListener != null) {
                    onRecycleItemSelectListener.OnRecycleItemSelect(item.ll_all, position);
                }

            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    private class RadioHolder extends RecyclerView.ViewHolder {

        public LinearLayout ll_all;
        public TextView tv_name;


        public RadioHolder(View itemView) {
            super(itemView);

            ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
        }
    }

}
