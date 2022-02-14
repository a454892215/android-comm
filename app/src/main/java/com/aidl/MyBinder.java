package com.aidl;

import com.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Pan
 * 2022/2/14
 * Description:
 */
public class MyBinder extends IMyAidlInterface.Stub {


    @Override
    public void basicTypes(int aInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) {

    }

    @Override
    public int getValue(int value) {
        return list.size();
    }

    @Override
    public String getName(String value) {
        return "传入的值是:" + value;
    }

    @Override
    public void test(String value) {
        doTask();
    }

    private final List<SizeEntity> list = new ArrayList<>();

    private void doTask() {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                list.add(new SizeEntity());
            }
        });
        thread.start();
    }
}
