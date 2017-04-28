package com.kxiang.quick.socket.chat;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleAdapter;
import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 10:05
 */

public class ChatFriendListAdapter extends BaseRecycleAdapter<String>  {



    public ChatFriendListAdapter(Context context, List<String>  list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseHolder(inflater.inflate(R.layout.adapter_chat_friend_list, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolder baseHolder = (BaseHolder) holder;
        baseHolder.setData(position, list.get(position));

    }

    public  class BaseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private int position;
        private String data;

        private void setData(int position, String data) {
            this.position = position;
            this.data = data;
            tv_name.setText(data);
        }

        private TextView tv_name;


        public BaseHolder(View itemView) {
            super(itemView);
            itemView.findViewById(R.id.ll_adapter_all).setOnClickListener(this);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);


        }

        @Override
        public void onClick(View v) {

            if (onRecycleItemSelectListener != null) {
                onRecycleItemSelectListener.OnRecycleItemSelect(v, position);
            }
        }
    }

}
