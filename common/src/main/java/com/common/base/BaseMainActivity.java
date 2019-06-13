package com.common.base;

import com.common.comm.version_update.VersionUpdateHelper;
import com.common.utils.LogUtil;

public abstract class BaseMainActivity extends BaseActivity {

    private boolean isHasInitCheckVersion = false;
    VersionUpdateHelper versionUpdateHelper = new VersionUpdateHelper();

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.d("BaseMainActivity isHasInitCheckVersion:" + isHasInitCheckVersion + " isForceUpdate :" + VersionUpdateHelper.isForceUpdate);
        if (!isHasInitCheckVersion || VersionUpdateHelper.isForceUpdate) {
            isHasInitCheckVersion = true;
           //TODO  请求是否存在新版本
            versionUpdateHelper.onHasNewVersion(this,0,"1.1",
                    "存在新版本是否更新？",false,null,null);
        }
    }
}
