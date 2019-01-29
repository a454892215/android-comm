package com.common.widget.trend;

import android.graphics.Point;

/**
 * Author:  Pan
 * CreateDate: 2019/1/29 9:27
 * Description: No
 */

public class CoordinateComputeHelper {
    /**
     * 获取直线和圆的交点
     */
    public static Point[] getIntersection(Point start, Point end, Point cC, float r) {
        float k = (start.y - end.y) / (float) (start.x - end.x);
        float b = start.y - k * start.x;
        float c = cC.x;
        float d = cC.y;
        float kk = k * k;
        float bb = b * b;
        float cc = c * c;
        float dd = d * d;
        float rr = r * r;
        float bc2 = 2 * b * c;
        float bd2 = 2 * b * d;
        float cd2 = 2 * c * d;
        float comX = (float) Math.sqrt((kk + 1) * rr - cc * kk + (cd2 + bc2) * k - dd - bd2 - bb);
        float x1 = -(comX + (d + b) * k + c) / (kk + 1);
        float x2 = (comX + (-d - b) * k - c) / (kk + 1);

        float cdk2 = 2 * c * d * k;
        float bck2 = 2 * b * c * k;
        float comY = (float) Math.sqrt(kk * rr + rr - rr * kk + cdk2 + bck2 - dd - bd2 - bb);
        float y1 = -(k * (comY + c) + d * kk - b) / (kk + 1);
        float y2 = (k * (c - comY) + d * kk - b) / (kk + 1);
        if (x1 < x2) {
            return new Point[]{new Point(Math.round(x1), Math.round(y1)), new Point(Math.round(x2), Math.round(y2))};
        } else {
            return new Point[]{new Point(Math.round(x2), Math.round(y2)), new Point(Math.round(x1), Math.round(y1))};
        }
    }
}

