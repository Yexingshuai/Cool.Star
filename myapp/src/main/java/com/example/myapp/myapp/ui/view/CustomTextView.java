package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daixiankade on 2018/4/25.
 */

public class CustomTextView extends View {
    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(35);
        paint.setFakeBoldText(true);  //使用伪粗体
        paint.setTypeface(Typeface.SERIF); //设置文字风格
        paint.setUnderlineText(true); //是否加下划线
        paint.setTextSkewX(-0.5f);  //文字倾斜
        paint.setLetterSpacing(0.2f);  //设置字符间距

        paint.setTextAlign(Paint.Align.CENTER);   //文字对齐方向
        canvas.drawText("Hello, I am the best cool ",400,100,paint);
    }
}
