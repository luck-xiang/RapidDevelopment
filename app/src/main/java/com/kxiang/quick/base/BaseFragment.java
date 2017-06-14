package com.kxiang.quick.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public abstract class BaseFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onActivityCreatedListener(savedInstanceState);
    }

    protected abstract void onActivityCreatedListener(Bundle savedInstanceState);

    protected ImageView title_left;
    protected TextView title_name;
    protected TextView title_right;

    //初始化title显示
    protected void initTitle() {
        title_left = (ImageView) getView().findViewById(R.id.title_left);
        title_name = (TextView) getView().findViewById(R.id.title_name);
        title_right = (TextView) getView().findViewById(R.id.title_right);
        if (title_left != null) {
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    switch (v.getId()) {
                        case R.id.title_left:
                            leftFinish();
                            break;
                        case R.id.title_right:
                            rightListener();
                            break;

                    }
                }
            };
            title_left.setOnClickListener(onClickListener);
            if (title_right != null)
                title_right.setOnClickListener(onClickListener);
        }

    }


    /**
     * 如需操作title左边按键执行该方法
     */
    protected void leftFinish() {
        getActivity().finish();
    }

    /**
     * 如需操作title右边按键执行该方法
     */
    protected void rightListener() {

    }


}
