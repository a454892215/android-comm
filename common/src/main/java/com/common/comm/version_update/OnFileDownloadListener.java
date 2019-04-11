package com.common.comm.version_update;

import java.io.File;

public interface OnFileDownloadListener {

    void onStart();
    void onProgress(float progress, long total);
    void onCompleted(File file);
    void onError(Throwable throwable);


}

