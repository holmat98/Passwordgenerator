package com.mateuszholik.passwordgenerator.ui.loggeduser.generatepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentGeneratePasswordBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class GeneratePasswordFragment : BaseFragment() {

    private lateinit var binding: FragmentGeneratePasswordBinding
    private val viewModel: GeneratePasswordViewModel by viewModel()

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentGeneratePasswordBinding>(
            inflater,
            R.layout.fragment_generate_password,
            container,
            false
        ).apply {
            viewModel = this@GeneratePasswordFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.goToSavePasswordScreenButton.setOnClickListener {
            goToSavePasswordScreen(binding.generatedPasswordTV.text.toString())
        }
    }

    private fun goToSavePasswordScreen(password: String?) {
        val action =
            GeneratePasswordFragmentDirections.actionGeneratePasswordToSavePasswordFragment(password)
        findNavController().navigate(action)
    }
}