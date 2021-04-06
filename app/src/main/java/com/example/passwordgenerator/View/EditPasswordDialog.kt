package com.example.passwordgenerator.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.Model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ViewModel.PasswordViewModel
import kotlinx.android.synthetic.main.add_password_dialog.*
import kotlinx.android.synthetic.main.edit_password_dialog.*
import kotlinx.android.synthetic.main.edit_password_dialog.view.*

class EditPasswordDialog: DialogFragment(){

    private lateinit var viewModel: PasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.edit_password_dialog, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordViewModel::class.java)

        rootView.editPasswordBtn.setOnClickListener {
            var password: String = newPasswordET.text.toString()

            if(!"".equals(password)){
                HelperClass.editPassword?.password = password
                viewModel.update(HelperClass.editPassword!!)
                Toast.makeText(context, "Edited", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        rootView.closeDialogBtn2.setOnClickListener{
            dismiss()
        }

        return rootView
    }
}