package com.kxiang.quick.socket.chat;

import com.google.gson.Gson;
import com.kxiang.quick.socket.chat.bean.LoginBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/6 10:45
 */

public class ClientChatMessageUtils {


    public static List<String> getFriends(String content) {

        List<String> list = new ArrayList<>();
        String[] temp = content.split("##");
        for (int i = 0; i < temp.length; i++) {
            list.add(temp[i]);
        }
        return list;
    }

    public static LoginBean getLogin(String content, Gson gson) {

        return gson.fromJson(content, LoginBean.class);

    }


}
