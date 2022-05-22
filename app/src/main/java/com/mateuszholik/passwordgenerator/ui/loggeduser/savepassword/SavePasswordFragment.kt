package com.mateuszholik.passwordgenerator.ui.loggeduser.savepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentSavePasswordBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SavePasswordFragment : Fragment() {

    private lateinit var binding: FragmentSavePasswordBinding
    private val args: SavePasswordFragmentArgs by navArgs()
    private val viewModel: SavePasswordViewModel by viewModel {
        parametersOf(args.password)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentSavePasswordBinding?>(
            inflater,
            R.layout.fragment_save_password,
            container,
            false
        ).apply {
            viewModel = this@SavePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpObservers()
    }

    private fun setUpObservers() {
        with(viewModel) {
            savedPassword.observe(viewLifecycleOwner) {
                findNavController().popBackStack()
                Toast.makeText(context, "Password saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}