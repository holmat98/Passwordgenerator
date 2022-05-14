package com.mateuszholik.passwordgenerator.extensions

import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.mateuszholik.passwordgenerator.R

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