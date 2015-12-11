package com.phonemate.utils;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * 项目名称：newzeroshop
 * 类描述：
 * 创建人：colorful
 * 创建时间：15/10/13 21:47
 * 修改人：colorful
 * 修改时间：15/10/13 21:47
 * 修改备注：
 */
public class ViewUtils {
    /**
     * 移动控件水平动画
     *
     * @param lastView
     * @param currView
     * @param move
     */
    public static void  cursorMoveBtnItemAnimation(View lastView, View currView, View move) {
        int currViewLeft = currView.getLeft() - move.getLeft();
        int lastViewLeft = lastView.getLeft() - move.getLeft();
        Animation anim = new TranslateAnimation(lastViewLeft, currViewLeft, 0,
                0);
        anim.setFillAfter(true);
        anim.setDuration(300);
        move.startAnimation(anim);
    }
}
