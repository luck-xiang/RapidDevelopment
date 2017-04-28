package com.kxiang.quick.socket.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleAdapter;
import com.kxiang.quick.R;
import com.kxiang.quick.socket.chat.bean.ChatBean;
import com.kxiang.quick.socket.chat.bean.ChatBeanUtils;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/6 14:39
 */

public class ChatListAdapter extends BaseRecycleAdapter<ChatBean> {
    public ChatListAdapter(Context context, List<ChatBean> list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ChatBeanUtils.LeftWord) {

            return new LeftWordHolder(inflater.inflate(R.layout.adapter_chat_list_left, parent, false));
        }
        else {
          return   new RightWordHolder(inflater.inflate(R.layout.adapter_chat_list_right, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (getItemViewType(position) == ChatBeanUtils.LeftWord) {
            LeftWordHolder leftWordHolder = (LeftWordHolder) holder;
            leftWordHolder.setDat(list.get(position), position);

        }
        else if (getItemViewType(position) == ChatBeanUtils.RightWord) {
            RightWordHolder rightWordHolder = (RightWordHolder) holder;
            rightWordHolder.setDat(list.get(position), position);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (list.get(position).getShowTyep() == ChatBeanUtils.LeftWord) {
            return ChatBeanUtils.LeftWord;
        }
        else {
            return ChatBeanUtils.RightWord;
        }

    }



    private class RightWordHolder extends RecyclerView.ViewHolder {


        private ChatBean data;
        private int position;

        public void setDat(ChatBean data, int position) {
            this.data = data;
            this.position = position;
            tv_name.setText(data.getSendName());
            tv_content.setText(data.getContent());
        }

        private TextView tv_name;
        private TextView tv_content;

        public RightWordHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }

    private class LeftWordHolder extends RecyclerView.ViewHolder {
        private ChatBean data;
        private int position;

        public void setDat(ChatBean data, int position) {
            this.data = data;
            this.position = position;
            tv_name.setText(data.getReceiveName());
            tv_content.setText(data.getContent());
        }


        private TextView tv_name;
        private TextView tv_content;

        public LeftWordHolder(View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
        }
    }
}
