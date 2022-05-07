package com.mateuszholik.passwordgenerator.ui.loggeduser.passworddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordDetailsBinding
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwordvalidationresult.PasswordValidationResultFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PasswordDetailsFragment : Fragment() {

    private val navArgs: PasswordDetailsFragmentArgs by navArgs()
    private val gsonFactory: GsonFactory by inject()
    private val password: Password by lazy {
        gsonFactory.create().fromJson(navArgs.password, Password::class.java)
    }
    private val viewModel: PasswordDetailsViewModel by viewModel {
        parametersOf(password)
    }
    private lateinit var binding: FragmentPasswordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate<FragmentPasswordDetailsBinding?>(
            inflater,
            R.layout.fragment_password_details,
            container,
            false
        ).apply {
            password = this@PasswordDetailsFragment.password
            viewModel = this@PasswordDetailsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayPasswordValidationResultFragment()
    }

    private fun displayPasswordValidationResultFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                binding.passwordValidationResult.id,
                PasswordValidationResultFragment.newInstance(password.password)
            )
            .commit()
    }
}