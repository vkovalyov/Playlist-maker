package com.example.playlistmaker.track

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.playlistmaker.R

class PlaybackButtonView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val iconBounds = Rect()
    private var isPlaying = false
    private var playIcon: Drawable? = null
    private var pauseIcon: Drawable? = null

    var onTogglePlayback: ((isPlaying: Boolean) -> Unit)? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PlaybackButtonView)
        playIcon = a.getDrawable(R.styleable.PlaybackButtonView_playIcon)
        pauseIcon = a.getDrawable(R.styleable.PlaybackButtonView_pauseIcon)
        a.recycle()

        isPlaying = false
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateIconBounds(w, h)
    }

    private fun updateIconBounds(width: Int, height: Int) {
        val iconSize = (minOf(width, height)).toInt()
        val left = (width - iconSize) / 2
        val top = (height - iconSize) / 2
        iconBounds.set(left, top, left + iconSize, top + iconSize)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val icon = if (isPlaying) pauseIcon else playIcon
        icon?.let {
            it.setBounds(iconBounds)
            it.draw(canvas)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                invalidate()
                return true
            }

            MotionEvent.ACTION_UP -> {
                toggleState()
                onTogglePlayback?.invoke(isPlaying)
                invalidate()
                return true
            }

            MotionEvent.ACTION_CANCEL -> {
                invalidate()
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun toggleState() {
        isPlaying = !isPlaying
    }

    fun setPlaying(isPlaying: Boolean) {
        this.isPlaying = isPlaying
        invalidate()
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }
}