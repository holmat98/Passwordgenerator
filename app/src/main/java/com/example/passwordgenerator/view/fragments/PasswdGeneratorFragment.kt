package com.example.passwordgenerator.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.Cryptography
import com.example.passwordgenerator.viewModel.PasswordViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class PasswdGeneratorFragment: Fragment(R.layout.fragment_passwd_generator) {

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
            createdPassword = HelperClass.generatePassword(numberPicker.value)

            view.findViewById<TextView>(R.id.createdPasswordTV).text = createdPassword
            savePasswordBtn.visibility = View.VISIBLE
        }

        savePasswordBtn.setOnClickListener {
            if(createdPassword.isNotEmpty()){

                val bottomSheetDialog = BottomSheetDialog(requireContext())
                val bottomSheetView = layoutInflater.inflate(
                    R.layout.add_password_bottom_sheet_dialog,
                    null
                )

                bottomSheetView.findViewById<Button>(R.id.saveBtn).setOnClickListener {
                    val platformName: String = bottomSheetView.findViewById<EditText>(R.id.platformNameET).text.toString()
                    if(platformName.isNotEmpty()){
                        val encryptedPassword = Cryptography.encryptData(createdPassword)
                        viewModel.insert(platformName = platformName, password = String(encryptedPassword.second, Charsets.ISO_8859_1), passwordIv = String(encryptedPassword.first, Charsets.ISO_8859_1))
                        Toast.makeText(context, getString(R.string.password_saved), Toast.LENGTH_SHORT).show()
                        bottomSheetDialog.dismiss()
                        createdPassword = ""
                        savePasswordBtn.visibility = View.INVISIBLE
                        view.findViewById<TextView>(R.id.createdPasswordTV).text = ""
                    }
                    else{
                        Toast.makeText(context, getString(R.string.platform_name_is_empty), Toast.LENGTH_SHORT).show()
                    }
                }

                bottomSheetView.findViewById<Button>(R.id.cancelBtn).setOnClickListener {
                    bottomSheetDialog.dismiss()
                }

                bottomSheetDialog.setContentView(bottomSheetView)
                bottomSheetDialog.show()
            }
        }

    }
}