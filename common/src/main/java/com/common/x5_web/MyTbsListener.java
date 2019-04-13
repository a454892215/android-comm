package com.common.x5_web;

import com.common.utils.LogUtil;
import com.tencent.smtt.sdk.TbsListener;

public class MyTbsListener implements TbsListener {
    @Override
    public void onDownloadFinish(int i) {
        LogUtil.d("=========================onDownloadFinish" + i);
    }

    @Override
    public void onInstallFinish(int i) {
        LogUtil.d("=========================onInstallFinish" + i);
    }

    @Override
    public void onDownloadProgress(int i) {
        LogUtil.d("==============================onDownloadProgress" + i);
    }
}
