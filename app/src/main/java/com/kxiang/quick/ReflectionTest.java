package com.kxiang.quick;

import java.lang.reflect.Field;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/26 15:56
 */

public class ReflectionTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException{
        Testsdf t=new Testsdf();
        t.print();
        Field[] f=t.getClass().getDeclaredFields();

        for(int i=0;i<f.length;i++)
        {
            f[i].setAccessible(true);
//System.out.println(f[i].getType());
            f[i].set(t, "japan");
        }
        t.print();
    }
}
