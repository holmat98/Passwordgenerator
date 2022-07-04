package com.mateuszholik.passwordgenerator.uicomponents.keyboard

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.mateuszholik.passwordgenerator.databinding.ViewKeyboardBinding

class Keyboard(context: Context, attrs: AttributeSet) : ConstraintLayout(context, attrs) {

    private val binding = ViewKeyboardBinding.inflate(
        LayoutInflater.from(context),
        this
    )

    var doOnNumberClicked: (Int) -> Unit = {}
    var doOnUndoClicked: () -> Unit = {}
    var doOnConfirmedClicked: () -> Unit = {}

    init {
        with(binding) {
            numberZeroBtn.setOnClickListener { doOnNumberClicked(0) }
            numberOneBtn.setOnClickListener { doOnNumberClicked(1) }
            numberTwoBtn.setOnClickListener { doOnNumberClicked(2) }
            numberThreeBtn.setOnClickListener { doOnNumberClicked(3) }
            numberFourBtn.setOnClickListener { doOnNumberClicked(4) }
            numberFiveBtn.setOnClickListener { doOnNumberClicked(5) }
            numberSixBtn.setOnClickListener { doOnNumberClicked(6) }
            numberSevenBtn.setOnClickListener { doOnNumberClicked(7) }
            numberEightBtn.setOnClickListener { doOnNumberClicked(8) }
            numberNineBtn.setOnClickListener { doOnNumberClicked(9) }
            undoBtn.setOnClickListener { doOnUndoClicked() }
            confirmBtn.setOnClickListener { doOnConfirmedClicked() }
        }
    }
}