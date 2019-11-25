package com.common.comm.version_update;

import android.annotation.SuppressLint;
import android.app.Activity;
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

import androidx.appcompat.app.AlertDialog;

import com.common.R;
import com.common.base.BaseActivity;
import com.common.utils.LogUtil;
import com.common.utils.SharedPreUtils;
import com.common.utils.StringUtil;
import com.common.utils.ToastUtil;
import com.common.widget.CommonTextView;
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
    private AlertDialog alertDialog;
    public static boolean isForceUpdate = false;

    public void onHasNewVersion(BaseActivity activity, long appSize, String version, String newVersionHint, boolean isForceUpdate, String appUrl, String appMD5) {
        if (TextUtils.isEmpty(appUrl)) return;
        String content;
        if (appSize <= 0) {
            content = newVersionHint;
        } else {
            content = "新版本大小：" + appSize + "\n\n" + newVersionHint;
        }
        showUpdatePrompt(activity, version, content, appUrl, isForceUpdate, appMD5);
    }

    private boolean apkIsDownloaded = false; //apk是否已经下载
    private File apkFile;

    private void showUpdatePrompt(BaseActivity activity, String newVersionName, String updateInfo, String apkUrl, boolean isForceUpdate, String md5) {
        String title = String.format("是否升级到v%s版本？", newVersionName);

        //判断文件是否已经下载完毕：
        String apkPath = SharedPreUtils.getString(key_apk_download_path, "");
        if (!TextUtils.isEmpty(apkPath)) {
            apkFile = new File(apkPath);
            if (apkFile.exists()) {//下载的apk文件还存在
                String fileMD5 = Md5Utils.getFileMD5(apkFile);
                if (!TextUtils.isEmpty(md5) && md5.equals(fileMD5)) {//新版本的apk文件已经下载完毕，还存在
                    title = String.format("新版本v%s已经下载完毕，是否更新？", newVersionName);
                    apkIsDownloaded = true;
                    LogUtil.d("已经下载完毕的apk文件路径是：" + apkFile.getAbsolutePath());
                } else {
                    apkIsDownloaded = false;
                    LogUtil.e("apk文件虽然还存在 但是md5值验证失败，准备提示下载");
                }
            }
        }
        @SuppressLint("InflateParams") View view = LayoutInflater.from(activity).inflate(R.layout.layout_version_update, null);
        LinearLayout llt_no_force_update = view.findViewById(R.id.llt_no_force_update);
        TextView tv_title = view.findViewById(R.id.tv_title);
        TextView tv_content = view.findViewById(R.id.tv_content);
        CommonTextView btn_yes = view.findViewById(R.id.btn_yes_1);
        CommonTextView btn_yes_2 = view.findViewById(R.id.btn_yes_2);
        CommonTextView btn_no_2 = view.findViewById(R.id.btn_no_2);
        tv_title.setText(title);
        tv_content.setText(updateInfo);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity)
                .setView(view);
        View.OnClickListener onYesClickListener = v -> onClickUpdate(activity, apkUrl, md5);
        if (isForceUpdate) {
            llt_no_force_update.setVisibility(View.GONE);
            btn_yes.setVisibility(View.VISIBLE);
            builder.setCancelable(false);
            btn_yes.setOnClickListener(onYesClickListener);
        } else {
            llt_no_force_update.setVisibility(View.VISIBLE);
            btn_yes.setVisibility(View.GONE);
            btn_yes_2.setOnClickListener(onYesClickListener);
            btn_no_2.setOnClickListener(v -> alertDialog.dismiss());
        }
        VersionUpdateHelper.isForceUpdate = isForceUpdate;
        alertDialog = builder.create();
        Window window = alertDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(activity.getResources().getColor(R.color.transparent)));
        }
        alertDialog.show();
    }

    private void onClickUpdate(BaseActivity activity, String apkUrl, String md5) {
        alertDialog.dismiss();
        if (apkIsDownloaded) {
            try {
                ApkInstallUtils.install(activity, apkFile);
            } catch (Exception e) {
                VersionUpdateHelper.isForceUpdate = false;
                LogUtil.e("安装新版本Apk失败");
                ToastUtil.showShort("安装新版本Apk失败");
                e.printStackTrace();
            }
        } else {
            downloadApk(activity, apkUrl, md5);
        }
    }


    private void downloadApk(BaseActivity activity, String apkUrl, String md5) {

        OnFileDownloadListener listener = new OnFileDownloadListener() {
            ProgressDialog dialog;

            @Override
            public void onStart() {
                dialog = showProgressDialog(activity);
            }

            @Override
            public void onProgress(float progress, long total) {
                if (dialog != null) dialog.setProgress(Math.round(progress / total * 100));
            }

            @Override
            public void onCompleted(File file) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                String fileMD5 = Md5Utils.getFileMD5(file);
                boolean isSameMd5 = !TextUtils.isEmpty(md5) && md5.equals(fileMD5);
                String absolutePath = file.getAbsolutePath();
                LogUtil.d("文件下载完毕，下载文件的路径是：" + absolutePath + " 下载文件的md5是否和期待值相同：" + isSameMd5);
                //安装刚刚下载完毕的apk 不进行md5值验证
                installJustDownloadFinishedApk(file, absolutePath, activity);
            }

            @Override
            public void onError(Throwable throwable) {
                if (dialog != null && dialog.isShowing()) dialog.dismiss();
                LogUtil.e("下载新版本apk文件发生错误:"+ StringUtil.getThrowableInfo(throwable));
                ToastUtil.showShort("下载新版本apk文件发生错误:" + throwable);
            }
        };
        String downloadFileSaveFullPath = getDownloadFileSaveFullPath(activity);
        FileDownloadHelper.load(apkUrl, downloadFileSaveFullPath, listener);
    }

    private ProgressDialog showProgressDialog(Activity activity) {
        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCanceledOnTouchOutside(false);
        //dialog.setProgressNumberFormat("%2dMB/%1dMB");
        dialog.setMessage("下载进度");
        dialog.show();
        return dialog;
    }

    private void installJustDownloadFinishedApk(File file, String absolutePath, BaseActivity activity) {
        try {
            ApkInstallUtils.install(activity, file);
            SharedPreUtils.putString("key_apk_download_path", absolutePath);
        } catch (Exception e) {
            LogUtil.e("安装新版apk文件异常：取消强制更新" + e.toString());
            VersionUpdateHelper.isForceUpdate = false;
            e.printStackTrace();
        }
    }

    private String getDownloadDirPath(Context context) {
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable()) {//如果存在外置储存空间
            try {
                path = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
            } catch (Exception e) {
                LogUtil.e("e：" + e.toString());
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {//如果不存在
            path = context.getCacheDir().getAbsolutePath();
        }
        return path;
    }

    private String getDownloadFileSaveFullPath(Context context) {
        return getDownloadDirPath(context) + "newApp" + context.getPackageName().replace(".", "_") + ".apk";
    }
}
