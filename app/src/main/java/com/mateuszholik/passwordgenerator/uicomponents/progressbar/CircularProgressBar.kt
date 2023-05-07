package com.mateuszholik.passwordgenerator.uicomponents.progressbar

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import com.mateuszholik.passwordgenerator.uicomponents.utils.Constants.ANIMATION_DURATION
import kotlin.math.min

class CircularProgressBar @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : View(context, attributeSet) {

    private var strokeWidth = DEFAULT_STROKE_WIDTH
    private var animatedProgressValue: Int = NONE
    private var primaryColor: Int = Color.BLACK
    private var secondaryColor: Int = Color.WHITE
    private var textSize: Float = NONE.toFloat()

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
            textAlign = Paint.Align.CENTER
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
            R.styleable.CircularProgressBar
        )

        val primaryColor = typedArray.getColor(
            R.styleable.CircularProgressBar_primaryColor,
            context.getAttrColor(R.attr.colorPrimary)
        )

        val secondaryColor = typedArray.getColor(
            R.styleable.CircularProgressBar_secondaryColor,
            context.getAttrColor(R.attr.colorOnPrimary)
        )

        val textSize = typedArray.getDimension(
            R.styleable.CircularProgressBar_android_textSize,
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
            drawArc(oval, START_ANGLE, FULL_ANGLE, false, paintBackground)
            drawArc(oval, START_ANGLE, FULL_ANGLE * animatedProgressValue / 100, false, paintProgress)

            val yPos: Float = (height / 2f) - (paintText.descent() + paintText.ascent()) / 2
            drawText(
                context.getString(R.string.progress_value, animatedProgressValue),
                width / 2f,
                yPos,
                paintText
            )
        }
    }

    fun animateProgress(progress: Int) {
        ValueAnimator.ofInt(0, progress).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val animationValue = it.animatedValue as Int
                animatedProgressValue = animationValue
                invalidate()
            }
        }.start()
    }

    private companion object {
        const val DEFAULT_STROKE_WIDTH = 40f
        const val NONE = 0
        const val START_ANGLE = -90f
        const val FULL_ANGLE = 360f
    }
}
