package com.example.playlistmaker.core.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val horizontalSpacing: Int,
    private val verticalSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % spanCount
        val row = position / spanCount

        outRect.left = column * horizontalSpacing / spanCount
        outRect.right = horizontalSpacing - (column + 1) * horizontalSpacing / spanCount

        if (row == 0) {
            outRect.top = 0
        } else {
            outRect.top = verticalSpacing
        }

        outRect.bottom = 0
    }
}