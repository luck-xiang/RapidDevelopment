package com.kxiang.quick.news;

import com.kxiang.quick.news.bean.NewsContentBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/12 10:14
 */

public class NewsMainDataUitls {

    private NewsContentBean newsContentBean;
    private List<NewsContentBean.ItemBean> itemBeanList = new ArrayList<>();

    public NewsContentBean getNewsContentBean() {
        return newsContentBean;
    }

    public void setNewsContentBean(NewsContentBean newsContentBean) {
        this.newsContentBean = newsContentBean;
    }

    public NewsMainDataUitls() {
        itemBeanList = new ArrayList<>();
        initBean();
    }

    private void initBean() {


        newsContentBean = new NewsContentBean();
        List<NewsContentBean.GroupBean> groupBeanList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewsContentBean.GroupBean groupBean = new NewsContentBean.GroupBean();
            List<NewsContentBean.ItemBean> itemBeanList = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                NewsContentBean.ItemBean itemBean = new NewsContentBean.ItemBean();
                itemBean.setName("i" + i + ":j" + j);
                itemBean.setNumbers(0);
                itemBeanList.add(itemBean);
            }
            groupBean.setGroupName("i" + i);
            groupBean.setItemBean(itemBeanList);
            groupBeanList.add(groupBean);
        }
        newsContentBean.setGroupBeen(groupBeanList);
    }


    public int addShoppingCart(NewsContentBean.ItemBean itemBean) {
        if (itemBean.getNumbers() == 1)
            itemBeanList.add(itemBean);

        int position = 0;
        for (int i = 0; i < itemBeanList.size(); i++) {
            position = position + itemBeanList.get(i).getNumbers();
        }
        return position;
    }

    public int subShoppingCart(NewsContentBean.ItemBean itemBean) {
        if (itemBean.getNumbers() == 0)
            itemBeanList.remove(itemBean);
        int position = 0;
        for (int i = 0; i < itemBeanList.size(); i++) {
            position = position + itemBeanList.get(i).getNumbers();
        }

        return position;
    }
}
