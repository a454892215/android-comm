package com.common.comm.version_update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Environment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.common.CommApp;
import com.common.R;
import com.common.utils.LogUtil;
import com.common.utils.SharedPreUtils;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.xuexiang.xupdate.utils.ApkInstallUtils;

import java.io.File;
import java.util.Objects;

/**
 * Author: L
 * CreateDate: 2018/9/1 9:50
 * Description: No
 */

public class VersionUpdateHelper {
    private static final String key_apk_download_path = "key_apk_download_path";

    public void showUpdateAppDialog(Activity activity, String apkUrl, boolean isForceUpdate, String newAppMD5, OnClickCancel onClickCancel) {
        @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(R.layout.layout_version_update, null);
        LinearLayout llt_no_force_update = view.findViewById(R.id.llt_no_force_update);
        TextView btn_yes = view.findViewById(R.id.btn_yes_1);
        TextView btn_yes_2 = view.findViewById(R.id.btn_yes_2);
        TextView btn_no_2 = view.findViewById(R.id.btn_no_2);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity).setView(view);
        AlertDialog alertDialog = builder.create();
        View.OnClickListener onYesClickListener = v -> {
            alertDialog.dismiss();
            onClickUpdate(activity, apkUrl, newAppMD5, null);
        };
        if (isForceUpdate) {
            llt_no_force_update.setVisibility(View.GONE);
            btn_yes.setVisibility(View.VISIBLE);
            builder.setCancelable(false);
            btn_yes.setOnClickListener(onYesClickListener);
        } else {
            llt_no_force_update.setVisibility(View.VISIBLE);
            btn_yes.setVisibility(View.GONE);
            btn_yes_2.setOnClickListener(onYesClickListener);
            btn_no_2.setOnClickListener(v -> {
                alertDialog.dismiss();
                if (onClickCancel != null) onClickCancel.onClickCancel();
            });
        }
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(android.R.color.transparent)));
        }
        alertDialog.show();
    }

    private boolean checkApkIsDownLoaded(String md5) {
        String apkPath = SharedPreUtils.getString(key_apk_download_path, "");
        if (!TextUtils.isEmpty(apkPath)) {
            File apkFile = new File(apkPath);
            if (apkFile.exists()) {//下载的apk文件还存在
                String fileMD5 = Md5Utils.getFileMD5(apkFile);
                if (!TextUtils.isEmpty(md5) && md5.equals(fileMD5)) {//新版本的apk文件已经下载完毕，还存在
                    LogUtil.d("已经下载完毕的apk文件路径是：" + apkFile.getAbsolutePath());
                    return true;
                } else {
                    LogUtil.e("apk文件虽然还存在 但是md5值验证失败，准备提示下载");
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 如果不进行MD5值校验 newAppMD5传null即可
     */
    public void onClickUpdate(Activity activity, String apkUrl, String newAppMD5, OnDownloadFinish onDownloadFinish) {
        //判断文件是否已经下载完毕：
        boolean apkIsDownloaded = checkApkIsDownLoaded(newAppMD5);
        LogUtil.d("===============apkUrl:" + apkUrl + " apkIsDownloaded:" + apkIsDownloaded);
        if (apkIsDownloaded) {
            try {
                File apkFile = new File(SharedPreUtils.getString(key_apk_download_path, ""));
                ApkInstallUtils.install(CommApp.app, apkFile);
            } catch (Exception e) {
                LogUtil.e(e);
                ToastUtil.show(CommApp.app,"安装新版本Apk失败");
            }
        } else {
            downloadApk(activity, apkUrl, newAppMD5, onDownloadFinish);
        }
    }

    private void installJustDownloadFinishedApk(File file, String absolutePath) {
        try {
            ApkInstallUtils.install(CommApp.app, file);
            SharedPreUtils.putString(key_apk_download_path, absolutePath);
        } catch (Exception e) {
            LogUtil.e(e);
        }
    }

    private void downloadApk(Activity activity, String apkUrl, String md5, OnDownloadFinish onDownloadFinish) {
        OnFileDownloadListener listener = new OnFileDownloadListener() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = showProgressDialog(activity);
            }

            @Override
            public void onProgress(float progress, long total) {
                int round = Math.round(progress / total * 100);
                LogUtil.d("==========文件下载进度:" + round);
                if (dialog != null) dialog.setProgress(round);
            }

            @Override
            public void onCompleted(File file) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                String fileMD5 = Md5Utils.getFileMD5(file);
                boolean isSameMd5 = !TextUtils.isEmpty(md5) && md5.equals(fileMD5);
                String absolutePath = file.getAbsolutePath();
                LogUtil.d("文件下载完毕，下载文件的路径是：" + absolutePath + " 下载文件的md5是否和期待值相同：" + isSameMd5);
                //安装刚刚下载完毕的apk 不进行md5值验证
                installJustDownloadFinishedApk(file, absolutePath);
                if (onDownloadFinish != null) {
                    onDownloadFinish.onDownloadFinish();
                }

            }

            @Override
            public void onError(Throwable throwable) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                LogUtil.e("下载新版本apk文件发生错误:" + StringUtil.getThrowableInfo(throwable));
                ToastUtil.show(CommApp.app,"下载新版本apk文件发生错误:" + throwable);
            }
        };
        String downloadFileSaveFullPath = getDownloadFileSaveFullPath(activity);
        FileDownloadHelper.load(apkUrl, downloadFileSaveFullPath, listener);
    }

    private ProgressDialog showProgressDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.setMessage("下载进度");
        dialog.setOnKeyListener((dialog1, keyCode, event) -> true);
        dialog.show();
        return dialog;
    }


    private String getDownloadDirPath(Context context) {
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果存在外置储存空间
            try {
                path = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
                LogUtil.d("======1===下载apk保存目录为External缓存目录：" + path);
            } catch (Exception e) {
                LogUtil.e(e);
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                LogUtil.d("======2===下载apk保存目录为外部公共储存目录：" + path);
            }
        } else {//如果不存在
            path = context.getCacheDir().getAbsolutePath();
            LogUtil.d("======3===下载apk保存目录为内部缓存目录：" + path);
        }
        return path;
    }

    private String getDownloadFileSaveFullPath(Context context) {
        return getDownloadDirPath(context) + "/newApp/" + context.getPackageName().replace(".", "_") + ".apk";
    }

    public interface OnDownloadFinish {
        void onDownloadFinish();
    }

    public interface OnClickCancel {
        void onClickCancel();
    }
}
