package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;

import com.common.R;


/**
 * Author:  Pan
 * Description: No
 */

public class SettingHintSizeEditText extends android.support.v7.widget.AppCompatEditText {

    public SettingHintSizeEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
    }

    public SettingHintSizeEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SettingHintSizeEditText, defStyleAttr, 0);
        String hint_text = typedArray.getString(R.styleable.SettingHintSizeEditText_hint_text);
        int hint_size = Math.round(typedArray.getDimension(R.styleable.SettingHintSizeEditText_hint_size, 33));
        int hint_color = typedArray.getColor(R.styleable.SettingHintSizeEditText_hint_color, Color.GRAY);
        typedArray.recycle();
        setHintText(hint_text, hint_color, hint_size);
    }

    private void setHintText(String hintText, int hint_color, int hint_size) {
        SpannableString ss = new SpannableString(hintText);
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(hint_size, false);
        setHintTextColor(hint_color);
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        setHint(new SpannedString(ss));
    }
}
