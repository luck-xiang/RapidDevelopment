package com.kexiang.function.view.own;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/6 9:02
 */

public class DragRightViewUtils {

    private static DragRightViewUtils dragRightViewUtils;
    private boolean rightShow;
    private boolean select ;

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public DragRightViewLayout showItem;

    public DragRightViewLayout getShowItem() {
        return showItem;
    }

    public void setShowItem(DragRightViewLayout showItem) {
        this.showItem = showItem;
    }

    public DragRightViewUtils() {
        select = false;
    }

    public static DragRightViewUtils getDragRightViewUtils() {

        if (dragRightViewUtils == null) {
            synchronized (DragRightViewUtils.class) {
                dragRightViewUtils = new DragRightViewUtils();
            }
        }
        return dragRightViewUtils;
    }

    public boolean isRightShow() {
        return rightShow;
    }

    public void setRightShow(boolean rightShow) {
        this.rightShow = rightShow;
    }
}
