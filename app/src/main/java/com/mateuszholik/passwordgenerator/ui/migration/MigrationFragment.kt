package com.mateuszholik.passwordgenerator.ui.migration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class MigrationFragment : BaseFragment(R.layout.fragment_migration) {

    override val isBottomNavVisible: Boolean = false

    private val viewModel: MigrationViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.migrationCompleted.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.action_dataMigration_to_passwords)
            }
        }
    }
}
