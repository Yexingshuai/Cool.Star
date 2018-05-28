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
 * 圆形
 */
public class CustomCircleView extends View {

    private Paint paint;

    public CustomCircleView(Context context) {
        this(context,null);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,-1);
    }

    public CustomCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private  void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);  //抗锯齿开关，更加平滑
        paint.setStrokeWidth(10);  //画边缘
        paint.setStyle(Paint.Style.STROKE);
    }
    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //参数1：圆心X坐标  参数2： 圆心Y坐标  参数3：圆的半径， 参数4：画笔
        canvas.drawCircle(540, 960, 200, paint);
    }
}
