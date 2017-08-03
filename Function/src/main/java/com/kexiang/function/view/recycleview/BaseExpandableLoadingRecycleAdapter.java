package com.kexiang.function.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kexiang.function.utils.LogUtils;

import java.util.LinkedList;
import java.util.List;


/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/8/1 9:16
 */

public abstract class BaseExpandableLoadingRecycleAdapter<T> extends BaseRecycleRefreshOrLoadingMoreAdapter {

    /**
     * 对应的所有item保存到该类中，保存所有数据
     */
    private List<BaseExpandableBean> saveListBean;
    /**
     * 显示的数据，点击后增删都是在这个list中处理
     */
    private List<BaseExpandableBean> changeListBean;

    public BaseExpandableLoadingRecycleAdapter(Context context, List<T> list,RecyclerView rlv_all) {
        super(context, list,rlv_all);
        saveListBean = new LinkedList<>();
        //因为会在不同位置增删，用linkedlist增删快
        changeListBean = new LinkedList<>();
    }


    /**
     * 自己封装的写法，也简单，看看就懂了
     */
    protected abstract class BaseExpandableViewHolder<T> extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        protected List<T> data;
        protected int position;
        protected BaseExpandableBean expandableBean;

        public void setData(List<T> data, int position) {
            this.position = position;
            this.data = data;
            //放置重用item时候显示数据出错
            expandableBean = changeListBean.get(position);
            //当为group的item的时候不需要调用该方法
            if (groupSelecltView != null)
                groupSelecltView.setSelected(expandableBean.isStatus());
            showGroupAndItemBean(data.get(expandableBean.getGroupPosition()), expandableBean);
        }

        public abstract void showGroupAndItemBean(T groupBean, BaseExpandableBean expandableBean);


        public BaseExpandableViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * 需要改变group的view，有可能是箭头符号
         */
        private View groupSelecltView;

        /**
         * 设置该view，这个是关键，不写将没有效果在构造方法调用
         *
         * @param groupSelecltView
         */
        protected void setGroupSelectView(View groupSelecltView) {
            this.groupSelecltView = groupSelecltView;
        }

        /**
         * 点击事件调用，这里是点击后对组别人员的增删的关键，实现点击位置的view位置触发，在点击事件onClick里面调用
         */
        protected void setGroupSelecltViewOnClick() {
            //改变需要动画view状态
            groupSelecltView.setSelected(!groupSelecltView.isSelected());
            //改变view的状态
            expandableBean.setStatus(groupSelecltView.isSelected());
            if (onExpandableClickListener != null) {
                //将位置展示给调用着
                onExpandableClickListener.onExpandableClick(
                        groupSelecltView,
                        expandableBean
                );
            }
            //这里是关键，增删的算法
            changeData(groupSelecltView.isSelected(), expandableBean.getGroupPosition());
        }


    }


    public List<BaseExpandableBean> getChangeListBean() {
        return changeListBean;
    }


    protected OnExpandableClickListener onExpandableClickListener;

    public void setOnExpandableClickListener(OnExpandableClickListener onExpandableClickListener) {
        this.onExpandableClickListener = onExpandableClickListener;
    }


   

    @Override
    public int getItemCount() {
        //这里是显示所有item的个数
        return changeListBean.size()+1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position==changeListBean.size()){
            return LOADING;
        }
        else if (changeListBean.get(position).getType() == BaseExpandableBean.GROUP) {
            return BaseExpandableBean.GROUP;
        }
        else {
            return BaseExpandableBean.FIRST_ITEM;
        }
    }

    private void changeData(boolean check, int groupPosition) {

        if (check) {
//            for (int i = 0; i < changeListBean.size(); i++) {
//                BaseExpandableBean score = changeListBean.get(i);
//                if (score.getGroupPosition() == groupPosition
//                        && score.getType() == BaseExpandableBean.FIRST_ITEM) {
//                    changeListBean.remove(i);
//                    i--;
//                    notifyItemRemoved(i);
//                }
//            }
//            this.notifyDataSetChanged();
            for (BaseExpandableBean score : saveListBean) {
                if (score.getGroupPosition() == groupPosition
                        && score.getType() == BaseExpandableBean.FIRST_ITEM) {
                    changeListBean.remove(score);
                }
            }
//            this.notifyDataSetChanged();
        }
        else {

            for (int i = 0; i < changeListBean.size(); i++) {
                BaseExpandableBean score = changeListBean.get(i);

                if (score.getGroupPosition() == groupPosition) {
                    LogUtils.toE("changeListBean", "groupPosition:" + groupPosition);
                    for (BaseExpandableBean scoreTemp : saveListBean) {
                        if (scoreTemp.getGroupPosition() == groupPosition
                                && scoreTemp.getType() == BaseExpandableBean.FIRST_ITEM) {
                            i++;
                            changeListBean.add(i, scoreTemp);
//                            notifyItemInserted(i);

                        }

                    }
                    break;
                }

            }

        }

        this.notifyDataSetChanged();
    }


    private int groupPosition = 0;

    public void addGroupAndItem(int itemSize) {
        BaseExpandableBean group = new BaseExpandableBean();
        group.setType(BaseExpandableBean.GROUP);
        group.setGroupPosition(groupPosition);
        changeListBean.add(group);
        saveListBean.add(group);

        for (int j = 0; j < itemSize; j++) {
            BaseExpandableBean item = new BaseExpandableBean();
            item.setType(BaseExpandableBean.FIRST_ITEM);
            item.setGroupPosition(groupPosition);
            item.setItemNumber(j);
            changeListBean.add(item);
            saveListBean.add(item);
        }
        groupPosition++;
    }

    public void removeAll() {
        changeListBean.clear();
        saveListBean.clear();
        groupPosition = 0;
    }



}
