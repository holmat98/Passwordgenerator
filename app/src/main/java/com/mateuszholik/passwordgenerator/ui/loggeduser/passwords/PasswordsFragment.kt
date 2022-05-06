package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mateuszholik.passwordgenerator.databinding.FragmentPasswordsBinding
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordsFragment : BaseFragment() {

    private lateinit var binding: FragmentPasswordsBinding
    private val viewModel: PasswordsViewModel by viewModel()
    private val adapter = PasswordsAdapter { viewModel.getPasswordScore(it) }

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
}