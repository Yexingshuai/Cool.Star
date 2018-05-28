package view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by daixiankade on 2018/4/24.
 */

/**
 * 画线
 */
public class CustomLineView extends View {

    private Paint paint;
    public CustomLineView(Context context) {
        super(context);
    }

    public CustomLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setAntiAlias(true);  //抗锯齿开关，更加平滑
        canvas.drawLine(200, 200, 800, 500, paint);
    }
}
