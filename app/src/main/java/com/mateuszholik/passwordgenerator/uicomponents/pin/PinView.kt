package com.mateuszholik.passwordgenerator.uicomponents.pin

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AnimationUtils
import com.google.android.material.R as MaterialR
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import com.mateuszholik.passwordgenerator.uicomponents.utils.Constants.ANIMATION_DURATION

class PinView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    var value: String = ""
        set(value) {
            field = value
            invalidate()
        }

    var shouldHideText: Boolean = false
        set(value) {
            field = value
            invalidate()
        }

    private var primaryColor: Int = Color.BLACK
    private var secondaryColor: Int = Color.WHITE
    private var secondaryCurrentColor: Int = Color.WHITE
    private var textSize: Float = DEFAULT_TEXT_SIZE
    private var radius: Float = DEFAULT_RADIUS

    private val stroke = resources.getDimension(R.dimen.stroke_2)

    private val paintPrimary: Paint
        get() = Paint().apply {
            color = primaryColor
        }

    private val paintSecondary: Paint
        get() = Paint().apply {
            color = secondaryCurrentColor
        }

    private val paintText: Paint
        get() = Paint().apply {
            color = secondaryCurrentColor
            textSize = this@PinView.textSize
            typeface = resources.getFont(R.font.mplus_rounded1c_regular)
            textAlign = Paint.Align.CENTER
        }

    private val path: Path
        get() = Path().apply {
            addRoundRect(
                RectF(stroke, stroke, width - stroke, height - stroke),
                FloatArray(8) { radius },
                Path.Direction.CW
            )
        }

    private val pathOuter: Path
        get() = Path().apply {
            addRoundRect(
                RectF(0f, 0f, width.toFloat(), height.toFloat()),
                FloatArray(8) { radius },
                Path.Direction.CW
            )
        }

    init {
        setAttributes(attrs)
    }

    private fun setAttributes(attributeSet: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(
            attributeSet,
            R.styleable.PinView
        )

        val primaryColor = typedArray.getColor(
            R.styleable.PinView_pinPrimaryColor,
            context.getAttrColor(MaterialR.attr.colorPrimary)
        )

        val secondaryColor = typedArray.getColor(
            R.styleable.PinView_pinSecondaryColor,
            context.getAttrColor(MaterialR.attr.colorOnPrimary)
        )

        val textSize = typedArray.getDimension(
            R.styleable.PinView_android_textSize,
            DEFAULT_TEXT_SIZE
        )

        val radius = typedArray.getDimension(
            R.styleable.PinView_android_radius,
            DEFAULT_RADIUS
        )

        this.primaryColor = primaryColor
        this.secondaryColor = secondaryColor
        this.secondaryCurrentColor = secondaryColor
        this.textSize = textSize
        this.radius = radius

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.run {
            drawPath(pathOuter, paintSecondary)
            drawPath(path, paintPrimary)

            if (shouldHideText && value.isNotEmpty()) {
                drawCircle(
                    width / 2f,
                    height / 2f,
                    CIRCLE_RADIUS,
                    paintSecondary.apply { style = Paint.Style.FILL }
                )
            } else {
                val paintText = this@PinView.paintText
                val yPos: Float = (height / 2f) - (paintText.descent() + paintText.ascent()) / 2
                drawText(value, width / 2f, yPos, paintText)
            }
        }
    }

    fun animateSuccess() {
        animateColors(context.getAttrColor(MaterialR.attr.colorSecondary))
    }

    fun animateFailure() {
        animateColors(context.getAttrColor(MaterialR.attr.colorError))
        shake()
    }

    fun clearColors() {
        animateColors(secondaryColor)
        invalidate()
    }

    private fun animateColors(endColor: Int) {
        ValueAnimator.ofArgb(secondaryCurrentColor, endColor).apply {
            duration = ANIMATION_DURATION
            addUpdateListener {
                val animatedColor = it.animatedValue as Int
                secondaryCurrentColor = animatedColor
                invalidate()
            }
        }.start()
    }

    private fun shake() {
        val animShake = AnimationUtils.loadAnimation(context, R.anim.shaking_anim)
        startAnimation(animShake)
    }

    private companion object {
        const val DEFAULT_RADIUS = 50f
        const val DEFAULT_TEXT_SIZE = 20f
        const val CIRCLE_RADIUS = 10f
    }
}
