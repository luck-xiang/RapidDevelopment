package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.own.DragRightViewLayout;
import com.kexiang.function.view.own.DragRightViewUtils;
import com.kexiang.function.view.recycleview.BaseRecycleAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.bean.ClassBean;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/1/17 11:29
 */

public class MainAdapter extends BaseRecycleAdapter<ClassBean> {


    private DragRightViewUtils dragRightViewUtils;

    public MainAdapter(Context context, List<ClassBean> list, RecyclerView recyclerView) {
        super(context, list);
//        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
//            @Override
//            public boolean onFling(int velocityX, int velocityY) {
//                LogUtils.toE("MainAdapter", "onFling");
//                if (DragRightViewUtils.getDragRightViewUtils().getShowItem() != null) {
//                    DragRightViewUtils.getDragRightViewUtils().getShowItem().startBack();
//                }
//
//                return false;
//            }
//        });
        dragRightViewUtils = new DragRightViewUtils();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(inflater.inflate(R.layout.quick_adapter_main, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.notyfyDataItem(list.get(position), position);
    }

    private class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_name_show;
        DragRightViewLayout id_drawerlayout;

        BaseHolder(View itemView) {
            super(itemView);
            tv_name_show = (TextView) itemView.findViewById(R.id.tv_name_show);
            id_drawerlayout = (DragRightViewLayout) itemView.findViewById(R.id.id_drawerlayout);
            tv_name_show.setOnClickListener(this);
            itemView.findViewById(R.id.tv_cancle).setOnClickListener(this);
            itemView.findViewById(R.id.tv_delete).setOnClickListener(this);
            id_drawerlayout.setSingleShow(dragRightViewUtils);
        }

        private int position;

        void notyfyDataItem(ClassBean bean, int position) {
            this.position = position;
            tv_name_show.setText(bean.getName());
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.tv_name_show:
                    if (onRecycleItemSelectListener != null) {
                        onRecycleItemSelectListener.OnRecycleItemSelect(v, position);
                    }
                    break;
                case R.id.tv_delete:
//                    id_drawerlayout.startBack();
                    id_drawerlayout.delete();
                    list.remove(position);
                    notifyDataSetChanged();
                    LogUtils.toE("MainAdapter", "tv_delete:");
                    break;
                case R.id.tv_cancle:
                    id_drawerlayout.startBack();
                    LogUtils.toE("MainAdapter", "tv_cancle:");
                    break;

            }
        }


    }
}
