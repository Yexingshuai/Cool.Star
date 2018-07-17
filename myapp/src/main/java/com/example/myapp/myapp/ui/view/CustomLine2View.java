package com.example.myapp.myapp.ui.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daixiankade on 2018/4/25.
 */

public class CustomLine2View extends View {

    private Paint paint;
    public CustomLine2View(Context context) {
        super(context);
    }

    public CustomLine2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLine2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint=new Paint();
        paint.setStrokeWidth(10);
        Path path = new Path();
        paint.setStyle(Paint.Style.STROKE);
//        path.lineTo(100, 100); // 由当前位置 (0, 0) 向 (100, 100) 画一条直线
//        path.rLineTo(100, 0);//由当前位置 (100, 100) 向正右方 100 像素的位置画一条直线

//        path.lineTo(100, 100); // 画斜线
//        path.moveTo(300, 100); // 我移~~    移动自己的坐标(全局)
//        path.lineTo(300, 0); // 画竖线


        path.moveTo(100, 100);
        path.lineTo(200, 100);
        path.lineTo(150, 150);// 子图形未封闭
        path.close();  //封闭

        //Path.setFillType(Path.FillType ft) 设置填充方式
        canvas.drawPath(path,paint);
    }


}
