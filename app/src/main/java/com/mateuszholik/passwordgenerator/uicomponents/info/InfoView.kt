package com.mateuszholik.passwordgenerator.uicomponents.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewInfoBinding
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import java.time.LocalDateTime

class InfoView(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ViewInfoBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var date: LocalDateTime = LocalDateTime.now()
        set(value) {
            updateView(value)
            field = value
        }

    private fun updateView(dateTime: LocalDateTime) {
        val style = getDrawableAndStringRes(dateTime)
        strokeColor = context.getAttrColor(style.attrColor)
        binding.infoText.text = context.getString(style.stringRes)
    }

    private fun getDrawableAndStringRes(dateTime: LocalDateTime): WarningViewDetails {
        val now = LocalDateTime.now()
        return when {
            now.isBefore(dateTime.minusDays(7)) ->
                WarningViewDetails(
                    attrColor = R.attr.colorSecondary,
                    stringRes = R.string.warning_password_correct
                )
            now.isAfter(dateTime) ->
                WarningViewDetails(
                    attrColor = R.attr.colorError,
                    stringRes = R.string.warning_password_expired
                )
            else ->
                WarningViewDetails(
                    attrColor = R.attr.colorTertiary,
                    stringRes = R.string.warning_password_expiring
                )
        }
    }

    private data class WarningViewDetails(
        @AttrRes val attrColor: Int,
        @StringRes val stringRes: Int
    )
}
