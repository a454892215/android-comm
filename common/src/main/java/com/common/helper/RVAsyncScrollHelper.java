package com.common.helper;

import android.support.v7.widget.RecyclerView;

/**
 * Author:  Pan
 * CreateDate: 2018/12/21 17:41
 * Description: No
 */

public class RVAsyncScrollHelper {

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;

    private RVAsyncScrollHelper(RecyclerView recyclerView1, RecyclerView recyclerView2) {
        this.recyclerView1 = recyclerView1;
        this.recyclerView2 = recyclerView2;
    }

    public static void syncScroll(RecyclerView recyclerView1, RecyclerView recyclerView2) {
        RVAsyncScrollHelper scrollHelper = new RVAsyncScrollHelper(recyclerView1, recyclerView2);
        scrollHelper.initScroll();
    }

    private int newState_1 = RecyclerView.SCROLL_STATE_IDLE;
    private int newState_2 = RecyclerView.SCROLL_STATE_IDLE;

    private void initScroll() {
        recyclerView1.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (newState_1 != RecyclerView.SCROLL_STATE_IDLE && newState_2 == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView2.setLayoutFrozen(false);
                    recyclerView2.scrollBy(dx, dy);
                    recyclerView2.setLayoutFrozen(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                newState_1 = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView2.setLayoutFrozen(false);
                }
            }
        });
        recyclerView2.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (newState_2 != RecyclerView.SCROLL_STATE_IDLE && newState_1 == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView1.setLayoutFrozen(false);
                    recyclerView1.scrollBy(dx, dy);
                    recyclerView1.setLayoutFrozen(true);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                newState_2 = newState;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    recyclerView1.setLayoutFrozen(false);
                }
            }
        });
    }
}
