package com.kxiang.quick.function.view.check;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleCheckAdapter;
import com.kxiang.quick.R;

import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/3 17:02
 */

public class DialogCheckAdapter<T> extends BaseRecycleCheckAdapter {
    public DialogCheckAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CheckViewHolder(inflater.inflate(R.layout.fun_adapter_dialog_check, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final CheckViewHolder checkViewHolder = (CheckViewHolder) holder;
        checkViewHolder.setData(list, position);
    }

    private class CheckViewHolder extends BaseChekcViewHolder {



        TextView cb_shop;

        public CheckViewHolder(View itemView) {
            super(itemView);
            cb_shop = (TextView) itemView.findViewById(R.id.cb_shop);
            cb_shop.setOnClickListener(this);
            setCheckView(cb_shop);
        }

        @Override
        public void showData(Object showData, int position) {
            CheckBean.CheckItemBean data1 = (CheckBean.CheckItemBean) showData;
            cb_shop.setText(data1.getName());
        }


        @Override
        public void onClick(View v) {
            setOnCheck(v);
        }
    }


//    /**
//     * 限制选择的个数
//     */
//    private int selectLimit = -1;
//    /**
//     * 当前已选中的个数
//     */
//    private int selectLimitChange;
//
//    public DialogCheckAdapter(Context context, List<T> list, int selectLimit, int selectLimitChange) {
//        super(context, list);
//
//        this.selectLimit = selectLimit;
//        this.selectLimitChange = selectLimitChange;
//    }
//
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        return new HolderGroup(inflater.inflate(R.layout.fun_adapter_dialog_check, parent, false));
//
//
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
//        final HolderGroup hol = (HolderGroup) holder;
//        CheckBean.CheckItemBean bean = (CheckBean.CheckItemBean) list.get(position);
//
//
//        hol.cb_shop.setSelected(bean.isSelect());
//        hol.cb_shop.setText(bean.getName());
//        hol.cb_shop.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectLimit != -1) {
//                    if (selectLimitChange >= selectLimit &&
//                            !((CheckBean.CheckItemBean) list.get(position)).isSelect()) {
//                        Toast.makeText(v.getContext(), "最多选择" + selectLimit + "个",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//
//                        if (hol.cb_shop.isSelected()) {
//                            selectLimitChange--;
//                        }
//                        else {
//                            selectLimitChange++;
//                        }
//                        ((CheckBean.CheckItemBean) list.get(position)).setSelect(
//                                !hol.cb_shop.isSelected());
//                        hol.cb_shop.setSelected(!hol.cb_shop.isSelected());
//                    }
//
//                }
//                else {
//                    if (hol.cb_shop.isSelected()) {
//                        selectLimitChange--;
//                    }
//                    else {
//                        selectLimitChange++;
//                    }
//                    ((CheckBean.CheckItemBean) list.get(position)).setSelect(!hol.cb_shop.isSelected());
//                    hol.cb_shop.setSelected(!hol.cb_shop.isSelected());
//                }
//
//            }
//        });
//
//
//    }
//
//    private class HolderGroup extends RecyclerView.ViewHolder {
//
//        TextView cb_shop;
//
//        public HolderGroup(View itemView) {
//            super(itemView);
//            cb_shop = (TextView) itemView.findViewById(R.id.cb_shop);
//
//        }
//    }


}
