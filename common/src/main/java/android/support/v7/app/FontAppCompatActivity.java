package android.support.v7.app;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.Window;

/**
 * author: ${VenRen}
 * created on: 2019/2/23 13:06
 * description: 自定义自定的父类
 */
public abstract class FontAppCompatActivity extends AppCompatActivity {

    private AppCompatDelegate mDelegate;

    @NonNull
    @Override
    public AppCompatDelegate getDelegate() {
        if (mDelegate == null) {
            mDelegate = new CustomCompatDelegate(this, getWindow(), this);
        }
        return mDelegate;
    }

    private class CustomCompatDelegate extends AppCompatDelegateImplV14 {

        CustomCompatDelegate(Context context, Window window, AppCompatCallback callback) {
            super(context, window, callback);
        }

        @Override
        View callActivityOnCreateView(View parent, String name, Context context, AttributeSet attrs) {

            switch (name) {
                case "TextView":
                    return new FontText(context, attrs);
                case "Button":
                    return new FontButton(context, attrs);
                /*case "CheckBox":
                    return new FontCheckBox(context,attrs);*/
                case "EditText":
                    return new FontEditText(context,attrs);
            }
            return super.callActivityOnCreateView(parent, name, context, attrs);
        }
    }
}
