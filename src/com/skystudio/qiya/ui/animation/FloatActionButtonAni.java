package com.skystudio.qiya.ui.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by Administrator on 2016/5/9.
 */
public class FloatActionButtonAni {
    /**
     * 显示Veiw
     *
     * @param view 要显示的View
     * @param to   显示的结束位置
     */
    @TargetApi(14)
    public static void showView(View view, float to) {
        view.setVisibility(View.VISIBLE);
        //垂直方向动画
        ObjectAnimator anim2 = ObjectAnimator.ofFloat(view, "TranslationY", 0, to);
        anim2.setDuration(1000);
        anim2.setInterpolator(new OvershootInterpolator());
        anim2.start();
        ObjectAnimator.ofFloat(view, View.ALPHA, 0, 1).setDuration(1000).start();
        //旋转动画        ObjectAnimator.ofFloat(view,"rotation",0,360*50).setDuration(10000).start();

    }

    /**
     * 隐藏View
     *
     * @param view 要隐藏的View
     * @param from View初始位置
     */
    @TargetApi(14)
    public static void hideView(final View view, float from) {
        ObjectAnimator anim = ObjectAnimator.ofFloat(view, "TranslationY", from, 0);
        anim.setDuration(1000);
        //anim.setInterpolator(new OvershootInterpolator());
        anim.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                //【重要】在动画完成时将可见性设置为GONE，佛则，直接设置为不可见将不能看到动画
                //view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        anim.start();

        ObjectAnimator.ofFloat(view, View.ALPHA, 1, 0).setDuration(1000).start();
        //ObjectAnimator.ofFloat(view, "rotation", 0, 360 * 30).setDuration(1000).start();
    }

}
