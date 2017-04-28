package com.kxiang.quick.socket.chat.bean;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 16:05
 */

public class ChatBean {


    /**
     * true群聊，false单聊
     */
    boolean chatSingle;
    /**
     * 发送者姓名
     */
    String sendName;
    /**
     * 接受者姓名
     */
    String receiveName;
    String content;

    /**
     * 显示的位置
     */
    int showTyep;

    public int getShowTyep() {
        return showTyep;
    }

    public void setShowTyep(int showTyep) {
        this.showTyep = showTyep;
    }

    public boolean isChatSingle() {
        return chatSingle;
    }

    public void setChatSingle(boolean chatSingle) {
        this.chatSingle = chatSingle;
    }

    public String getSendName() {
        return sendName;
    }

    public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
