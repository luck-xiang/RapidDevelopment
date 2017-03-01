package com.kxiang.quick.function.view.check;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kxiang.quick.R;
import com.kxiang.quick.function.view.rlv.BaseRecycleViewAdapter;

import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/3 17:02
 */

public class DialogCheckAdapter<T> extends BaseRecycleViewAdapter {


    /**
     * 限制选择的个数
     */
    private int selectLimit = -1;
    /**
     * 当前已选中的个数
     */
    private int selectLimitChange;

    public DialogCheckAdapter(Context context, List<T> list, int selectLimit, int selectLimitChange) {
        super(context, list);

        this.selectLimit = selectLimit;
        this.selectLimitChange = selectLimitChange;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new HolderGroup(inflater.inflate(R.layout.fun_adapter_dialog_check, parent, false));


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final HolderGroup hol = (HolderGroup) holder;
        CheckBean.CheckItemBean bean = (CheckBean.CheckItemBean) list.get(position);


        hol.cb_shop.setSelected(bean.isSelect());
        hol.cb_shop.setText(bean.getName());
        hol.cb_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectLimit != -1) {
                    if (selectLimitChange >= selectLimit &&
                            !((CheckBean.CheckItemBean) list.get(position)).isSelect()) {
                        Toast.makeText(v.getContext(), "最多选择" + selectLimit + "个",
                                Toast.LENGTH_SHORT).show();
                    }
                    else {

                        if (hol.cb_shop.isSelected()) {
                            selectLimitChange--;
                        }
                        else {
                            selectLimitChange++;
                        }
                        ((CheckBean.CheckItemBean) list.get(position)).setSelect(
                                !hol.cb_shop.isSelected());
                        hol.cb_shop.setSelected(!hol.cb_shop.isSelected());
                    }

                }
                else {
                    if (hol.cb_shop.isSelected()) {
                        selectLimitChange--;
                    }
                    else {
                        selectLimitChange++;
                    }
                    ((CheckBean.CheckItemBean) list.get(position)).setSelect(!hol.cb_shop.isSelected());
                    hol.cb_shop.setSelected(!hol.cb_shop.isSelected());
                }

            }
        });

//        hol.cb_shop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                if (selectLimit != -1) {
//                    if (selectLimitChange >= selectLimit &&
//                            ((CheckBean.CheckItemBean) list.get(position)).isSelect()) {
//                        Toast.makeText(buttonView.getContext(), "最多选择" + selectLimit,
//                                Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        selectLimitChange++;
//                        ((CheckBean.CheckItemBean) list.get(position)).setSelect(isChecked);
//                    }
//
//                }
//                else {
//                    ((CheckBean.CheckItemBean) list.get(position)).setSelect(isChecked);
//                }
//
//
//            }
//        });

    }

    private class HolderGroup extends RecyclerView.ViewHolder {

        TextView cb_shop;

        public HolderGroup(View itemView) {
            super(itemView);
            cb_shop = (TextView) itemView.findViewById(R.id.cb_shop);

        }
    }


}
