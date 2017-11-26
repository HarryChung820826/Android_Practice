package com.example.harry.appmsg.RecyclerViewApplication;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by Harry on 2017/11/23.
 */
public class ScrollSpeedLinearLayoutManger extends LinearLayoutManager{
    public ScrollSpeedLinearLayoutManger(Context context) {
        super(context, VERTICAL, false);
    }

    public ScrollSpeedLinearLayoutManger(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }


    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
        Log.e("linksu",
                "smoothScrollToPosition(ScrollSpeedLinearLayoutManger.java:62)");
        RecyclerView.SmoothScroller smoothScroller = new CenterSmoothScroller(recyclerView.getContext());
        smoothScroller.setTargetPosition(position);
        startSmoothScroll(smoothScroller);
    }

    private class CenterSmoothScroller extends LinearSmoothScroller {

        CenterSmoothScroller(Context context) {
            super(context);
        }

        @Nullable
        @Override
        public PointF computeScrollVectorForPosition(int targetPosition) {
            return ScrollSpeedLinearLayoutManger.this.computeScrollVectorForPosition(targetPosition);
        }

        @Override
        public int calculateDtToFit(int viewStart, int viewEnd, int boxStart, int boxEnd, int snapPreference) {
            switch (snapPreference) {
                case SNAP_TO_START:
                    return boxStart - viewStart;
                case SNAP_TO_END:
                    return boxEnd - viewEnd;
                default:
                    throw new IllegalArgumentException("snap preference should be one of the"
                            + " constants defined in SmoothScroller, starting with SNAP_");
            }
//            return (boxStart + (boxEnd - boxStart)) - (viewStart + (viewEnd - viewStart));
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return 0.3f;
        }

        @Override
        protected int getVerticalSnapPreference() {
            return SNAP_TO_START;
//            return SNAP_TO_END;
        }
    }
}
