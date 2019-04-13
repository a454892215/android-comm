package com.common.x5_web;

import android.content.Intent;
import android.net.Uri;

import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.tencent.smtt.sdk.DownloadListener;

public class MyDownloadListener implements DownloadListener {
    private BaseActivity activity;

    MyDownloadListener(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
        LogUtil.d("=========onDownloadStart==========url:" + url + " userAgent" + userAgent +
                " contentDisposition:" + contentDisposition + " mimeType:" + mimeType + "  contentLength:" + contentLength);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        activity.startActivity(intent);
    }
}
