package com.kxiang.quick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

import com.kxiang.quick.R;
import com.kxiang.quick.function.view.rlv.BaseRecycleNetAdapter;
import com.kxiang.quick.utils.LogUtils;
import com.ybao.pullrefreshview.layout.BaseHeaderView;


/**
 * 创建人:kexiang
 * 创建时间:2016/11/4 15:13
 * 下拉刷新和上拉加载更多
 */

public abstract class BaseRecycleRefreshLoadingActivity extends BaseRecycleRefreshActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * 每页显示数量
     */
    protected final int SIZE = 3;
    /**
     * 页数
     */
    protected int page = 0;

    /**
     * 上拉加载更多开始
     */
    protected final int LoadMoreId = 1;

    /**
     * 下拉刷新不执行上拉加载
     * <BR/> 当为true的时候表示可以下拉刷新
     */
    protected boolean refresh = true;
    /**
     * 上拉加载不执行下拉刷新
     * <BR/> 当为true的时候表示可以上拉加载更多
     */
    protected boolean loadMore = false;
    protected BaseHeaderView pl_header;
    protected BaseRecycleNetAdapter allAdapter;
    protected LinearLayoutManager linearLayoutManager;

    //初始化刷新，该方法必须在初始化allAdapter之后执行
    @Override
    protected void initRefresh() {
        pl_header = (BaseHeaderView) findViewById(R.id.pl_header);

        if (pl_header != null) {
            pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
                @Override
                public void onRefresh(BaseHeaderView baseHeaderView) {

                    if (!showInterNetStatus()) {
                        stopRefrsh();
                        stopLoad();
                    }
                    else {
                        if (refresh) {
                            loadMore = false;
                            networkingRefreshing();
                            netWorkGetData(RefreshId);
                        }
                        else {
                            pl_header.stopRefresh();
                        }
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


        if (allAdapter != null) {
            allAdapter.setOnLoadingMoreListener(new BaseRecycleNetAdapter.OnLoadingMoreListener() {
                @Override
                public void onLoadingMore() {
                    if (!showInterNetStatus()) {
                        stopRefrsh();
                        stopLoad();
                    }
                    else {
                        if (loadMore) {
                            refresh = false;
                            loadMore = false;
                            networkingLoadingMore();
                            netWorkGetData(LoadMoreId);

                        }
                        else {
                            allAdapter.stopLoadingMore();
                        }


                    }
                }
            });
        }

        if (rlv_all != null) {
            rlv_all.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    //判断是当前layoutManager是否为LinearLayoutManager
                    // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
//                LogUtils.toE("recyclerView", "number:" + (++number));
                    if (layoutManager instanceof LinearLayoutManager
                            && loadMore && !allAdapter.isLoadMoreBoolean()) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
//                    //获取第一个可见view的位置
//                    int firstItemPosition = linearManager.findFirstVisibleItemPosition();
                        //获取最后一个可见view的位置
                        final int lastItemPosition = linearManager.findLastVisibleItemPosition();
//                        LogUtils.toE("recyclerView", "lastItemPosition:  " + lastItemPosition);
                        if (lastItemPosition + 1 == allAdapter.getItemCount()) {
                            allAdapter.startLoadingMore();
                        }

                    }

                }
            });
        }

    }


    /**
     * 上拉加载更多联网操作
     * <BR/>在实现完成上拉加载更多作后将refresh置为true下拉刷新才能开始
     */
    protected void networkingLoadingMore() {
    }

    /**
     * 当返回的数据小于SIZE及表示没有跟多数据了
     *
     * @param size
     */
    protected void noMessage(int size) {
        if (size != SIZE) {
            allAdapter.setNoLoadingMore();
        }
    }

    @Override
    protected void stopRefrsh() {
        loadMore = true;
        if (allAdapter != null)
            allAdapter.setLoadMoreBoolean();
        if (pl_header != null)
            pl_header.stopRefresh();
    }

    protected void stopLoad() {
        refresh = true;
        loadMore = true;
        if (allAdapter != null)
            allAdapter.stopLoadingMore();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            if (loadMore && linearLayoutManager != null && !allAdapter.isLoadMoreBoolean()) {
                //获取最后一个可见view的位置
                final int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                LogUtils.toE("recyclerView", "lastItemPosition:  " + lastItemPosition);
                if (lastItemPosition + 1 == allAdapter.getItemCount() && allAdapter.getItemCount() > 0) {
                    allAdapter.startLoadingMore();
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
