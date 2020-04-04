package com.common.utils;

import com.common.widget.trend.MyPoint;

/**
 * Author:  L
 * CreateDate: 2019/1/29 9:27
 * Description: No
 */

public class GeometryUtil {
    /**
     * Intersection of a line and a circle
     */
    static MyPoint[] getIntersection(MyPoint start, MyPoint end, MyPoint cC, float r) {
        float k = (start.y - end.y) / (start.x - end.x);
        float b = start.y - k * start.x;
        float c = -cC.x;
        float d = -cC.y;
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
        float comY = (float) Math.sqrt(kk * rr + rr - cc * kk + cdk2 + bck2 - dd - bd2 - bb);
        float y1 = -(k * (comY + c) + d * kk - b) / (kk + 1);
        float y2 = -(k * (c - comY) + d * kk - b) / (kk + 1);
        if (x1 < x2) {
            return new MyPoint[]{new MyPoint(Math.round(x1), Math.round(y1)), new MyPoint(Math.round(x2), Math.round(y2))};
        } else {
            return new MyPoint[]{new MyPoint(Math.round(x2), Math.round(y2)), new MyPoint(Math.round(x1), Math.round(y1))};
        }
    }

    /**
     * 获取两直线交点坐标
     * float x0, float y0, float x1, float y1 直线1
     * float x2, float y2, float x3, float y3 直线2
     *
     * @return 交点
     */
    public static String getIntersectionForTowLine(float x0, float y0, float x1, float y1, float x2, float y2, float x3, float y3) {
        try {
/*            float y = ((y0 - y1) * (y3 - y2) * x0 + (y3 - y2) * (x1 - x0) * y0 + (y1 - y0) * (y3 - y2) * x2 + (x2 - x3) * (y1 - y0) * y2) / ((x1 - x0) * (y3 - y2) + (y0 - y1) * (x3 - x2));
            float x = x2 + (x3 - x2) * (y - y2) / (y3 - y2);
        */

            float a = y1-y0;
            float b = x1*y0-x0*y1;
            float c = x1-x0;
            float d = y3-y2;
            float e = x3*y2-x2*y3;
            float f = x3-x2;
            float y = (a*e-b*d)/(a*f-c*d);
            float x = (y*c-b)/a;

            float minX = x0 < x1 ? x0 : x1;
            minX = minX < x2 ? minX : x2;
            minX = minX < x3 ? minX : x3;

            float maxX = x0 > x1 ? x0 : x1;
            maxX = maxX > x2 ? maxX : x2;
            maxX = maxX > x3 ? maxX : x3;

            if (x >= minX && x <= maxX) {
                return x + "-" + y;
            }

/*            float minY = y0 < y1 ? y0 : y1;
            minY = minY < y2 ? minY : y2;
            minY = minY < y3 ? minY : y3;

            float maxY = y0 > y1 ? y0 : y1;
            maxY = maxY > y2 ? maxY : y2;
            maxY = maxY > y3 ? maxY : y3;


            if (y >= minY && y <= maxY) { //防止精度损失 遗漏数据
                   return x + "-" + y;
            }*/
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return null;
    }

}

