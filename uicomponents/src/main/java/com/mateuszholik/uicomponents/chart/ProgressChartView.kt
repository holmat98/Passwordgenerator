package com.mateuszholik.uicomponents.chart

import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.mateuszholik.uicomponents.R
import com.mateuszholik.uicomponents.databinding.ViewProgressChartBinding

class ProgressChartView(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {

    private val binding = ViewProgressChartBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var progress: Int = 0
        set(value) {
            animateProgress(value)
            binding.progressValue.text = context.getString(R.string.progress_value, value)
            field = value
        }

    private fun animateProgress(value: Int) {
        ValueAnimator.ofInt(0, value).apply {
            addUpdateListener {
                val animatedValue = it.animatedValue as Int
                binding.progressBar.progress = animatedValue
            }
            duration = ANIMATION_DURATION
        }.start()
    }

    private companion object {
        const val ANIMATION_DURATION = 400L
    }
}