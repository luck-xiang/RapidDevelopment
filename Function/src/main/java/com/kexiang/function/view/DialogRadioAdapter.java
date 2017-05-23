package com.kexiang.function.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.BaseRecycleRadioAdapter;
import com.kxiang.function.R;

import java.util.List;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/11/3 17:02
 */

public class DialogRadioAdapter extends BaseRecycleRadioAdapter<String>  {


    public DialogRadioAdapter(Context context, List<String> list, RecyclerView recyclerView, int selectDefault) {
        super(context, list, recyclerView, selectDefault);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHolder(inflater.inflate(R.layout.fun_dialog_radio_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;
        baseViewHolder.notyfyDataShow(position);
    }


    public class BaseViewHolder extends RadioViewHolder {


        private void notyfyDataShow(int position) {
            setPosition(position);

        }

        private TextView tv_radio;

        public BaseViewHolder(View itemView) {
            super(itemView);
            tv_radio = (TextView) itemView.findViewById(R.id.tv_radio);
            setRaidoView(tv_radio);
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
        }
    }
}
