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

    }

}