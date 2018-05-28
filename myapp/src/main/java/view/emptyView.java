package view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by daixiankade on 2018/5/15.
 */

public class emptyView extends View {
    public emptyView(Context context) {
        super(context);
    }

    public emptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public emptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Toast.makeText(getContext(),event.getX()+"---",Toast.LENGTH_SHORT).show();
        return true;
    }
}
