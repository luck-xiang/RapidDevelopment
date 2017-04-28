package com.kxiang.quick.news.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRefreshLoadingFragment;
import com.kxiang.quick.function.adapter.RefreshLoadingAdapter;
import com.kxiang.quick.net.BaseRetrofit;
import com.kxiang.quick.news.bean.NewsInfo;
import com.kxiang.quick.utils.ApplicationUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsListFragment extends BaseRefreshLoadingFragment {


    public NewsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycleView();
        netWork();
    }

    protected RecyclerView rlv_all;
    RefreshLoadingAdapter recycleAdapter;
    List<String> recycleData;
    private int position = 1;

    private void initRecycleView() {
        recycleData = new ArrayList<>();
        rlv_all = (RecyclerView) getView().findViewById(R.id.rlv_all);
        recycleAdapter = new RefreshLoadingAdapter(getContext(), recycleData, rlv_all);
        initRecycle(recycleAdapter);
        rlv_all.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv_all.setAdapter(recycleAdapter);
    }


    //Fragment是否可以见会调用该函数
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.toE("isVisibleToUser", "" + isVisibleToUser);
        if (recycleAdapter != null && isVisibleToUser) {
            recycleAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onRefreshLoadingListener(int type) {

        if (type == BaseRefreshLoadingFragment.REFRESH) {
            pl_header.postDelayed(new Runnable() {

                @Override
                public void run() {
                    initRefreshAndLoading();
                    recycleData.clear();

                    for (int i = 0; i < 20; i++) {
                        recycleData.add("数据:" + position);
                        position++;
                    }
                    pl_header.stopRefresh();
                    if (pageSize == PAGE_SIZE) {
                        recycleAdapter.initLoading();
                    }
                    else {
                        recycleAdapter.noMoreData();
                    }
                    recycleAdapter.notifyDataSetChanged();

                }
            }, 5000);
        }
        else if (type == BaseRefreshLoadingFragment.LOADING) {
            rlv_all.postDelayed(new Runnable() {
                @Override
                public void run() {

                    initRefreshAndLoading();
                    Log.e("下拉加载", "结束");
                    if (pageSize != PAGE_SIZE) {
                        recycleAdapter.noMoreData();
                    }
                    else {

                        recycleAdapter.initLoading();
                        for (int i = 0; i < 5; i++) {
                            recycleData.add("数据:" + position);
                            position++;
                        }
                    }
                    recycleAdapter.notifyDataSetChanged();

                }
            }, 2000);
        }

    }

    private void netWork() {
        Observable
                .create(new ObservableOnSubscribe<NewsInfo>() {
                    @Override
                    public void subscribe(ObservableEmitter<NewsInfo> e) throws Exception {

                        String type;
                        if ("T1348647909107".equals(BaseRetrofit.HEAD_LINE_NEWS)) {
                            type = "headline";
                        }
                        else {
                            type = "list";
                        }

                        Response<NewsInfo> response = ApplicationUtils
                                .getApplicition(getActivity())
                                .getApiNetService()
                                .getNewsList(type, "T1348647909107", 0)
                                .execute();

                        LogUtils.toJsonString("news",response.body());

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsInfo value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
