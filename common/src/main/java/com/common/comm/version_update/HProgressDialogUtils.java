package com.common.comm.version_update;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

/**
 * Created by Vector on 2016/8/12 0012.
 */
class HProgressDialogUtils {
    private static ProgressDialog sHorizontalProgressDialog;

    private HProgressDialogUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    @SuppressLint("NewApi")
    static void showHorizontalProgressDialog(Activity context) {
        cancel();

        if (sHorizontalProgressDialog == null) {
            sHorizontalProgressDialog = new ProgressDialog(context);
            sHorizontalProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            sHorizontalProgressDialog.setCanceledOnTouchOutside(false);
            //    sHorizontalProgressDialog.setProgressNumberFormat("%2dMB/%1dMB");

        }
        sHorizontalProgressDialog.setMessage("下载进度");
        sHorizontalProgressDialog.show();

    }

/*    public static void setMax(long total) {
        if (sHorizontalProgressDialog != null) {
            sHorizontalProgressDialog.setMax(((int) total) / (1024 * 1024));
        }
    }*/

    static void cancel() {
        if (sHorizontalProgressDialog != null) {
            sHorizontalProgressDialog.dismiss();
            sHorizontalProgressDialog = null;
        }
    }

    static void setProgress(int current) {
        if (sHorizontalProgressDialog == null) {
            return;
        }
        sHorizontalProgressDialog.setProgress(current);
        if (sHorizontalProgressDialog.getProgress() >= sHorizontalProgressDialog.getMax()) {
            sHorizontalProgressDialog.dismiss();
            sHorizontalProgressDialog = null;
        }
    }

}
