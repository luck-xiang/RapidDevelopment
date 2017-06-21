package com.kxiang.quick.function.activity.wipemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.function.activity.wipemenu.utils.CommonAdapter;
import com.kxiang.quick.function.activity.wipemenu.utils.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class MainCstActivity extends AppCompatActivity {
    private static final String TAG = "zxt";
    private ListView mLv;
    private List<SwipeBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cst);
        mLv = (ListView) findViewById(R.id.test);

        initDatas();
        mLv.setAdapter(new CommonAdapter<SwipeBean>(this, mDatas, R.layout./*item_swipe_menu*/item_cst_swipe) {
            @Override
            public void convert(ViewHolder holder, SwipeBean swipeBean, final int position, View convertView) {
                holder.setText(R.id.tv, swipeBean.name);
                holder.setOnClickListener(R.id.tv, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainCstActivity.this, "position:"+position, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        LogUtils.toE("SnowViewActivity","SnowViewActivity:"+Thread.currentThread().getName());
    }

    private void initDatas() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mDatas.add(new SwipeBean("" + i));
        }

    }
}
