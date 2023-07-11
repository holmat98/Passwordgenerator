package com.mateuszholik.passwordgenerator.uicomponents.info

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.AttrRes
import androidx.annotation.StringRes
import com.google.android.material.card.MaterialCardView
import com.mateuszholik.data.repositories.models.PasswordValidity
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ViewInfoBinding
import com.mateuszholik.passwordgenerator.extensions.getAttrColor

class InfoView(context: Context, attrs: AttributeSet) : MaterialCardView(context, attrs) {

    private val binding = ViewInfoBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var passwordValidity: PasswordValidity? = null
        set(value) {
            updateView(value)
            field = value
        }

    private fun updateView(passwordValidity: PasswordValidity?) {
        val style = getDrawableAndStringRes(passwordValidity)
        strokeColor = context.getAttrColor(style.attrColor)
        binding.infoText.text = context.getString(style.stringRes)
    }

    private fun getDrawableAndStringRes(passwordValidity: PasswordValidity?): WarningViewDetails =
        when (passwordValidity) {
            is PasswordValidity.Valid ->
                WarningViewDetails(
                    attrColor = R.attr.colorPrimary,
                    stringRes = R.string.warning_password_correct
                )
            is PasswordValidity.Expired ->
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

    private data class WarningViewDetails(
        @AttrRes val attrColor: Int,
        @StringRes val stringRes: Int,
    )
}
