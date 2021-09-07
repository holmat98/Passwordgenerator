package com.example.passwordgenerator.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.viewModel.PasswordViewModel


class PasswdGeneratorFragment: Fragment(R.layout.fragment_passwd_generator) {

    private var passwordLengths: List<Int> = listOf( 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20 )
    private var length: Int = 8
    private var createdPassword: String = ""

    private lateinit var viewModel: PasswordViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val numberPicker = view.findViewById<NumberPicker>(R.id.passwordLengthNP)
        val savePasswordBtn = view.findViewById<Button>(R.id.savePasswordBtn)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordViewModel::class.java)

        numberPicker.apply {
            minValue = 8
            maxValue = 20
        }

        view.findViewById<Button>(R.id.createPasswordBtn).setOnClickListener {
            createdPassword = HelperClass.generatePassword(true, true, true, true, numberPicker.value)

            view.findViewById<TextView>(R.id.createdPasswordTV).text = createdPassword
            savePasswordBtn.visibility = View.VISIBLE
        }

        savePasswordBtn.setOnClickListener {
            if(createdPassword.isNotEmpty()){

            }
        }

    }
}