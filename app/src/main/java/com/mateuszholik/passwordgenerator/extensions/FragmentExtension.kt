package com.mateuszholik.passwordgenerator.extensions

import android.view.LayoutInflater
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.DialogNumberPickerBinding

fun Fragment.showDialog(
    @StringRes titleRes: Int,
    @StringRes messageRes: Int,
    @StringRes negativeButtonRes: Int = R.string.dialog_button_no,
    @StringRes positiveButtonRes: Int = R.string.dialog_button_yes,
    doOnPositiveButton: () -> Unit
) {
    context?.let {
        AlertDialog.Builder(it).apply {
            setTitle(it.getString(titleRes))
            setMessage(it.getString(messageRes))
            setNegativeButton(negativeButtonRes) { dialog, _ -> dialog.dismiss() }
            setPositiveButton(positiveButtonRes) { _, _ -> doOnPositiveButton() }
        }.show()
    }
}

fun Fragment.showNumberPickerDialog(
    @StringRes messageRes: Int,
    minValue: Int,
    maxValue: Int,
    doOnConfirm: (number: Int) -> Unit
) {
    context?.let {
        val customView = DialogNumberPickerBinding.inflate(LayoutInflater.from(context)).apply {
            dialogText.text = context?.getString(messageRes)
            numberPicker.minValue = minValue
            numberPicker.maxValue = maxValue
        }

        AlertDialog.Builder(it).apply {
            setView(customView.root)
            setNegativeButton(R.string.dialog_button_cancel) { dialog, _ -> dialog.dismiss() }
            setPositiveButton(R.string.dialog_button_save) { dialog, _ ->
                doOnConfirm(customView.numberPicker.value)
                dialog.dismiss()
            }
        }.show()
    }
}