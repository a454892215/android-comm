package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.common.R;
import com.common.widget.comm.EditTextUtil;


/**
 * @author L
 */
public class InputLayout extends FrameLayout {

    private final Context context;
    private String title;
    private String hint;
    private int max_length;
    private int input_type;
    private float et_margin_end;

    public InputLayout(Context context) {
        this(context, null);
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public InputLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.InputLayout, defStyleAttr, 0);
        title = typedArray.getString(R.styleable.InputLayout_title);
        hint = typedArray.getString(R.styleable.InputLayout_hint);
        max_length = typedArray.getInt(R.styleable.InputLayout_max_length, 20);
        input_type = typedArray.getInt(R.styleable.InputLayout_input_type, InputType.TYPE_CLASS_TEXT);
        et_margin_end = typedArray.getDimension(R.styleable.InputLayout_et_margin_end, 0);
        typedArray.recycle();
        initView();
    }

    private void initView() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.custom_view_input_layout, this, false);

        TextView tv_title = contentView.findViewById(R.id.tv_title);
        EditText et_input = contentView.findViewById(R.id.et_input);
        tv_title.setText(title);
        et_input.setHint(hint);
        et_input.setInputType(input_type);
        et_input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(max_length)});
        LinearLayout.LayoutParams lp_et = (LinearLayout.LayoutParams) et_input.getLayoutParams();
        lp_et.rightMargin = Math.round(et_margin_end);
        et_input.setLayoutParams(lp_et);
        EditTextUtil.addWordNumFilter(et_input);
        addView(contentView);
    }
}
