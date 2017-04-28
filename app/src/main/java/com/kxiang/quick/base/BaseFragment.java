package com.kxiang.quick.base;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kxiang.quick.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/30 10:20
 */

public class BaseFragment extends Fragment {




    protected ImageView iv_title_left;
    protected TextView tv_title_name;
    protected TextView tv_title_right;

    //初始化title显示
    protected void initTitle() {
        iv_title_left = (ImageView) getView().findViewById(R.id.iv_title_left);
        tv_title_name = (TextView) getView().findViewById(R.id.tv_title_name);
        tv_title_right = (TextView) getView().findViewById(R.id.tv_title_right);
        if (iv_title_left != null)
            iv_title_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftFinish();
                }
            });
    }

    /**
     * 如需操作左返回键执行该方法
     */
    protected void leftFinish() {
        getActivity().finish();
    }

}
