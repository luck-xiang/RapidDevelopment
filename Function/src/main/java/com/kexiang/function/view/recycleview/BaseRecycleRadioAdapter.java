package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/13 13:12
 */
public abstract class BaseRecycleRadioAdapter<T> extends BaseRecycleAdapter {


    public static final int Radio_Select_No = -1;
    protected int selectPosition = Radio_Select_No;

    protected RecyclerView recyclerView;

    public BaseRecycleRadioAdapter(Context context, List<T> list, RecyclerView recyclerView, int selectDefault) {
        super(context, list);
        selectPosition = selectDefault;
        this.recyclerView = recyclerView;
    }


    public void notifyCleanSelect() {
        selectPosition = -1;
        notifyDataSetChanged();
    }

//    public void setSelectPosition(int selectPosition) {
//        this.selectPosition = selectPosition;
//        notifyDataSetChanged();
//    }


    public int getSelectPosition() {
        return selectPosition;
    }

    public class RadioViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        public void setPosition(int position) {
            this.position = position;
            if (selectPosition == position) {
                radio_select.setSelected(true);
            }
            else {
                radio_select.setSelected(false);
            }
        }

        protected int position;
        View radio_select;

        protected void setRaidoView(View view) {
            radio_select = view;
            radio_select.setOnClickListener(this);
        }

        public RadioViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {
//            Log.e("recyclerView", "" + recyclerView);
            if (position >= 0 && recyclerView != null) {
                RadioViewHolder item = (RadioViewHolder) recyclerView.findViewHolderForLayoutPosition(selectPosition);
                Log.e("item", "" + item);
                if (item != null) {//还在屏幕里
                    item.radio_select.setSelected(false);
                }
                else {
                    notifyItemChanged(selectPosition);
                }
            }
            radio_select.setSelected(true);
            selectPosition = position;
        }
    }
}
