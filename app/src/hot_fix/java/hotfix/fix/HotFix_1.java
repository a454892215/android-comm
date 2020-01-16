package hotfix.fix;

import android.app.Activity;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.common.utils.ToastUtil;
import com.common.hotfix.BaseHotFix;

public class HotFix_1 implements BaseHotFix {

    @Override
    public void fixActivity(Activity activity, View view) {
        ToastUtil.showShort("我是来自热修复的代码-fixActivity");
    }

    @Override
    public void fixFragment(Fragment fragment, View view) {
        ToastUtil.showShort("我是来自热修复的代码-fixFragment");
    }
}
