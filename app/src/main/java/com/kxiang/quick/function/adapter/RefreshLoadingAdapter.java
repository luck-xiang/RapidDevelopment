package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.own.DragRightViewLayout;
import com.kexiang.function.view.own.DragRightViewUtils;
import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/20 14:20
 */

public class RefreshLoadingAdapter extends BaseRecycleRefreshOrLoadingMoreAdapter<String> {

    DragRightViewUtils dragRightViewUtils;

    public RefreshLoadingAdapter(Context context, List<String> list, RecyclerView rlv_all) {
        super(context, list, rlv_all);
        dragRightViewUtils = new DragRightViewUtils();
    }

    @Override
    protected void onBindViewHolderLoadingMore(RecyclerView.ViewHolder holder, final int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.tv_data.setText(list.get(position));
//        baseHolder. tv_data.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogUtils.toE("onTouch", "BaseHolder:" +position+":"+ event.getAction());
//                return true;
//            }
//        });
    }

    public void notifyData() {
        if (dragRightViewUtils.getShowItem() != null)
            dragRightViewUtils.getShowItem().delete();
        notifyDataSetChanged();
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolderLoadingMore(ViewGroup parent, int viewType) {

        return new BaseHolder(inflater.inflate(R.layout.adapter_refresh_loading, parent, false));
    }


    public class BaseHolder extends RecyclerView.ViewHolder {

        TextView tv_data;
        DragRightViewLayout drag_right;


        public BaseHolder(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            drag_right = (DragRightViewLayout) itemView.findViewById(R.id.drag_right);
            drag_right.setSingleShow(dragRightViewUtils);

        }
    }

}
