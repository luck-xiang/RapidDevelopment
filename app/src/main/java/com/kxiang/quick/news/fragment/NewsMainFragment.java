package com.kxiang.quick.news.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseFragment;
import com.kxiang.quick.news.NewsMainActivity;
import com.kxiang.quick.news.bean.NewsContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class NewsMainFragment extends BaseFragment {


    public static NewsMainFragment newInstance() {
        NewsMainFragment fragment = new NewsMainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news_main, container, false);
    }


    @Override
    protected void onActivityCreatedListener(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        initTitle();
        initViewPager();
        title_name.setText("新闻");
    }

    @Override
    protected void leftFinish() {
        NewsMainActivity activity = (NewsMainActivity) getActivity();


        if (activity.getmDrawerLayout().isDrawerOpen(Gravity.LEFT)) {
            activity.getmDrawerLayout().closeDrawer(Gravity.LEFT);

        }
        else {
            activity.getmDrawerLayout().openDrawer(Gravity.LEFT);
        }
    }

    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<Fragment> fragments;
    private NewsContentBean newsContentBean;

    private void initViewPager() {
        newsContentBean = ((NewsMainActivity) getActivity())
                .getNewsMainDataUitls()
                .getNewsContentBean();

        fragments = new ArrayList<>();
        for (int i = 0; i < newsContentBean.getGroupBeen().size(); i++) {
            fragments.add(NewsListFragment.newInstance(i));
        }

        view_pager = (ViewPager) getView().findViewById(R.id.view_pager);
        view_pager.setAdapter(new ViewPagerBaseAdapter(getChildFragmentManager()));
        tab_layout = (TabLayout) getView().findViewById(R.id.tab_layout);
        tab_layout.setTabMode(TabLayout.MODE_FIXED);//设置tab模式，当前为系统默认模式
        tab_layout.setupWithViewPager(view_pager, true);
//        tab_layout.getTabAt(0).setText("数据1");
//        tab_layout.getTabAt(1).setText("数据2");
//        tab_layout.getTabAt(2).setText("数据3");
//        tab_layout.getTabAt(3).setText("数据4");
//        tab_layout.getTabAt(4).setText("数据5");
//        tab_layout.getTabAt(5).setText("数据5");
//        tab_layout.getTabAt(6).setText("数据7");

        tab_layout.postDelayed(new Runnable() {
            @Override
            public void run() {
                tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
        }, 3000);
    }


    private class ViewPagerBaseAdapter extends FragmentPagerAdapter {

        public ViewPagerBaseAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return newsContentBean.getGroupBeen().get(position).getGroupName();
        }
    }
}
