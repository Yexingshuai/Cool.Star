package adapter;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

/**
 * Created by daixiankade on 2018/3/30.
 */

public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space=0 ;
    private int left;
    private int right;
    private int top;
    private int bottom;
    public SpaceItemDecoration(int space) {
        this.space = space;
    }

    public SpaceItemDecoration(int left, int right, int top, int bottom) {
        this.left=left;
        this.right=right;
        this.top=top;
        this.bottom=bottom;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int a = parent.getChildPosition(view);
        if (parent.getChildPosition(view) > 1&&space!=0) {
            outRect.top = space;
        } else {
            outRect.left=left;
            outRect.right=right;
            outRect.bottom=bottom;
            outRect.top=top;
        }
    }
}