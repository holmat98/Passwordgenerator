package com.mateuszholik.passwordgenerator.ui.loggeduser.passwordscore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordScoreBinding
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult.PasswordValidationResultFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PasswordScoreFragment : Fragment() {

    private lateinit var binding: FragmentPasswordScoreBinding
    private val args: PasswordScoreFragmentArgs by navArgs()
    private val viewModel: PasswordScoreViewModel by viewModel {
        parametersOf(args.password)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPasswordScoreBinding>(
            inflater,
            R.layout.fragment_password_score,
            container,
            false
        ).apply {
            password = args.password
            viewModel = this@PasswordScoreFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorOccurred.observe(viewLifecycleOwner) { errorOccurred ->
            if (errorOccurred) {
                findNavController().popBackStack()
            }
        }

        displayPasswordValidationResultFragment()
    }

    private fun displayPasswordValidationResultFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                binding.passwordValidationResult.id,
                PasswordValidationResultFragment.newInstance(args.password)
            )
            .commit()
    }
}