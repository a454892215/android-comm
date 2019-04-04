package com.common.widget.rv;

import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseItemAnimator extends SimpleItemAnimator {

    private List<RecyclerView.ViewHolder> oldChangeHolderList = new ArrayList<>();
    private List<RecyclerView.ViewHolder> newChangeHolderList = new ArrayList<>();
    @Override
    public boolean animateRemove(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public boolean animateAdd(RecyclerView.ViewHolder holder) {
        return false;
    }

    @Override
    public boolean animateMove(RecyclerView.ViewHolder holder, int fromX, int fromY, int toX, int toY) {
        LogUtil.d("======animateMove============tv:");
        return false;
    }

    @Override
    public boolean animateChange(RecyclerView.ViewHolder oldHolder, RecyclerView.ViewHolder newHolder, int fromLeft, int fromTop, int toLeft, int toTop) {
        oldChangeHolderList.add(oldHolder);
        newChangeHolderList.add(newHolder);
        TextView tv = (TextView) oldHolder.itemView;
        LogUtil.d("======animateChange============tv:" + tv.getText());
        return true;
    }

    @Override
    public void runPendingAnimations() {
        LogUtil.d("======runPendingAnimations============:");
        for (int i = 0; i < oldChangeHolderList.size(); i++) {

            ViewPropertyAnimatorCompat animate = ViewCompat.animate(oldChangeHolderList.get(i).itemView);
            animate.setDuration(200).setInterpolator(new DecelerateInterpolator());
           // dispatchChangeStarting(oldChangeHolderList.get(i), true);
            dispatchChangeStarting(newChangeHolderList.get(i), false);
        }

    }

    @Override
    public void endAnimation(RecyclerView.ViewHolder item) {
        LogUtil.d("======endAnimation============1:");
        item.itemView.clearAnimation();
    }

    @Override
    public void endAnimations() {
        LogUtil.d("======endAnimations============2:");
    }

    @Override
    public boolean isRunning() {
        LogUtil.d("======isRunning============2:");
        return true;
    }


}
