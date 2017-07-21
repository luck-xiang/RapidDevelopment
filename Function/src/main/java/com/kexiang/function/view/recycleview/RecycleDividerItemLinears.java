package com.kexiang.function.view.recycleview;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kexiang.function.utils.LogUtils;

/**
 * RecyclerView的分割线
 * LinearLayoutManager
 */
public class RecycleDividerItemLinears extends RecyclerView.ItemDecoration {

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        LogUtils.toE("onDraw","state:"+state.getItemCount());
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        LogUtils.toE("onDraw","state:"+state.getItemCount());
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        LogUtils.toE("onDraw","state:"+state.getItemCount());
    }

}
