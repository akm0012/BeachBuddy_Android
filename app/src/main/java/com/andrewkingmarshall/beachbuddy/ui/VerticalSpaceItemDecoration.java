package com.andrewkingmarshall.beachbuddy.ui;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * This Decoration adds some extra space at the bottom of each RecyclerView item.
 * <p>
 */
public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    private final boolean showLastElementSpace;

    public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.showLastElementSpace = false;
    }

    /**
     * @param showLastElementSpace False if you do not want this to be applied to the last element.
     *                             Handy when you have another decoration handling the last element due
     *                             to FAB spacing.
     */
    public VerticalSpaceItemDecoration(int verticalSpaceHeight, boolean showLastElementSpace) {
        this.verticalSpaceHeight = verticalSpaceHeight;
        this.showLastElementSpace = showLastElementSpace;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        // Don't add space to the last element.
        if (parent.getChildAdapterPosition(view) != parent.getAdapter().getItemCount() - 1) {
            outRect.bottom = verticalSpaceHeight;
        } else if (showLastElementSpace) {
            outRect.bottom = verticalSpaceHeight;
        }
    }
}