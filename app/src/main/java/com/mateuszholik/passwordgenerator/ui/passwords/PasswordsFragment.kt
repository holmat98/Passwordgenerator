package com.mateuszholik.passwordgenerator.ui.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.TOAST_MESSAGE_PROVIDER
import com.mateuszholik.passwordgenerator.factories.GsonFactory
import com.mateuszholik.passwordgenerator.managers.ClipboardManager
import com.mateuszholik.passwordgenerator.providers.MessageProvider
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import com.mateuszholik.passwordgenerator.ui.passwords.adapters.PasswordsAdapter
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class PasswordsFragment : BaseFragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val viewModel: PasswordsViewModel by viewModel()
    private val messageProvider: MessageProvider by inject(named(TOAST_MESSAGE_PROVIDER))
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
        binding = DataBindingUtil.inflate<FragmentPasswordsBinding?>(
            inflater,
            R.layout.fragment_passwords,
            container,
            false
        ).apply {
            viewModel = this@PasswordsFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        setUpObservers()

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getAllPasswords()

            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllPasswords()
    }

    private fun setUpRecyclerView() {
        binding.recyclerView.adapter = adapter
    }

    private fun setUpObservers() {
        with(viewModel) {
            passwords.observe(viewLifecycleOwner) { adapter.addPasswords(it) }
            errorOccurred.observe(viewLifecycleOwner) { messageProvider.show(it) }
        }
    }

    private fun navigateToPasswordDetails(password: Password) {
        val passwordJson = gsonFactory.create().toJson(password)
        val action =
            PasswordsFragmentDirections.actionPasswordsToPasswordDetailsFragment(passwordJson)
        findNavController().navigate(action)
    }
}