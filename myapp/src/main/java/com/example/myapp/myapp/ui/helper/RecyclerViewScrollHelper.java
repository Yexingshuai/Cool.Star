package com.example.myapp.myapp.ui.helper;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;

public class RecyclerViewScrollHelper {


    public static void scrollToPosition(RecyclerView recyclerView, int position) {
        RecyclerView.LayoutManager manager1 = recyclerView.getLayoutManager();
        if (manager1 instanceof LinearLayoutManager) {
            LinearLayoutManager manager = (LinearLayoutManager) manager1;
            final TopSmoothScroller mScroller = new TopSmoothScroller(recyclerView.getContext());
            mScroller.setTargetPosition(position);
            manager.startSmoothScroll(mScroller);
        }
    }

    public static class TopSmoothScroller extends LinearSmoothScroller {
        TopSmoothScroller(Context context) {
            super(context);
        }

        @Override
        protected int getHorizontalSnapPreference() {
            return SNAP_TO_START;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
        }
    }

}
