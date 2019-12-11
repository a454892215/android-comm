package com.common.template;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Message;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.common.x5_web.WindowX5WebDialogFragment;

@SuppressWarnings("unused")
public class Template1 {

    /**
     * WebView 加载html格式的文本
     */
    public static void tem1(Activity activity) {
        //加载html 格式文本
        WebView web_view = new WebView(activity);
        web_view.setBackgroundColor(Color.parseColor("#000000")); //黑色背景
        web_view.loadDataWithBaseURL(null, "html格式的数据", "text/html", "UTF-8", null);

        //打开外部浏览器
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        Uri content_url = Uri.parse("https://mobile.alipay.com/index.htm");
        intent.setData(content_url);
        activity.startActivity(intent);

        //Glide示例
        Glide.with((Activity) null).load("file:///android_asset/" + "vip_pic/vip_no_color/" + "name.png").into((ImageView) null);
        Glide.with(activity).setDefaultRequestOptions(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.NONE)).load("").into((ImageView) null);
    }

    //简单的多窗口模板代码
    public boolean onCreateWindow(com.tencent.smtt.sdk.WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WindowX5WebDialogFragment dialogFragment = new WindowX5WebDialogFragment();
        dialogFragment.setWebChromeClient(view.getWebChromeClient());
        dialogFragment.setWebViewTransport((com.tencent.smtt.sdk.WebView.WebViewTransport) resultMsg.obj);
        view.postDelayed(resultMsg::sendToTarget, 200);
        dialogFragment.show((FragmentManager) null, dialogFragment.getClass().getName());
        return true;
    }
}
