package com.example.passwordgenerator.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.passwordgenerator.R
import com.example.passwordgenerator.ViewModel.PasswordViewModel
import kotlinx.android.synthetic.main.add_password_dialog.*
import kotlinx.android.synthetic.main.add_password_dialog_2.*
import kotlinx.android.synthetic.main.add_password_dialog_2.view.*

class AddPasswordDialog2: DialogFragment() {
    private lateinit var viewModel: PasswordViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var rootView: View = inflater.inflate(R.layout.add_password_dialog_2, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordViewModel::class.java)

        rootView.addPasswordBtn3.setOnClickListener {
            var platform: String = platformNameET2.text.toString()
            var password: String = passwordValueET.text.toString()

            if(!"".equals(platform) || !"".equals(password)){
                viewModel.insert(platformName = platform, password = password)
                Toast.makeText(context, "Added", Toast.LENGTH_SHORT).show()
                dismiss()
            }
        }

        rootView.closeDialogBtn3.setOnClickListener {
            dismiss()
        }

        return rootView
    }
}