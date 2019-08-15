package com.example.myapp.myapp.component.life.view;

import android.support.design.widget.AppBarLayout;

/**
 * 如何监听CollapsingToolbarLayout的展开与折叠
 */

public abstract class AppBarStateChangeListener implements AppBarLayout.OnOffsetChangedListener {

    public enum State {
        EXPANDED,
        COLLAPSED,
        IDLE
    }

    private State mCurrentState = State.IDLE;

    @Override
    public final void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        if (offset == 0) {
            if (mCurrentState != State.EXPANDED) {
                onStateChanged(appBarLayout, State.EXPANDED, offset);
            }
            mCurrentState = State.EXPANDED;
        } else if (Math.abs(offset) >= appBarLayout.getTotalScrollRange()) {
            if (mCurrentState != State.COLLAPSED) {
                onStateChanged(appBarLayout, State.COLLAPSED, offset);
            }
            mCurrentState = State.COLLAPSED;
        } else {
            if (mCurrentState != State.IDLE) {
                onStateChanged(appBarLayout, State.IDLE, offset);
            }
            mCurrentState = State.IDLE;
        }
        onScroll(appBarLayout, offset);
    }

    public abstract void onStateChanged(AppBarLayout appBarLayout, State state, int i);
    public abstract void onScroll(AppBarLayout appBarLayout, int i);
}