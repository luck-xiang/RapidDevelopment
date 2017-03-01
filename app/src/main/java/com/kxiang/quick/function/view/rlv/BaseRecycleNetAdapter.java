package com.kxiang.quick.function.view.rlv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:growing
 * 创建人:kexiang
 * 创建时间:2016/8/29 16:28
 */
public abstract class BaseRecycleNetAdapter<T> extends BaseRecycleViewAdapter<T> {


    public BaseRecycleNetAdapter(Context context, List<T> list) {
        super(context, list);
    }


    @Override
    public int getItemViewType(int position) {
        if (position == list.size()) {
            return LOADING_MORE;
        }
        return getItemViewTypeNet(position);
    }

    @Override
    public int getItemCount() {
        if (list.size() == 0) {
            return 0;
        }
        return list.size() + 1;
    }

    public int getItemViewTypeNet(int position) {

        return super.getItemViewType(position);
    }


    public abstract RecyclerView.ViewHolder onCreateViewHolderNet(ViewGroup parent, int viewType);

    public interface OnLoadingMoreListener {
        void onLoadingMore();
    }

    private OnLoadingMoreListener onLoadingMoreListener;

    /**
     * 加载更多接口
     *
     * @param onLoadingMoreListener
     */
    public void setOnLoadingMoreListener(OnLoadingMoreListener onLoadingMoreListener) {
        this.onLoadingMoreListener = onLoadingMoreListener;
    }


    private final int LOADING_MORE = -10;
    /**
     * 是否执行加载跟多接口
     * <BR>false:不执行加载更多接口
     * <BR>true:执行加载更多接口
     */
    protected boolean loadMoreBoolean = true;

    public void setLoadMoreBoolean() {
        this.loadMoreBoolean = false;
    }

    public boolean isLoadMoreBoolean() {
        return loadMoreBoolean;
    }

    private String loadingString = "点 击 加 载 更 多";

    /**
     * 没有更多了
     */
    public void setNoLoadingMore() {
        loadMoreBoolean = true;
        loadingString = "没 有 更 多 消 息 了……";
        if (loadingMoreHolder != null)
            loadingMoreHolder.noMoreData();
    }

    /**
     * 正在加载更多
     */
    public void startLoadingMore() {
        loadingString = "努 力 加 载 中……";
        if (loadMoreBoolean) {
            return;
        }
        if (loadingMoreHolder != null)
            loadingMoreHolder.loadingMore();
        if (onLoadingMoreListener != null) {
            onLoadingMoreListener.onLoadingMore();
        }
    }

    /**
     * 停止加载
     */
    public void stopLoadingMore() {
        loadingString = "点 击 加 载 更 多……";
        if (loadingMoreHolder != null)
            loadingMoreHolder.checkLoading();

    }

    private NetLoadingMoreHolder loadingMoreHolder;

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == LOADING_MORE) {
            loadingMoreHolder = (NetLoadingMoreHolder) holder;
            loadingMoreHolder.tv_load_more_ex.setText(loadingString);
//            if (loadingMoreType == Loading) {
//                loadingMoreHolder.loadingMore();
//            }
//            else if (loadingMoreType == No_Loading_More) {
//                loadingMoreHolder.noMoreData();
//            }
//            else if (loadingMoreType == Loading_OK) {
//                loadingMoreHolder.checkLoading();
//            }
            //1、加载更多判断，当loadMoreBoolean=true的时候执行加载操作，当执行加载操作的时候
            //不能再次执行该方法，由于滑动监听或者item的添加都会执行该if语句的内容，所以，在执行了该if
            //语句后立即将loadMoreBoolean=false直到完成网络连接或者网络连接失败后将
            //loadMoreBoolean置为true
            //2、加载分为三个状态
            //1，正在加载，2，加载完成但是还有跟多信息或者加载失败提示，3，加载完成后没有跟多信息
            //只有当加载状态处于第1种Loading状态的时候才能执行加载，当处于第2种状态的时候等待加载，可以切换状态
            //当处于第3种状态的时候只有执行下拉刷新才能重置状态

//            if (onLoadingMoreListener != null && loadMoreBoolean && loadingMoreType == Loading) {
//                loadMoreBoolean = false;
//                onLoadingMoreListener.onLoadingMore();
//            }

        }
        else {
            onBindViewHolderNet(holder, position);
        }
    }

    public abstract void onBindViewHolderNet(RecyclerView.ViewHolder holder, final int position);

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOADING_MORE) {
            return new NetLoadingMoreHolder(inflater.inflate(R.layout.fun_recycle_load_more, parent, false));
        }
        return onCreateViewHolderNet(parent, viewType);
    }


    public class NetLoadingMoreHolder extends RecyclerView.ViewHolder
//            implements View.OnClickListener
    {
        ProgressBar pb_load_more_ex;
        TextView tv_load_more_ex;


        public NetLoadingMoreHolder(View itemView) {
            super(itemView);
//            itemView.findViewById(ll_net_all).setOnClickListener(this);
            pb_load_more_ex = (ProgressBar) itemView.findViewById(R.id.pb_load_more_ex);
            tv_load_more_ex = (TextView) itemView.findViewById(R.id.tv_load_more_ex);
        }

        void loadingMore() {

            if (pb_load_more_ex.getVisibility() == View.GONE) {
                pb_load_more_ex.setVisibility(View.VISIBLE);
            }
            tv_load_more_ex.setText(loadingString);
        }

        void noMoreData() {
            if (pb_load_more_ex.getVisibility() == View.VISIBLE) {
                pb_load_more_ex.setVisibility(View.GONE);
            }
            tv_load_more_ex.setText(loadingString);
        }

        void checkLoading() {
            if (pb_load_more_ex.getVisibility() == View.VISIBLE) {
                pb_load_more_ex.setVisibility(View.GONE);
            }
            tv_load_more_ex.setText(loadingString);
        }

//        @Override
//        public void onClick(View v) {
//            switch (v.getId()) {
//                case ll_net_all:
//                    if (loadingMoreType == Loading_OK) {
//                        startLoadingMore();
//                    }
//                    break;
//            }
//        }
    }


}
