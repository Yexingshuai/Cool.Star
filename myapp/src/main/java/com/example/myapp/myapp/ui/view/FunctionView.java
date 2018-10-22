package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapp.R;

/**
 * Created by yexing on 2018/10/9.
 */


/**
 * 功能条目展示view
 */
public class FunctionView extends LinearLayout {

    private Drawable mIcon;
    private String mText;
    private View view;

    public FunctionView(Context context) {
        this(context, null);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
        init(attrs);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.function_tab, this, true);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.function);
        mIcon = typedArray.getDrawable(R.styleable.function_icon);
        mText = (String) typedArray.getText(R.styleable.function_text);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        ImageView icon = view.findViewById(R.id.icon);
        TextView name = view.findViewById(R.id.name);
        if (mIcon != null) {
            icon.setImageDrawable(mIcon);
        }
        if (mText != null) {
            name.setText(mText);
        }


    }
}
