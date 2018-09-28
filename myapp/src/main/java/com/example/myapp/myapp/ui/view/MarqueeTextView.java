package com.example.myapp.myapp.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myapp.myapp.ui.activity.MainActivity;
import com.example.myapp.myapp.ui.activity.VocalConcertTextActivity;

/**
 * Created by yexing on 2018/9/19.
 */

public class MarqueeTextView extends View {
    private static final String TAG ="MarqueeText" ;
    private int x = 0;//
    private int screenWidth;
    private int textWidth;
    private boolean isMeasure;
    private Paint mPaint;
    private String str = "晚上6点,银海国际酒店的三楼,600多平方的伊甸园里,布置简约而又充满的年轻点气息";
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                x-=5;
                if(Math.abs(x)>=textWidth){
                    x=screenWidth;
                }
                invalidate();
                mHandler.sendEmptyMessageDelayed(1,50);
            }
        }
    };
    public MarqueeTextView(Context context) {
        this(context,null);
    }
    public MarqueeTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }
    public MarqueeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.WHITE);
        mPaint.setTextSize(20);

        WindowManager wm = ((VocalConcertTextActivity)context).getWindowManager();
        screenWidth = wm.getDefaultDisplay().getWidth();
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isMeasure){
            textWidth = (int) mPaint.measureText(str);
            isMeasure = true;
        }
        canvas.drawText(str,x,25,mPaint);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(screenWidth,dip2px(getContext(),40));
    }
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        if(hasWindowFocus){
            mHandler.sendEmptyMessage(1);
        }else{
            mHandler.removeCallbacks(null);
        }
    }
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
