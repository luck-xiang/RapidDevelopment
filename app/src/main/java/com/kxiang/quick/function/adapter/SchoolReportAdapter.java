package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kexiang.function.view.recycleview.BaseRecycleViewHolder;
import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:OneCardSchool
 * 创建人:kexiang
 * 创建时间:2017/7/3 16:27
 */

public class SchoolReportAdapter extends BaseRecycleRefreshOrLoadingMoreAdapter<SchoolReportAdapter.Score> {

    public SchoolReportAdapter(Context context, List<Score> list,
                               RecyclerView rlv_all) {
        super(context, list, rlv_all);
    }


    @Override
    protected void onBindViewHolderLoadingMore(ViewHolder holder, int position) {

        if (getItemViewType(position) == Score.TITLE) {
            ScoreGropViewHolder gropViewHolder = (ScoreGropViewHolder) holder;
            gropViewHolder.setData(list, position);
        }
    }

    @Override
    protected ViewHolder onCreateViewHolderLoadingMore(ViewGroup parent, int viewType) {
        if (viewType == Score.TITLE) {
            return new ScoreGropViewHolder(inflater.inflate(R.layout.adatper_group, parent, false));
        }
        else {
            return new ScoreItemViewHolder(inflater.inflate(R.layout.adatper_group_item, parent, false));
        }


    }


    @Override
    public int getItemViewType(int position) {

        if (position == list.size()) {
            return super.getItemViewType(position);
        }
        else if (list.get(position).getType() == Score.TITLE) {
            return Score.TITLE;
        }
        else {
            return Score.ITEM;
        }
    }

    public class ScoreGropViewHolder extends BaseRecycleViewHolder<SchoolReportAdapter.Score> {

        private LinearLayout ll_title;

        
        public ScoreGropViewHolder(View itemView) {
            super(itemView);
            ll_title = (LinearLayout) itemView.findViewById(R.id.ll_title);
            ll_title.setOnClickListener(this);

        }

        @Override
        public void showData(Score showData, int position) {
            ll_title.setSelected(showData.isStatus());
        }

       

        @Override
        public void onClick(View v) {
            ll_title.setSelected(!ll_title.isSelected());

            
            showData.setStatus(ll_title.isSelected());
            if (onRecycleItemSelectListener != null) {
                onRecycleItemSelectListener.OnRecycleItemSelect(ll_title, showData.getGroupPosition());
            }


        }
    }

    public class ScoreItemViewHolder extends RecyclerView.ViewHolder {

        public ScoreItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class Score {
        public static final int TITLE = 1;
        public static final int ITEM = 2;
        private int type;
        private int groupPosition;
        private int titleNumber;
        private int itemNumber;
        private boolean status;

        public boolean isStatus() {
            return status;
        }

        public void setStatus(boolean status) {
            this.status = status;
        }

        public int getGroupPosition() {
            return groupPosition;
        }

        public void setGroupPosition(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public int getTitleNumber() {
            return titleNumber;
        }

        public void setTitleNumber(int titleNumber) {
            this.titleNumber = titleNumber;
        }

        public int getItemNumber() {
            return itemNumber;
        }

        public void setItemNumber(int itemNumber) {
            this.itemNumber = itemNumber;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
