package com.kxiang.quick.base;

import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;
import com.ybao.pullrefreshview.layout.BaseHeaderView;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/20 17:19
 */

public abstract class BaseRefreshLoadingActivity extends BaseRecycleViewActivity {

    protected int PAGE_SIZE = 20;
    protected int pageSize = 20;
    protected final static int REFRESH = 1;
    protected final static int LOADING = 2;

    private boolean loadingMoreStatus = true;
    private boolean refreshStatus = true;

    protected void initRefreshAndLoading() {
        loadingMoreStatus = true;
        refreshStatus = true;
    }

    /**
     * 阻止下拉刷新
     */
    protected void stopRefresh() {
        refreshStatus = false;
    }

    /**
     * 阻止上拉加载更多
     */
    protected void stopLoadingMore() {
        loadingMoreStatus = false;
    }


    protected BaseHeaderView pl_header;

    /**
     * 初始化刷新
     *
     * @param adapter
     */
    protected void initRecycle(BaseRecycleRefreshOrLoadingMoreAdapter adapter) {
        stopLoadingMore();
        pl_header = (BaseHeaderView) findViewById(R.id.pl_header);
        setRefresh();
        setLoading(adapter);
        //开始的时候自动刷新
        pl_header.post(new Runnable() {
            @Override
            public void run() {
                pl_header.startRefresh();
            }
        });
    }

    /**
     * 初始化下拉刷新
     */
    protected void setRefresh() {
        pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {
                stopLoadingMore();
                if (refreshStatus) {
                    onRefreshLoadingListener(REFRESH);

                }
                else {
                    pl_header.stopRefresh();
                }

            }
        });

    }

    /**
     * 初始上拉加载更多
     */
    protected void setLoading(final BaseRecycleRefreshOrLoadingMoreAdapter adapter) {

        adapter.setOnLoadingMoreListener(new RefreshLoadingAdapter.OnLoadingMoreListener() {
            @Override
            public void onLoadingMore() {

                if (loadingMoreStatus) {
                    stopRefresh();
                    onRefreshLoadingListener(LOADING);

                }
                else {
                    adapter.initLoading();
                }

            }
        });
    }


    /**
     * 状态返回
     * REFRESH = 1，下拉刷新;
     * LOADING = 2，上拉加载更多;
     *
     * @param type
     */
    protected abstract void onRefreshLoadingListener(int type);
}
