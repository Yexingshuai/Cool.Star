package view;

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
 * 圆角矩形效果
 */
public class CustomRoundRectView extends View {
    private Paint paint;

    public CustomRoundRectView(Context context) {
        super(context);
    }

    public CustomRoundRectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRoundRectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);  //抗锯齿开关，更加平滑
        //后面两个参数代表：圆角的横向半径，和纵向半径
        canvas.drawRoundRect(100, 100, 500, 300, 50, 50, paint);
    }
}
