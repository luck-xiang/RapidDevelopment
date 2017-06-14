package com.kxiang.quick.news.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.recycleview.OnRecycleItemClickListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRefreshLoadingFragment;
import com.kxiang.quick.function.adapter.NewsAdapter;
import com.kxiang.quick.net.BaseRetrofit;
import com.kxiang.quick.news.NewsMainActivity;
import com.kxiang.quick.news.bean.NewsContentBean;
import com.kxiang.quick.news.bean.NewsInfo;
import com.kxiang.quick.utils.ApplicationUtils;

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
        // Required star_empty public constructor
    }

    public static NewsListFragment newInstance(int position) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle a = new Bundle();
        a.putInt("item", position);
        fragment.setArguments(a);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            position = getArguments().getInt("item");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_list, container, false);
    }


    @Override
    protected void onActivityCreatedListener(Bundle savedInstanceState) {
        netWork();
    }

    protected RecyclerView rlv_all;
    NewsAdapter recycleAdapter;
    List<NewsContentBean.ItemBean> recycleData;
    private int position = 0;


    @Override
    public void initRecycleView() {
        recycleData = ((NewsMainActivity) getActivity())
                .getNewsMainDataUitls()
                .getNewsContentBean()
                .getGroupBeen()
                .get(position)
                .getItemBean();
        rlv_all = (RecyclerView) getView().findViewById(R.id.rlv_all);
        recycleAdapter = new NewsAdapter(getContext(), recycleData, rlv_all);
        setRecycle(recycleAdapter,null,null);
        initRecycle(recycleAdapter);
        recycleAdapter.setOnRecycleItemSelectListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                switch (view.getId()) {
                    case R.id.tv_add:
                        recycleData.get(position)
                                .setNumbers(recycleData.get(position).getNumbers() + 1);
                        ((NewsMainActivity) getActivity()).addShoppingCart(recycleData.get(position));
                        break;
                    case R.id.tv_sub:
                        if (recycleData.get(position).getNumbers() >= 1) {
                            recycleData.get(position)
                                    .setNumbers(recycleData.get(position).getNumbers() - 1);
                            ((NewsMainActivity) getActivity())
                                    .subShoppingCart(recycleData.get(position));
                        }

                        break;
                }
                recycleAdapter.notifyDataSetChanged();
            }
        });
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

//        if (type == BaseRefreshLoadingFragment.REFRESH) {
//            pl_header.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    initRefreshAndLoading();
//                    recycleData.clear();
//
//                    for (int i = 0; i < 20; i++) {
//                        recycleData.add("数据:" + position);
//                        position++;
//                    }
//                    pl_header.stopRefresh();
//                    if (pageSize == PAGE_SIZE) {
//                        recycleAdapter.initLoading();
//                    }
//                    else {
//                        recycleAdapter.noMoreData();
//                    }
//                    recycleAdapter.notifyDataSetChanged();
//
//                }
//            }, 5000);
//        }
//        else if (type == BaseRefreshLoadingFragment.LOADING) {
//            rlv_all.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//
//                    initRefreshAndLoading();
//                    Log.e("下拉加载", "结束");
//                    if (pageSize != PAGE_SIZE) {
//                        recycleAdapter.noMoreData();
//                    }
//                    else {
//
//                        recycleAdapter.initLoading();
//                        for (int i = 0; i < 5; i++) {
//                            recycleData.add("数据:" + position);
//                            position++;
//                        }
//                    }
//                    recycleAdapter.notifyDataSetChanged();
//
//                }
//            }, 2000);
//        }

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

                        LogUtils.toJsonString("news", response.body());

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
