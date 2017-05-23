package com.kexiang.function.view.refresh;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/10 9:57
 */

public interface RefreshHeaderable {

    int STOPRFESH = 0;
    int REFRESHING = 1;
    int NORMAL = 3;


    boolean onRefresScroll(float y,int type);

    void setPullRefreshLayout(RefreshLoadingLayout refreshLayout);

    void startRefresh();

    void stopRefresh();
}
