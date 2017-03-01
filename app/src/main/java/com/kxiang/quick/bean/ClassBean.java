package com.kxiang.quick.bean;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/1/17 12:32
 */

public class ClassBean extends BaseBean {
    private Class<?> theClass;
    private String name;

    public Class<?> getTheClass() {
        return theClass;
    }

    public void setTheClass(Class<?> theClass) {
        this.theClass = theClass;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
