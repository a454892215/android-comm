package com.common.utils;

import com.common.comm.L;
import com.common.widget.trend.MyPoint;

/**
 * Author:  L
 * CreateDate: 2019/1/29 9:27
 * Description: No
 */
@SuppressWarnings("unused")
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
            //如果第1,2根线段都是是水平的
            if (y1 - y0 == 0 && y3 - y2 == 0) {
                //判断第2根线段的y取值 是否在第一根线段y区间
                LogUtil.d("========1,2根线段都是是水平的=========");
                return null;
            }
            //如果第1根线段是水平的
            if (y1 - y0 == 0) {
                String crossPoint = getCrossPointForOneHorizontal(y0, x2, y3, x3, y3);
                LogUtil.d("========第1根线段是水平的===:" + crossPoint);
                return crossPoint;
            }
            //如果第2根线段是水平的
            if (y3 - y2 == 0) {
                String crossPoint = getCrossPointForOneHorizontal(y3, x0, y0, x1, y1);
                LogUtil.d("========第2根线段是水平的=========:" + crossPoint);
                return crossPoint;
            }

            float a = y1 - y0;
            float b = x1 * y0 - x0 * y1;
            float c = x1 - x0;
            float d = y3 - y2;
            float e = x3 * y2 - x2 * y3;
            float f = x3 - x2;
            float y = (a * e - b * d) / (a * f - c * d);
            float x = (y * c - b) / a;

            float minX = x0 < x1 ? x0 : x1;
            minX = minX < x2 ? minX : x2;
            minX = minX < x3 ? minX : x3;

            float maxX = x0 > x1 ? x0 : x1;
            maxX = maxX > x2 ? maxX : x2;
            maxX = maxX > x3 ? maxX : x3;


            if (x >= minX && x <= maxX) {
                return x + L.split + y + L.split + (a / c);
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

    //直线公式 y = kx +b
    public static String getKAndBForLine(float x0, float y0, float x1, float y1) {
        if (x1 - x0 == 0) {
            return null;
        }
        float k = y1 - y0 / x1 - x0;
        float b = y0 - k * x0;
        return k + L.split + b;
    }


    /**
     * 当两根线段 其中一根水平的情况 获取交点坐标
     */
    private static String getCrossPointForOneHorizontal(float horizontalLineY, float x1, float y1, float x2, float y2) {
        //判断第2根线段的y取值 是否穿过第一根线段
        float line2MinY = y1 < y2 ? y1 : y2; //线段2最小Y
        float line2MaxY = y1 > y2 ? y1 : y2; //线段2最大Y
        if (horizontalLineY >= line2MinY && horizontalLineY <= line2MaxY) { //相交
            //获取Y2 K和B
            String kAndBForLine = getKAndBForLine(x1, y1, x2, y2);
            if (kAndBForLine != null) {
                float line2K = FloatUtil.getF1(kAndBForLine);
                float line2B = FloatUtil.getF2(kAndBForLine);
                float crossPointX = (horizontalLineY - line2B) / line2K;
                return crossPointX + L.split + horizontalLineY + L.split + line2K; //当第一根线水平 则返回第二根线的斜率
            } else {
                LogUtil.d("============发生异常===========");
            }

        } else {
            return null;
        }
        return null;
    }

}

