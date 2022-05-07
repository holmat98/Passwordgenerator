package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords

import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsBinding
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import com.mateuszholik.passwordgenerator.ui.loggeduser.passwords.adapters.PasswordsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordsFragment : BaseFragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val viewModel: PasswordsViewModel by viewModel()
    private val clipboardManager: ClipboardManager by inject()
    private val gsonFactory: GsonFactory by inject()
    private val adapter = PasswordsAdapter(
        copyToClipboard = { label, password ->
            clipboardManager.copyToClipboard(label, password)
        },
        calculateProgress = { viewModel.getPasswordScore(it) },
        navigateToPasswordDetails = { navigateToPasswordDetails(it) }
    )

    override val isBottomNavVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordsBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObservers()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun setUpObservers() {
        viewModel.run {
            passwords.observe(viewLifecycleOwner) { adapter.addPasswords(it) }
            errorOccurred.observe(viewLifecycleOwner) {
                Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun navigateToPasswordDetails(password: Password) {
        val passwordJson = gsonFactory.create().toJson(password)
        Log.d("TEST", passwordJson)
        val action =
            PasswordsFragmentDirections.actionPasswordsToPasswordDetailsFragment(passwordJson)
        findNavController().navigate(action)
    }
}