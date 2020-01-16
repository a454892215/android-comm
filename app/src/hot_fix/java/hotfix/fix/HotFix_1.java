package hotfix.fix;

import android.app.Activity;
import android.app.Application;

import com.common.utils.ToastUtil;
import com.common.hotfix.BaseHotFix;

public class HotFix_1 implements BaseHotFix {

    @Override
    public void onAppCreate(Application application) {

    }

    @Override
    public void onActivityCreate(Activity activity) {
    }

    @Override
    public void onActivityResume(Activity activity) {
        ToastUtil.showShort("我是来自热修复的代码-onActivityResume");
    }

    @Override
    public void onActivityPause(Activity activity) {

    }

    @Override
    public void onActivityDestroy(Activity activity) {

    }
}
