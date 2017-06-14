package com.kxiang.quick.function.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.news.bean.NewsContentBean;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/20 14:20
 */

public class NewsAdapter extends BaseRecycleRefreshOrLoadingMoreAdapter<NewsContentBean.ItemBean> {
    public NewsAdapter(Context context, List<NewsContentBean.ItemBean> list, RecyclerView rlv_all) {
        super(context, list, rlv_all);
    }

    @Override
    protected void onBindViewHolderLoadingMore(RecyclerView.ViewHolder holder, final int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.tv_data.setText(list.get(position).getName());

        if (list.get(position).getNumbers()>0){
            baseHolder.tv_numbers.setText(list.get(position).getNumbers()+"");
        }else {
            baseHolder.tv_numbers.setText("");
        }

        baseHolder.initData(position);
//        baseHolder. tv_data.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                LogUtils.toE("onTouch", "BaseHolder:" +position+":"+ event.getAction());
//                return true;
//            }
//        });
    }

    @Override
    protected RecyclerView.ViewHolder onCreateViewHolderLoadingMore(ViewGroup parent, int viewType) {

        return new BaseHolder(inflater.inflate(R.layout.adapter_refresh_loading, parent, false));
    }


    public class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_data;
        TextView tv_numbers;

        int position;

        private void initData(int position) {
            this.position = position;
        }

        public BaseHolder(View itemView) {
            super(itemView);
            tv_data = (TextView) itemView.findViewById(R.id.tv_data);
            tv_numbers = (TextView) itemView.findViewById(R.id.tv_numbers);

            itemView.findViewById(R.id.tv_add).setOnClickListener(this);
            itemView.findViewById(R.id.tv_sub).setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if (onRecycleItemSelectListener != null)
                onRecycleItemSelectListener.OnRecycleItemSelect(v, position);
        }
    }

}
