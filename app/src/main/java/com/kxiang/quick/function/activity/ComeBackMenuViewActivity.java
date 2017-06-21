package com.kxiang.quick.function.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.kexiang.function.view.own.ComeBackMenuView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class ComeBackMenuViewActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 开始
     */
    private Button btn_add;
    /**
     * 星星减
     */
    private Button btn_sub;
    private ComeBackMenuView come_back_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_come_back_menu_view);
        initStatusBarColor();
        initView();

    }

    @Override
    protected void initView() {
        title_bar.setTitleName("收缩菜单");
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        come_back_menu = (ComeBackMenuView) findViewById(R.id.come_back_menu);
        come_back_menu.setOnSelectItemListener(new ComeBackMenuView.OnSelectItemListener() {
            @Override
            public void OnSelectItem(int position) {
                showToastShort("选中了：" + position);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                come_back_menu.starAlpha();
                break;
            case R.id.btn_sub:
                Toast toast = new Toast(this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View child = inflater.inflate(R.layout.fun_title_back_bar, null);
                toast.setView(child);
                toast.show();
//                Toast.makeText(this,R.layout.activity_a,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
