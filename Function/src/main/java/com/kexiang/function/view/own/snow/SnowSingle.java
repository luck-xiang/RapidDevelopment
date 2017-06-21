package com.kexiang.function.view.own.snow;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import java.util.Random;


/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/19 15:53
 */

public class SnowSingle {
    private Random random;
    private Point point;
    private int size;
    private int heiht;
    private int width;
    private int maxSiz;
    private int minSize;
    private int falling;
    private int rightAndLeft;
    private int index = 100;
    private int alpha;

    private int paddingLeft;
    private int paddingRight;
    private int paddingTop;
    private int paddingBotton;


    public SnowSingle(Random random, int startX, int startY, int maxSiz, int minSize,
                      int paddingLeft, int paddingRight, int paddingTop, int paddingBotton) {

        this.paddingLeft = paddingLeft;
        this.paddingRight = paddingRight;
        this.paddingTop = paddingTop;
        this.paddingBotton = paddingBotton;

        this.random = random;
        width = startX;
        heiht = startY;
        this.maxSiz = maxSiz;
        this.minSize = minSize;
        point = new Point();


        initSnow();
//        point.set(random.nextInt(width), -random.nextInt(heiht));
        point.set(getWidth(), -random.nextInt(heiht));
    }

    private int getWidth() {
        return random.nextInt(width - paddingLeft - paddingRight) + paddingLeft + size / 2;
    }

//    private int getHeiht() {
//        return random.nextInt(width - paddingTop - paddingBotton) + paddingTop;
//    }


    private void initSnow() {
//        point.set(random.nextInt(width), 0);
        point.set(getWidth(), 0);
        size = random.nextInt(maxSiz);
        if (size < minSize) {
            size = minSize;
        }
        falling = 1;
        rightAndLeft = 1;
        alpha = random.nextInt(156) + 100;
        resetMoveXboolean();
//        LogUtils.toE("random:", "alpha:" + alpha);
//        LogUtils.toE("random:", "falling:" + falling);
//        LogUtils.toE("random:", "rightAndLeft:" + (paddingLeft + size));
//        LogUtils.toE("random:", "rightAndLeft:" + paddingLeft);
//        LogUtils.toE("random:", "rightAndLeft:" + size);
    }

    public void move(Canvas canvas, Paint paint) {

        moveX(rightAndLeft);
        moveY(falling);
        paint.setAlpha(alpha);
        canvas.drawCircle(point.x, point.y, size, paint);
    }

    private boolean moveXboolean;

    private void resetMoveXboolean() {
        switch (random.nextInt(2)) {
            case 0:
                moveXboolean = true;
                break;
            case 1:
                moveXboolean = false;
                break;
        }
    }

    private void moveX(int x) {

        if (!moveXboolean) {
            x = -x;
        }
        int tempX = point.x + x;
        if (tempX < (width - paddingRight - size / 2) && tempX > (paddingLeft + size / 2)) {
            point.offset(x, 0);
        }
        else if (tempX <= (paddingLeft + size / 2)) {

            point.set(paddingLeft + size / 2, point.y);
        }
        else {
            point.set(width - paddingRight - size / 2, point.y);
        }
        index--;
        if (index < 0) {
            index = 100;
            resetMoveXboolean();
        }
    }

    private void moveY(int y) {
        if (point.y + y < heiht) {
            point.offset(0, y);
        }
        else if (point.y + y >= heiht) {
            initSnow();
        }
    }


}
