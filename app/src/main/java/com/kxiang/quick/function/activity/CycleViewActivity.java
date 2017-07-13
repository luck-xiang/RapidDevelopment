package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.cycle.LoopCycleLayout;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class CycleViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cycle_view);

    }

    private LoopCycleLayout ll_cycle;
    private LoopCycleLayout.Adapter adapter;
    private List<String> testData;

    @Override
    protected void initView() {
        testData = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            testData.add("数据：" + i);
        }

        ll_cycle = (LoopCycleLayout) findViewById(R.id.ll_cycle);
        adapter = new LoopCycleLayout.Adapter() {

            @Override
            public LoopCycleLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return new ViewHodler(
                        LayoutInflater.from(thisActivity).inflate(R.layout.item_cycle, parent, false)
                );
            }

            @Override
            public void onBindViewHolder(LoopCycleLayout.ViewHolder holder, int position) {

                ViewHodler viewHodler = (ViewHodler) holder;
                viewHodler.setData(position, testData.get(position));
            }

            @Override
            public int getItemCount() {
                return testData.size();
            }

            class ViewHodler extends LoopCycleLayout.ViewHolder implements View.OnClickListener {


                private int position;

                public void setData(int position, String data) {
                    this.position = position;
                    LogUtils.toE("onBindViewHolder", "position:" + position);
                    tv_test.setText(data);
                    if (position == 0) {
                        ll_all.setBackgroundColor(getResources().getColor(R.color.color_fd6361));
                    }
                    else if (position == 1) {
                        ll_all.setBackgroundColor(getResources().getColor(R.color.color_ffa200));
                    }
                    else if (position == 2) {
                        ll_all.setBackgroundColor(getResources().getColor(R.color.color_FB819E));
                    }
                    else if (position == 3) {
                        ll_all.setBackgroundColor(getResources().getColor(R.color.color_65D0B7));
                    }
                    else if (position == 4) {
                        ll_all.setBackgroundColor(getResources().getColor(R.color.palevioletred));
                    }

                }

                private TextView tv_test;
                private LinearLayout ll_all;

                public ViewHodler(View itemView) {
                    super(itemView);
                    tv_test = (TextView) itemView.findViewById(R.id.tv_test);
                    ll_all = (LinearLayout) itemView.findViewById(R.id.ll_all);
                    ll_all.setOnClickListener(this);

                }

                @Override
                public void onClick(View v) {
                    LogUtils.toE("onBindViewHolder", "onClick:" + position);
                    showToastShort("点击了："+position);
                }
            }

        };
        ll_cycle.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
