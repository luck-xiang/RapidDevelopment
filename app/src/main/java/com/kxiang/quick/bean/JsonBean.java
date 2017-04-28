package com.kxiang.quick.bean;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/28 9:29
 */

public class JsonBean {



    private List<BeanBean> bean;

    public List<BeanBean> getBean() {
        return bean;
    }

    public void setBean(List<BeanBean> bean) {
        this.bean = bean;
    }

    public static class BeanBean {
        /**
         * phone_num : null
         * relative :
         * name :
         */

        private Object phone_num;
        private String relative;
        private String name;

        public Object getPhone_num() {
            return phone_num;
        }

        public void setPhone_num(Object phone_num) {
            this.phone_num = phone_num;
        }

        public String getRelative() {
            return relative;
        }

        public void setRelative(String relative) {
            this.relative = relative;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
