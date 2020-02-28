package com.common.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import com.common.R;
import com.common.comm.L;
import com.common.comm.timer.MyTimer;
import com.common.utils.LogUtil;
import com.common.utils.ViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * CreateDate:
 * Description: 红包雨
 */

public class HongBaoYuView extends View {

    private int item_height;
    private MyTimer timer;

    private int moveMinUnit;
    private static final int max_hong_bao_count = 50; //两屏的红包雨数目

    private List<HongBaoItem> hongBaoList = new ArrayList<>();
    private int item_width;
    private Bitmap bitmap;

    public HongBaoYuView(Context context) {
        this(context, null);
    }

    public HongBaoYuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HongBaoYuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        moveMinUnit = Math.round(L.dp_1 / 3);
        if (moveMinUnit < 1) moveMinUnit = 1;

        BitmapDrawable drawable_bao_piao = (BitmapDrawable) context.getResources().getDrawable(R.drawable.ic_hong_bao_piao);
        item_width = Math.round(L.dp_1 * 30);
        item_height = Math.round(ViewUtil.getPicHeightByWidth(drawable_bao_piao, item_width));
        bitmap = Bitmap.createScaledBitmap(drawable_bao_piao.getBitmap(),item_width,item_height,false);
        timer = new MyTimer(Integer.MAX_VALUE, 16);
        timer.setOnTickListener((millisUntilFinished, executeCount) -> {
            int size = hongBaoList.size();
            for (int i = 0; i < size; i++) {
                HongBaoItem hongBaoItem = hongBaoList.get(i);
                hongBaoItem.currentTop += moveMinUnit;
                hongBaoItem.currentRotate += 1;
                if (hongBaoItem.currentRotate > 360) {
                    hongBaoItem.currentRotate = 0;
                }
                if (hongBaoItem.currentTop > getHeight()) {//出屏
                    hongBaoItem.currentTop = getInitTop();
                }
            }
            postInvalidate();
        });
        postDelayed(() -> {
            initHongBaoItem();
            timer.start();
        }, 250);
        srcRect.left = 0;
        srcRect.right = this.bitmap.getWidth();
        srcRect.top = 0;
        srcRect.bottom = this.bitmap.getHeight();
    }

    private int getInitTop() {
        return -new Random().nextInt(getHeight() * 2 + item_height);
    }

    private void initHongBaoItem() {
        for (int i = 0; i < max_hong_bao_count; i++) {
            HongBaoItem hongBaoItem = new HongBaoItem();
            hongBaoItem.width = item_width;
            hongBaoItem.height = item_height;
            hongBaoItem.currentLeft = new Random().nextInt(getWidth() - item_width);
            hongBaoItem.currentTop = getInitTop();
            hongBaoItem.currentRotate = new Random().nextInt(360);
            hongBaoList.add(hongBaoItem);
        }
    }

    private RectF srcRect = new RectF();
    private RectF rectf = new RectF();
    private Paint paint = new Paint();

    protected void onDraw(Canvas canvas) {
        int size = hongBaoList.size();
        for (int i = 0; i < size; i++) { //一次性绘制N张红包
            HongBaoItem hongBaoItem = hongBaoList.get(i);
            rectf.top = hongBaoItem.currentTop;
            rectf.left = hongBaoItem.currentLeft;
            rectf.bottom = hongBaoItem.currentTop + hongBaoItem.height;
            rectf.right = hongBaoItem.currentLeft + hongBaoItem.width;
            hongBaoItem.matrix.setRectToRect(srcRect, rectf, Matrix.ScaleToFit.CENTER);
            hongBaoItem.matrix.preRotate(hongBaoItem.currentRotate, hongBaoItem.width/2f, hongBaoItem.height/2f);
            canvas.drawBitmap(bitmap, hongBaoItem.matrix, paint);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtil.d("================onDetachedFromWindow:");
        if (timer != null) {
            timer.cancel();
        }
    }

    private static class HongBaoItem {
        private int currentLeft;
        private int currentTop;
        private float currentRotate;
        private int width;
        private int height;
        Matrix matrix = new Matrix();
    }
}
