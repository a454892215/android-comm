package com.common.http;

/**
 * Author:  L
 * CreateDate: 2019/1/18 9:21
 * Description: No
 */

public interface HttpCallback {
    void onSuccess(String text);

    void onFail(Throwable e);
}
