package com.kxiang.quick.socket.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.recycleview.OnRecycleItemClickListener;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.R;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class ChatFriendListFragment extends Fragment {


    public static ChatFriendListFragment newInstance() {
        ChatFriendListFragment fragment = new ChatFriendListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat_friend_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initRecycle();
    }

    private RecyclerView rlv_all;
    private ChatFriendListAdapter chatFriendListAdapter;
    private List<String> chatFriend;

    private void initRecycle() {
        chatFriend = new ArrayList<>();
        chatFriendListAdapter = new ChatFriendListAdapter(getContext(), chatFriend);
        rlv_all = (RecyclerView) getView().findViewById(R.id.rlv_all);
        rlv_all.addItemDecoration(
                new RecycleDividerItemLinear(
                        getContext()
                        , LinearLayoutManager.VERTICAL
                        , getResources().getColor(R.color.color_dddddd)
                ));
        rlv_all.setLayoutManager(new LinearLayoutManager(getContext()));
        rlv_all.setAdapter(chatFriendListAdapter);
        chatFriendListAdapter.setOnRecycleItemSelectListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                Intent intent = new Intent(getContext(), ChatListActivity.class);
                intent.putExtra(ChatListActivity.ReciviedName, chatFriend.get(position));
                startActivity(intent);
            }
        });
    }

    public void setChatFriend(List<String> chatFriend) {
        LogUtils.toE("chatFriend", chatFriend);
        ChatFriendListFragment.this.chatFriend.addAll(chatFriend);
        rlv_all.post(new Runnable() {
            @Override
            public void run() {
                chatFriendListAdapter.notifyDataSetChanged();
            }
        });

    }

    //Fragment是否可以见会调用该函数
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.toE("isVisibleToUser", "" + isVisibleToUser);
        if (chatFriendListAdapter != null && isVisibleToUser) {
            chatFriendListAdapter.notifyDataSetChanged();
        }
    }

}
