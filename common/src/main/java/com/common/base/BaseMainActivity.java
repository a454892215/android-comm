package com.common.base;

import com.common.comm.update.VersionUpdateHelper;
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
            versionUpdateHelper.checkVersionUpdateInfo(null, this, false);
        }
    }
}
