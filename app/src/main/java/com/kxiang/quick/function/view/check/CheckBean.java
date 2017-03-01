package com.kxiang.quick.function.view.check;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/12/2 13:58
 */

public class CheckBean implements Serializable {
    private String title;
    private List<CheckItemBean> itemBean;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<CheckItemBean> getItemBeen() {
        return itemBean;
    }

    public void setItemBeen(List<CheckItemBean> itemBeen) {
        this.itemBean = itemBeen;
    }

    public static class CheckItemBean implements Serializable {
        private String name;
        private boolean select;
        private String type;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }
    }
}
