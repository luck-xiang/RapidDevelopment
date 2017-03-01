package com.kxiang.quick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kxiang.quick.R;
import com.ybao.pullrefreshview.layout.BaseHeaderView;


/**
 * 创建人:kexiang
 * 创建时间:2016/11/4 15:13
 * 只执行下拉刷新
 */

public abstract class BaseRecycleRefreshActivity extends BaseRecycleActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInternetShow();
        initRefresh();
    }

    /**
     * 下拉刷新开始
     */
    protected final int RefreshId = 0;
    protected BaseHeaderView pl_header;

    protected void initRefresh() {
        pl_header = (BaseHeaderView) findViewById(R.id.pl_header);
        if (pl_header != null) {
            pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
                @Override
                public void onRefresh(BaseHeaderView baseHeaderView) {

                    if (!showInterNetStatus()) {
                        stopRefrsh();
                    }
                    else {

                        networkingRefreshing();
                        netWorkGetData(RefreshId);


                    }


                }
            });
            pl_header.post(new Runnable() {
                @Override
                public void run() {
                    pl_header.startRefresh();
                }
            });
        }

    }

    /**
     * 网络刷新
     * <BR/>打开页面就进入下拉加载刷新
     * <BR/>RefreshId = 0：下拉刷新
     * <BR/>LoadMoreId = 1:上拉加载更多
     *
     * @param type
     */
    protected abstract void netWorkGetData(int type);

    /**
     * 下拉刷新联网操作
     * <BR/>在实现完成下拉刷新操作后将loadMore置为true上拉加载更多才能执行
     */
    protected void networkingRefreshing() {

    }

    protected void stopRefrsh() {
        if (pl_header != null)
            pl_header.stopRefresh();
    }

}
