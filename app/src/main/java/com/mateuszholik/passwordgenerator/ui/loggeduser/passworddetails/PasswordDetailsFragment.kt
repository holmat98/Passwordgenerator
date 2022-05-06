package com.mateuszholik.passwordgenerator.ui.loggeduser.passworddetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordDetailsBinding

class PasswordDetailsFragment : Fragment() {

    private val navArgs: PasswordDetailsFragmentArgs by navArgs()
    private val password: Password by lazy {
        Gson().fromJson(navArgs.password, Password::class.java)
    }
    private lateinit var binding: FragmentPasswordDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordDetailsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.test.text = password.toString()
    }
}