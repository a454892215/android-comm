package com.java_comm.entity;


/**
 * Author: Pan
 * 2022/2/14
 * Description:
 */
public class SizeEntity {
    private final byte[] size_1M = new byte[1024 * 1024];

    public void init() {
        int length = size_1M.length;
        size_1M[0] = 1;
        size_1M[length - 1] = 2;
    }

}
