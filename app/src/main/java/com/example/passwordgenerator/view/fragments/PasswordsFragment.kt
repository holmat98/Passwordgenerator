package com.example.passwordgenerator.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.Cryptography
import com.example.passwordgenerator.viewModel.PasswordViewModel
import com.example.passwordgenerator.viewModel.PasswordsAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PasswordsFragment : Fragment(R.layout.fragment_passwords) {

    private lateinit var viewModel: PasswordViewModel

    private lateinit var myAdapter: PasswordsAdapter
    private lateinit var myLayoutManager: GridLayoutManager

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordViewModel::class.java)
        myAdapter = PasswordsAdapter(viewModel.passwords, viewModel, context, requireActivity())
        myLayoutManager = GridLayoutManager(context, 3)

        viewModel.passwords.observe(viewLifecycleOwner){
            myAdapter.notifyDataSetChanged()
        }

        view.findViewById<RecyclerView>(R.id.passwordsRV).apply {
            adapter = myAdapter
            layoutManager = myLayoutManager
        }

        view.findViewById<FloatingActionButton>(R.id.addNewPasswordBtn).setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomSheetView = layoutInflater.inflate(
                R.layout.add_new_password_dialog,
                null
            )

            bottomSheetView.findViewById<Button>(R.id.saveBtn).setOnClickListener {
                val platformName: String = bottomSheetView.findViewById<EditText>(R.id.platformNameET).text.toString()
                val password: String = bottomSheetView.findViewById<EditText>(R.id.passwordNameET).text.toString()
                if(platformName.isNotEmpty() && password.isNotEmpty()){
                    val encryptedPassword = Cryptography.encryptData(password)
                    viewModel.insert(
                        platformName = platformName,
                        password = String(encryptedPassword.second, Charsets.ISO_8859_1),
                        passwordIv =  String(encryptedPassword.first, Charsets.ISO_8859_1)
                    )
                    Toast.makeText(context, context?.getString(R.string.password_saved), Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }
                else{
                    Toast.makeText(context, context?.getString(R.string.password_platform_empty), Toast.LENGTH_SHORT).show()
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