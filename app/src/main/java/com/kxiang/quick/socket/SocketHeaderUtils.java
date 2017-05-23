package com.kxiang.quick.socket;

import com.kexiang.function.utils.SmollUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/1 15:06
 * <p>
 * 1130000012
 * 12位表示发送类型，3位表示昵称长度，后7未表示内容长度
 */

public class SocketHeaderUtils {

    public static final int HeaderLength = 10;
    public static final int PORT = 40012;
    public static final int HeaderLengthStart = 3;


    /**
     * 登录
     */
    public static final String LOGIN = "001";


    /**
     * 群聊
     */
    public static final String CHART = "111";
    /**
     * 发送好友在线列表
     */
    public static final String FRENDS = "011";


    public static String getHeader(int lenth) {
        return SmollUtils.autoGenericCode(lenth + "", HeaderLength);
    }

    public static String getHeader(String titleType, String content) {
        return titleType +
                SmollUtils.autoGenericCode(
                        content.length() + "",
                        HeaderLength - titleType.length()
                ) +
                content;
    }


    public static String getHeader(String titleType, String nickName, String content) {
        return titleType +
                nickName.length() +
                SmollUtils.autoGenericCode(content.length() + "", HeaderLength - 3) +
                nickName +
                content;
    }

}
