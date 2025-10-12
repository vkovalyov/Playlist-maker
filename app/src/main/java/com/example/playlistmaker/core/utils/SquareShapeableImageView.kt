package com.example.playlistmaker.core.utils

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.imageview.ShapeableImageView

class SquareShapeableImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ShapeableImageView(context, attrs, defStyleAttr) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val size: Int = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize
            MeasureSpec.AT_MOST -> minOf(widthSize, heightSize)
            else -> heightSize
        }

        setMeasuredDimension(size, size)
    }
}