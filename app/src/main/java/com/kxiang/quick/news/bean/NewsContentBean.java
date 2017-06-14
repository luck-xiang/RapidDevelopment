package com.kxiang.quick.news.bean;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/12 9:03
 */

public class NewsContentBean {

    List<GroupBean> groupBeen;

    public List<GroupBean> getGroupBeen() {
        return groupBeen;
    }

    public void setGroupBeen(List<GroupBean> groupBeen) {
        this.groupBeen = groupBeen;
    }

    public static class GroupBean {
        private String groupName;
        private List<ItemBean> itemBean;

        public String getGroupName() {
            return groupName;
        }

        public void setGroupName(String groupName) {
            this.groupName = groupName;
        }

        public List<ItemBean> getItemBean() {
            return itemBean;
        }

        public void setItemBean(List<ItemBean> itemBean) {
            this.itemBean = itemBean;
        }
    }


    public static class ItemBean {

        private String name;
        private int numbers;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNumbers() {
            return numbers;
        }

        public void setNumbers(int numbers) {
            this.numbers = numbers;
        }
    }

}
