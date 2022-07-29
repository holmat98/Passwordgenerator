package com.mateuszholik.passwordgenerator.uicomponents.progressbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.mateuszholik.passwordgenerator.R
import kotlin.math.min

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private var strokeWidth = DEFAULT_STROKE_WIDTH
    private var progress: Int = 0
    private var primaryColor: Int = Color.BLACK
    private var secondaryColor: Int = Color.WHITE
    private var textSize: Float = 0f

    private val radius: Float
        get() = (min(width, height) - (strokeWidth * 2)) / 2

    private val paintBackground: Paint
        get() = Paint().apply {
            color = primaryColor
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth
        }

    private val paintProgress: Paint
        get() = Paint().apply {
            color = secondaryColor
            style = Paint.Style.STROKE
            strokeWidth = this@CircularProgressBar.strokeWidth
        }

    private val paintText: Paint
        get() = Paint().apply {
            color = secondaryColor
            textSize = this@CircularProgressBar.textSize
        }

    private val oval: RectF
        get() = RectF(
            strokeWidth * 2,
            (height / 2f) - radius + strokeWidth,
            radius * 2,
            (height / 2f) + radius - strokeWidth
        )

    init {
        attributeSet?.let { setAttributes(it) }
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.CircularProgressView
        )

        val primaryColor = typedArray.getColor(
            R.styleable.CircularProgressView_primaryColor,
            ContextCompat.getColor(context, R.color.primary)
        )

        val secondaryColor = typedArray.getColor(
            R.styleable.CircularProgressView_secondaryColor,
            ContextCompat.getColor(context, R.color.secondary)
        )

        val textSize = typedArray.getDimension(
            R.styleable.CircularProgressView_android_textSize,
            resources.getDimension(R.dimen.text_size_25)
        )

        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
        this.textSize = textSize

        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        strokeWidth = min(width, height) * 0.05f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.run {
            drawArc(oval, -90f, 360f, false, paintBackground)
            drawArc(oval, -90f, 360f * progress / 100, false, paintProgress)
            drawText(
                context.getString(R.string.progress_value, progress),
                if (progress > 10) {
                    width / 2f - radius / 2 - (strokeWidth * 1.5).toInt()
                } else {
                    width / 2f - radius / 2 + strokeWidth / 2
                },
                height / 2f + radius / 2 - strokeWidth * 2,
                paintText
            )
        }
    }

    fun animateProgress(progress: Int) {
        ValueAnimator.ofInt(0, progress).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val animationValue = it.animatedValue as Int
                this@CircularProgressBar.progress = animationValue
                invalidate()
            }
        }.start()
    }

    private companion object {
        const val DEFAULT_STROKE_WIDTH = 40f
        const val ANIMATION_DURATION = 300L
    }
}