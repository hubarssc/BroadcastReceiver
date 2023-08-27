package com.example.customview

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomProgressBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val borderPaint = Paint().apply {
        color = Color.BLACK //
        style = Paint.Style.STROKE
        strokeWidth = 20f //
    }

    private val progressPaint = Paint()
    private var progress = 0

    init {
        progressPaint.color = Color.RED
    }

    fun setProgress(percent: Int) {
        val targetColor: Int = when {
            percent <= 1 -> Color.RED
            percent >= 100 -> Color.GREEN
            else -> {
                val greenValue = (255 * percent / 100).coerceIn(0, 255)
                val redValue = 255 - greenValue
                Color.rgb(redValue, greenValue, 0)
            }
        }

        val colorAnimator = ValueAnimator.ofArgb(progressPaint.color, targetColor)
        colorAnimator.addUpdateListener { animation ->
            progressPaint.color = animation.animatedValue as Int
            invalidate()
        }

        val progressAnimator = ValueAnimator.ofInt(progress, percent)
        progressAnimator.addUpdateListener { animation ->
            progress = animation.animatedValue as Int
            invalidate()
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(colorAnimator, progressAnimator)
        animatorSet.duration = 1000
        animatorSet.start()
    }

    override fun onDraw(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        val progressHeight = height * progress / 100

        canvas.drawRect(0f, height - progressHeight, width, height, progressPaint)


        canvas.drawRect(0f, 0f, width, height, borderPaint)
    }
}