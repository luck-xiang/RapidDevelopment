package com.kexiang.function.view.calendar;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.kexiang.function.view.recycleview.OnRecycleItemClickListener;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.function.R;

import java.util.List;

/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/11 14:22
 */
public class PopDropDownBox extends PopupWindow {


    public PopDropDownBox(Context context, int width,
                          TextView showText,
                          List<String> list,
                          int position) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View mMenuView = inflater.inflate(R.layout.fun_pop_drop_down_box, null);

        //设置RedactPop弹出窗体的宽
        this.setWidth(width);
        //设置RedactPop弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //设置RedactPop弹出窗体可点击
        this.setFocusable(true);
//        //设置RedactPop弹出窗体动画效果
//        this.setAnimationStyle(R.style.redactpop_anim_style);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置RedactPop弹出窗体的背景
        this.setBackgroundDrawable(dw);

        //设置RedactPop的View
        this.setContentView(mMenuView);
        initRecycleALLView(mMenuView, context, showText, list, position);
    }

    private RecyclerView rlv_all;
    private DropDownBoxAdapter allAdapter;
    private List<String> listTxt;


    public void initRecycleALLView(View view, Context context,
                                   final TextView showText,
                                   List<String> list,
                                   int position) {

        rlv_all = (RecyclerView) view.findViewById(R.id.rlv_all);
        listTxt = list;


        LinearLayoutManager manager = new LinearLayoutManager(context);
        allAdapter = new DropDownBoxAdapter(context, listTxt);
        rlv_all.setAdapter(allAdapter);
        rlv_all.setLayoutManager(manager);
        rlv_all.addItemDecoration(
                new RecycleDividerItemLinear(context,
                        LinearLayoutManager.VERTICAL,
                        context.getResources().getColor(R.color.color_dddddd))
        );

        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {

                int year = getInt(showText.getText() + "");

                if (onItemSelcectListener != null) {
                    onItemSelcectListener.OnItemSelcect(
                            Integer.parseInt(listTxt.get(position) + "") - year

                    );
                }
                dismiss();

            }
        });
        manager.scrollToPositionWithOffset(position, 0);
    }

    private int getInt(String str) {
        return Integer.parseInt(str);

    }


    public interface OnItemSelcectListener {
        void OnItemSelcect(int item);
    }

    private OnItemSelcectListener onItemSelcectListener;

    public void setOnItemSelcectListener(OnItemSelcectListener onItemSelcectListener) {
        this.onItemSelcectListener = onItemSelcectListener;
    }
}
