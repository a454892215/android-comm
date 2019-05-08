package com.common.widget.trend;

public class Point {

    float x;
    float y;

    Point() {
    }

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }


    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }


}