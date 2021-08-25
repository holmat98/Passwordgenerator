package com.example.passwordgenerator.view

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.viewModel.PasswordViewModel


class PasswdGeneratorFragment: Fragment(R.layout.fragment_passwd_generator) {

    private var passwordLengths: List<Int> = listOf( 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 )
    private var length: Int = 8
    private var createdPassword: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberPicker = view.findViewById<NumberPicker>(R.id.passwordLengthNP)

        numberPicker.apply {
            minValue = 8
            maxValue = 20
        }

        view.findViewById<Button>(R.id.createPasswordBtn).setOnClickListener {
            val password: String = HelperClass.generatePassword(true, true, true, true, numberPicker.value)

            view.findViewById<TextView>(R.id.createdPasswordTV).text = password
        }

    }
}