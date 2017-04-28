package com.kxiang.quick.base;

import android.support.v4.app.Fragment;

import com.kexiang.function.view.recycleview.BaseRecycleRefreshOrLoadingMoreAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;
import com.ybao.pullrefreshview.layout.BaseHeaderView;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/30 9:59
 */

public abstract class BaseRefreshLoadingFragment extends Fragment {
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

    protected void stopRefresh() {
        refreshStatus = false;
    }

    protected void stopLoadingMore() {
        loadingMoreStatus = false;
    }


    protected BaseHeaderView pl_header;

    protected void initRecycle(BaseRecycleRefreshOrLoadingMoreAdapter adapter) {
        stopLoadingMore();
        pl_header = (BaseHeaderView) getView().findViewById(R.id.pl_header);
        setRefresh();
        setLoading(adapter);
        pl_header.post(new Runnable() {
            @Override
            public void run() {
                pl_header.startRefresh();
            }
        });
    }

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


    protected abstract void onRefreshLoadingListener(int type);
}
