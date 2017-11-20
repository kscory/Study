package com.example.kyung.recyclerviewexample;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

/**
 * Created by Kyung on 2017-11-20.
 */

public class CustomItemAnimator extends DefaultItemAnimator {

    @Override
    public boolean animateRemove(final RecyclerView.ViewHolder holder) {
        View view = holder.itemView;

        ObjectAnimator ani = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 300).setDuration(1000);
        ani.setInterpolator(new DecelerateInterpolator());
        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchRemoveFinished(holder);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        ani.start();
        return true;
    }

    @Override
    public boolean animateAdd(final RecyclerView.ViewHolder holder) {
        View view = holder.itemView;

        ObjectAnimator ani = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 300).setDuration(1000);
        ani.setInterpolator(new DecelerateInterpolator());
        ani.start();
        ani.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                dispatchAddFinished(holder);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        return super.animateMove(holder, fromX, fromY, toX, toY);
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromX, int fromY, int toX, int toY) {
        return super.animateChange(oldHolder, newHolder, fromX, fromY, toX, toY);
    }

    @Override
    public void runPendingAnimations() {
        super.runPendingAnimations();
        Log.e("실행","======================================================");
    }
}
