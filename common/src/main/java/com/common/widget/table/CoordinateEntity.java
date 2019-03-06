package com.common.widget.table;

/**
 * Author:  Pan
 * Description: No
 */

public class CoordinateEntity {

    private float X;
    private float Y;

    public  CoordinateEntity(float x, float y) {
        X = x;
        Y = y;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    @Override
    public String toString() {
        return "CoordinateEntity{" +
                "X=" + X +
                ", Y=" + Y +
                '}';
    }
}
