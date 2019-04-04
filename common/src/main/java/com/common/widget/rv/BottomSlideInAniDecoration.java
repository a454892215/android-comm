package com.common.widget.rv;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;

import java.util.ArrayList;
import java.util.List;

public class BottomSlideInAniDecoration extends RecyclerView.ItemDecoration {

    private RecyclerView parent;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (this.parent == null) this.parent = parent;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        playAnimation();
    }

    private List<Integer> animatedList = new ArrayList<>();

    private int indexPerAni = 0;

    private void playAnimation() {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View itemView = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(itemView);
            if (!animatedList.contains(position)) {
                animatedList.add(position);
                itemView.setAlpha(0);
                itemView.postDelayed(() -> {
                    int current_po = parent.getChildAdapterPosition(itemView);
                    if (current_po == position) {
                        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_SELF, 0);
                        animation.setInterpolator(new DecelerateInterpolator());
                        animation.setDuration(aniDuring);
                        itemView.clearAnimation();
                        itemView.setAlpha(1);
                        itemView.startAnimation(animation);
                    } else {
                        itemView.clearAnimation();
                    }
                }, Math.round(indexPerAni * aniDuring * 0.6f + 50));
                indexPerAni = indexPerAni + 1;
            } else {
                indexPerAni = 0;
            }
        }
    }

    public void restartAnim() {
        animatedList.clear();
        playAnimation();
    }

    private long aniDuring = 300;

    @SuppressWarnings("unused")
    public void setAniDuring(long aniDuring) {
        this.aniDuring = aniDuring;
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }
}
