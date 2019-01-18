package com.common.http;

/**
 * Author:  Pan
 * CreateDate: 2019/1/18 9:21
 * Description: No
 */

public interface HttpCallback {
    void onSuccess(String text);

    void onFail();
}
