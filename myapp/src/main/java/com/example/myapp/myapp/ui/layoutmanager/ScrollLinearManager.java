package com.example.myapp.myapp.ui.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;


public class ScrollLinearManager extends LinearLayoutManager {

    private boolean mCanScrollHorizontally;
    private boolean mCanScrollVertically;

    /**
     * Creates a vertical LinearLayoutManager
     *
     * @param context Current context, will be used to access resources.
     */
    public ScrollLinearManager(Context context) {
        super(context);
    }

    /**
     * @param context       Current context, will be used to access resources.
     * @param orientation   Layout orientation. Should be {@link #HORIZONTAL} or {@link
     *                      #VERTICAL}.
     * @param reverseLayout When set to true, layouts from end to start.
     */
    public ScrollLinearManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    /**
     * Constructor used when layout manager is set in XML by RecyclerView attribute
     * "layoutManager". Defaults to vertical orientation.
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * @param defStyleRes
     * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_android_orientation
     * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_reverseLayout
     * @attr ref android.support.v7.recyclerview.R.styleable#RecyclerView_stackFromEnd
     */
    public ScrollLinearManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean canScrollHorizontally() {
        return mCanScrollHorizontally;
    }

    @Override
    public boolean canScrollVertically() {
        return mCanScrollVertically;
    }

    public void setCanScrollHorizontally(boolean canScrollHorizontally) {
        this.mCanScrollHorizontally = canScrollHorizontally;
    }

    public void setCanScrollVertically(boolean canScrollVertically) {
        this.mCanScrollVertically = canScrollVertically;
    }
}
