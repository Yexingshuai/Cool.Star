package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daixiankade on 2018/4/24.
 */

/**
 * 绘制矩形
 */
public class CustomRectView extends View {

    private Paint paint;

    public CustomRectView(Context context) {
        super(context);
    }

    public CustomRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);  //抗锯齿开关，更加平滑
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        //四个参数分别代表四条边的坐标。
        canvas.drawRect(100, 100, 500, 500, paint);
    }
}
