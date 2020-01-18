package com.common.comm.version_update;

import com.common.CommApp;
import com.common.utils.LogUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.File;

/**
 * Author: L
 * CreateDate: 2018/9/2 14:18
 * Description: No
 */

public class FileDownloadHelper {

    public static void load(String fileUrl, String fileFullName, OnFileDownloadListener onFileDownloadListener) {
        FileDownloader.setup(CommApp.app);
        BaseDownloadTask baseDownloadTask = FileDownloader.getImpl().create(fileUrl);
        baseDownloadTask.setWifiRequired(false)
                .setPath(fileFullName)
                .setForceReDownload(true)
                .setListener(new FileDownloadListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {//等待，已经进入下载队列
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        //int percent=(int) ((double) soFarBytes / (double) totalBytes * 100);
                        onFileDownloadListener.onProgress(soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        String path = task.getPath();
                        File file = new File(path);
                        LogUtil.d("文件下载完毕：path" + path + "  size:" + file.length() / 1024 + "kb");
                        if (file.exists()) {
                            onFileDownloadListener.onCompleted(file);
                        } else {
                            onFileDownloadListener.onError(new Throwable("文件下载完毕，发生异常"));
                        }

                    }

                    @Override
                    protected void started(BaseDownloadTask task) {
                        super.started(task);
                        onFileDownloadListener.onStart();
                        LogUtil.d("下载文件开始任务");
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        LogUtil.d("下载文件被暂停");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.e(e);
                        onFileDownloadListener.onError(e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        LogUtil.d(" warn taskId:" + task.getId());
                    }
                }).start();

    }

}
