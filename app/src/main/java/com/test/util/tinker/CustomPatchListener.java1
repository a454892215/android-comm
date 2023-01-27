package com.test.util.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

public class CustomPatchListener extends DefaultPatchListener {

    private String currentMD5;

    public void setCurrentMD5(String md5Value) {
        this.currentMD5 = md5Value;
    }
    public CustomPatchListener(Context context) {
        super(context);
    }

    /**
     * 校验
     * @return
     */
    @Override
    public int patchCheck(String path, String patchMd5) {
        //做自己的校验

        return super.patchCheck(path, patchMd5);
    }
}

