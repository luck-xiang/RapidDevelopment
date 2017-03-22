package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.util.List;

/**
 * 项目名称:growing
 * 创建人:kexiang
 * 创建时间:2016/8/29 16:28
 */
public abstract class BaseRecycleRefreshOrLoadingMoreAdapter<T> extends BaseRecycleAdapter<T> {

    private int lastItemPosition;

    public BaseRecycleRefreshOrLoadingMoreAdapter(Context context,
                                                  List<T> list,
                                                  RecyclerView rlv_all) {
        super(context, list);

        rlv_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    LogUtils.toE("recyclerView", "lastItemPosition:  " + lastItemPosition);
                    if (lastItemPosition + 1 == getItemCount()) {
                        startLoading();
                    }
                }
                return false;
            }
        });

        rlv_all.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearManager =
                        (LinearLayoutManager) recyclerView.getLayoutManager();
                //获取最后一个可见view的位置
                lastItemPosition = linearManager.findLastVisibleItemPosition();

                LogUtils.toE("recyclerView", "lastItemPosition:  " + lastItemPosition);
                if (lastItemPosition + 1 == getItemCount()) {
                    startLoading();
                }
            }
        });

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == LOADING) {
            return new HolderLoading(inflater.inflate(R.layout.fun_loading_footer, parent, false));
        }
        else {
            return onCreateViewHolderLoadingMore(parent, viewType);
        }

    }


    private HolderLoading holderLoading;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == LOADING) {
            holderLoading = (HolderLoading) holder;
            holderLoading.tv_add_footer.setText(stringStatus);
        }
        else {
            onBindViewHolderLoadingMore(holder, position);
        }
    }

    private int LOADING = 101;

    @Override
    public int getItemViewType(int position) {

        if (position == list.size()) {
            return LOADING;
        }
        else {
            return super.getItemViewType(position);
        }

    }

    protected abstract void onBindViewHolderLoadingMore(RecyclerView.ViewHolder holder, int position);

    protected abstract RecyclerView.ViewHolder onCreateViewHolderLoadingMore(ViewGroup parent, int viewType);


    @Override
    public int getItemCount() {
        return list.size() + 1;
    }


    public interface OnLoadingMoreListener {
        void onLoadingMore();
    }

    private OnLoadingMoreListener onLoadingMoreListener;

    public void setOnLoadingMoreListener(OnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }

    private boolean status = false;

    /**
     * 正在刷新状态
     */
    private void startLoading() {
        holderLoading.ll_footer.setEnabled(false);
        LogUtils.toE("下拉加载", "status:" + status);
        if (onLoadingMoreListener != null && !status && holderLoading != null) {
            status = true;
            holderLoading.startLoadingShow();
            holderLoading.pb_footer.setVisibility(View.VISIBLE);
            onLoadingMoreListener.onLoadingMore();

        }
    }

    /**
     * 没有更多状态
     */
    public void noMoreData() {
        if (holderLoading != null) {
            holderLoading.pb_footer.setVisibility(View.GONE);
            stringStatus = "没有更多了";
            holderLoading.stopName(stringStatus);
            //无法点击
            holderLoading.ll_footer.setEnabled(false);
        }
    }

    private String stringStatus = "点击加载";

    /**
     * 初始化加载跟多，即下拉或者点击查看更多状态
     */
    public void initLoading() {
        status = false;
        if (holderLoading != null) {
            holderLoading.pb_footer.setVisibility(View.GONE);
            stringStatus = "下拉或者点击查看更多";
            holderLoading.stopName(stringStatus);
            //可以点击
            holderLoading.ll_footer.setEnabled(true);
        }
    }


    public class HolderLoading extends RecyclerView.ViewHolder implements View.OnClickListener {

        void startLoadingShow() {
            stringStatus = "加载中……";
            tv_add_footer.setText(stringStatus);
        }

        void stopName(String name) {
            tv_add_footer.setText(name);
        }

        TextView tv_add_footer;
        LinearLayout ll_footer;
        ProgressBar pb_footer;

        public HolderLoading(View itemView) {
            super(itemView);
            tv_add_footer = (TextView) itemView.findViewById(R.id.tv_add_footer);
            ll_footer = (LinearLayout) itemView.findViewById(R.id.ll_footer);
            pb_footer = (ProgressBar) itemView.findViewById(R.id.pb_footer);
            ll_footer.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            startLoading();
        }
    }

}
