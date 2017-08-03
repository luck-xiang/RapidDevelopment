package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kexiang.function.view.recycleview.BaseExpandableBean;
import com.kexiang.function.view.recycleview.OnExpandableClickListener;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseRecycleViewActivity;
import com.kxiang.quick.function.adapter.ExpandableRecycleAdapter;
import com.ybao.pullrefreshview.layout.BaseHeaderView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class ExpandableActivity extends BaseRecycleViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable);
    }

    @Override
    protected void initView() {
        initTitle();
        title_name.setText("收缩框");
    }

    private ExpandableRecycleAdapter adapter;
    private List<GroupBean> dataAdapter;

    protected BaseHeaderView pl_header;


    @Override
    protected void initRecycleView() {
        dataAdapter = new LinkedList<>();
        //分组创建数据
        for (int size = 0; size < 3; size++) {
            GroupBean groupBean = new GroupBean();
            List<ItemBean> itemBeanList = new ArrayList<>();
            groupBean.setGroup(size);

            for (int j = 0; j < 10; j++) {
                ItemBean itemBean = new ItemBean();
                itemBean.setItem(j);
                itemBeanList.add(itemBean);
            }
            groupBean.setItemBeanList(itemBeanList);
            dataAdapter.add(groupBean);
        }

        //recycleview的基本写法
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        adapter = new ExpandableRecycleAdapter(thisActivity, dataAdapter);

        //这里是关键，根据你的组别和每组个数绑定正真的item
        for (int i = 0; i < dataAdapter.size(); i++) {
            adapter.addGroupAndItem(dataAdapter.get(i).getItemBeanList().size());
        }
        setRecycle(adapter, null, new RecycleDividerItemLinear(thisActivity,
                LinearLayoutManager.VERTICAL,
                getResources().getColor(R.color.color_eeeeee))
        );

        adapter.setOnExpandableClickListener(new OnExpandableClickListener() {
            @Override
            public void onExpandableClick(View clickView, BaseExpandableBean selecBean) {

                //这个bean里面含有是group还是group里面的item；
            }
        });
        pl_header = (BaseHeaderView) findViewById(R.id.pl_header);


        pl_header.setOnRefreshListener(new BaseHeaderView.OnRefreshListener() {
            @Override
            public void onRefresh(BaseHeaderView baseHeaderView) {
                pl_header.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pl_header.stopRefresh();

                        //刷新完成，底部添加组数
                        List<GroupBean> tempdata = new LinkedList<>();
                        int size = dataAdapter.size();
                        for (int i = size; i < size + 3; i++) {
                            GroupBean groupBean = new GroupBean();
                            List<ItemBean> itemBeanList = new ArrayList<>();
                            groupBean.setGroup(i);

                            for (int j = 0; j < 10; j++) {
                                ItemBean itemBean = new ItemBean();
                                itemBean.setItem(j);
                                itemBeanList.add(itemBean);
                            }
                            groupBean.setItemBeanList(itemBeanList);
                            tempdata.add(groupBean);
                        }

                        dataAdapter.addAll(tempdata);
                        //根据你添加的组数，添加实际item的个数
                        for (int i = 0; i < tempdata.size(); i++) {
                            adapter.addGroupAndItem(tempdata.get(i).getItemBeanList().size());
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, 2000);

            }
        });


    }


    public static class GroupBean {
        List<ItemBean> itemBeanList;
        int group;

        public List<ItemBean> getItemBeanList() {
            return itemBeanList;
        }

        public void setItemBeanList(List<ItemBean> itemBeanList) {
            this.itemBeanList = itemBeanList;
        }

        public int getGroup() {
            return group;
        }

        public void setGroup(int group) {
            this.group = group;
        }
    }

    public static class ItemBean {
        int item;

        public int getItem() {
            return item;
        }

        public void setItem(int item) {
            this.item = item;
        }
    }

}
