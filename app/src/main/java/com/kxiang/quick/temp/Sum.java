package com.kxiang.quick.temp;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/21 11:45
 */

public class Sum {

    private int positionSize = 2000;

    public int getPositionSize() {
        return positionSize;
    }

    public void getsum(String name) {

        LogUtils.toE(name, positionSize);
        positionSize--;

    }

    public void getsum1(String name) {


        LogUtils.toE(name, positionSize);
        positionSize--;

    }

    public void getsum2(String name) {

        LogUtils.toE(name, positionSize);
        positionSize--;

    }

    public void getsum3(String name) {

        LogUtils.toE(name, positionSize);
        positionSize--;

    }


}
