package com.kxiang.quick.function.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.own.star.StarLikesView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class StarPraiseActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_star_praise);
        initVelocityTracker();
    }

    /**
     * 星星加
     */
    private Button btn_add;
    /**
     * 星星减
     */
    private Button btn_sub;
    private StarLikesView star_normal;
    private StarLikesView star_scale;
    private StarLikesView star_rotate;

    @Override
    protected void initView() {


        final List<String> listData = new ArrayList<>();
        ListView listView = new ListView(this);
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listData.get(position);
            }
        });

        title_bar.setTitleName("星星点赞");
        initStatusBarColor();
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(this);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_sub.setOnClickListener(this);
        star_normal = (StarLikesView) findViewById(R.id.star_normal);
        star_normal.setOnSelectListener(new StarLikesView.OnSelectListener() {
            @Override
            public void onSelect(int select) {
                showToastShort("选中：" + select);
            }
        });
        star_scale = (StarLikesView) findViewById(R.id.star_scale);
        star_rotate = (StarLikesView) findViewById(R.id.star_rotate);
        star_scale.setStytle(StarLikesView.SCALE);
        star_rotate.setStytle(StarLikesView.ROTATE);

        ValueAnimator animator = ValueAnimator.ofInt(0, 400, 0, 200);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                star_normal.addStar();
                star_scale.addStar();
                star_rotate.addStar();
                break;
            case R.id.btn_sub:
                star_normal.subStar();
                star_scale.subStar();
                star_rotate.subStar();
                break;
        }
    }

    // velocityTracker.computeCurrentVelocity(1, (float)0.01);
    // 设置maxVelocity值为0.1时，速率大于0.01时，显示的速率都是0.01,速率小于0.01时，显示正常
    private VelocityTracker mVelocityTracker;
    private int mMaxVelocity;
    private int mPointerId;
    private TextView tv_show;

    private void initVelocityTracker() {

        mVelocityTracker = VelocityTracker.obtain();
        mMaxVelocity = ViewConfiguration.get(this).getMaximumFlingVelocity();
        tv_show = (TextView) findViewById(R.id.tv_show);

        LogUtils.toE("initVelocityTracker","mMaxVelocity:"+mMaxVelocity);
        LogUtils.toE("initVelocityTracker",
                "Minimum:" + ViewConfiguration.get(this).getScaledMinimumFlingVelocity());
        LogUtils.toE("initVelocityTracker",
                "Maximum:" + ViewConfiguration.get(this).getScaledMaximumFlingVelocity());

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        acquireVelocityTracker(event);
        final VelocityTracker verTracker = mVelocityTracker;
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //求第一个触点的id， 此时可能有多个触点，但至少一个
                mPointerId = event.getPointerId(0);
                break;

            case MotionEvent.ACTION_MOVE:
                //求伪瞬时速度
                verTracker.computeCurrentVelocity(1000, mMaxVelocity);
                final float velocityX = verTracker.getXVelocity(mPointerId);
                final float velocityY = verTracker.getYVelocity(mPointerId);
                recodeInfo(velocityX, velocityY);
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                releaseVelocityTracker();
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    //释放VelocityTracker

    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    private static final String sFormatStr = "velocityX=%f\nvelocityY=%f";

    /**
     * 记录当前速度
     *
     * @param velocityX x轴速度
     * @param velocityY y轴速度
     */
    private void recodeInfo(final float velocityX, final float velocityY) {
        final String info = String.format(sFormatStr, velocityX, velocityY);
        tv_show.setText(info);
    }

}
