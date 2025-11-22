package com.example.playlistmaker.track

import android.content.Context
import android.graphics.Canvas
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

    private var isPlaying = false
    private var playIcon: Drawable? = null
    private var pauseIcon: Drawable? = null

    // Callback для уведомления о нажатии
    var onTogglePlayback: ((isPlaying: Boolean) -> Unit)? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PlaybackButtonView)
        playIcon = typedArray.getDrawable(R.styleable.PlaybackButtonView_playIcon)
        pauseIcon = typedArray.getDrawable(R.styleable.PlaybackButtonView_pauseIcon)
        typedArray.recycle()

        isPlaying = false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val icon = if (isPlaying) pauseIcon else playIcon
        icon?.let {
            val iconSize = Math.min(width, height)
            val left = (width - iconSize) / 2f
            val top = (height - iconSize) / 2f
            it.setBounds(
                left.toInt(),
                top.toInt(),
                (left + iconSize).toInt(),
                (top + iconSize).toInt()
            )
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