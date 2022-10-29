package com.mateuszholik.passwordgenerator.uicomponents.info

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.mateuszholik.passwordgenerator.R
import java.time.LocalDateTime

private typealias WarningViewDetails = Pair<Int, Int>

class InfoView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {

    var date: LocalDateTime = LocalDateTime.now()
        set(value) {
            updateView(value)
            field = value
        }

    private fun updateView(dateTime: LocalDateTime) {
        val style = getDrawableAndStringRes(dateTime)
        background = ContextCompat.getDrawable(context, style.first)
        text = context.getString(style.second)
    }

    private fun getDrawableAndStringRes(dateTime: LocalDateTime): WarningViewDetails {
        val now = LocalDateTime.now()
        return when {
            now.isBefore(dateTime.minusDays(7)) ->
                R.drawable.rounded_ligter_dark_background_with_green_stroke to R.string.warning_password_correct
            now.isAfter(dateTime) ->
                R.drawable.rounded_lighter_dark_background_with_red_stroke to R.string.warning_password_expired
            else ->
                R.drawable.rounded_lighter_dark_background_with_yellow_stroke to R.string.warning_password_expiring
        }
    }
}