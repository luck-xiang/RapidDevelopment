package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseExpandableBean;
import com.kexiang.function.view.recycleview.BaseExpandableRecycleAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.function.activity.ExpandableActivity;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/8/1 9:42
 */

public class ExpandableRecycleAdapter extends BaseExpandableRecycleAdapter<ExpandableActivity.GroupBean> {
    public ExpandableRecycleAdapter(Context context, List<ExpandableActivity.GroupBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == BaseExpandableBean.GROUP) {
            return new GropViewHolder(inflater.inflate(R.layout.adatper_group, parent, false));
        }
        else {
            return new ItemViewHolder(inflater.inflate(R.layout.adatper_group_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == BaseExpandableBean.GROUP) {
            GropViewHolder gropViewHolder = (GropViewHolder) holder;
            gropViewHolder.setData(list, position);
        }
        else {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.setData(list, position);
        }
    }

    public class GropViewHolder extends BaseExpandableViewHolder<ExpandableActivity.GroupBean> {

        private TextView tv_group;

        @Override
        public void showGroupAndItemBean(ExpandableActivity.GroupBean groupBean,
                                         BaseExpandableBean expandableBean) {
            tv_group.setText("组别" + groupBean.getGroup());
        }

        public GropViewHolder(View itemView) {
            super(itemView);
            tv_group = (TextView) itemView.findViewById(R.id.tv_group);
            tv_group.setOnClickListener(this);
            setGroupSelectView(tv_group);
        }

        @Override
        public void onClick(View v) {
            setGroupSelecltViewOnClick();
        }
    }

    public class ItemViewHolder extends BaseExpandableViewHolder<ExpandableActivity.GroupBean> {


        private TextView tv_item;

        @Override
        public void showGroupAndItemBean(ExpandableActivity.GroupBean groupBean, BaseExpandableBean expandableBean) {
            tv_item.setText("组别：" + groupBean.getGroup() +
                    "成员：" + groupBean.getItemBeanList().get(expandableBean.getItemNumber()).getItem());
        }

        public ItemViewHolder(View itemView) {
            super(itemView);
            tv_item = (TextView) itemView.findViewById(R.id.tv_item);
        }

        @Override
        public void onClick(View v) {

            if (onExpandableClickListener != null) {
                onExpandableClickListener.onExpandableClick(v, expandableBean);
            }
        }
    }
}
