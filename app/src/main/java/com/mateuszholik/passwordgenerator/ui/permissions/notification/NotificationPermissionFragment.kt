package com.mateuszholik.passwordgenerator.ui.permissions.notification

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPermissionScreenBinding
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_PERMISSION
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.permissions.PermissionManager
import com.mateuszholik.passwordgenerator.managers.permissions.models.PermissionState
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named

class NotificationPermissionFragment : BaseFragment(R.layout.fragment_permission_screen) {

    private val binding by viewBinding(FragmentPermissionScreenBinding::bind)
    private val permissionManager by inject<PermissionManager>(named(NOTIFICATION_PERMISSION))

    private val appPermissionsSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        handlePermissionState()
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        handlePermissionState()
    }

    override val isBottomNavVisible: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handlePermissionState()
    }

    private fun handlePermissionState() {
        when (permissionManager.checkPermission(requireActivity())) {
            PermissionState.NOT_REQUESTED -> {
                updateButton {
                    requestPermissionLauncher.launch(permissionManager.permission)
                    permissionManager.savePermissionRequested()
                }
            }
            PermissionState.GRANTED -> {
                findNavController().navigate(R.id.action_notificationPermissionFragment_to_logInFragment)
            }
            PermissionState.DENIED -> {
                updateButton(
                    textResId = R.string.permission_button_settings,
                    doOnClick = ::openSettings
                )
            }
            PermissionState.SHOW_RATIONALE -> {
                showDialog(
                    titleRes = R.string.permission_rationale_title,
                    messageRes = R.string.notification_permission_rationale,
                    positiveButtonRes = R.string.permission_button_access,
                    doOnPositiveButton = {
                        requestPermissionLauncher.launch(permissionManager.permission)
                    }
                )
            }
        }
    }

    private fun updateButton(
        @StringRes textResId: Int = R.string.permission_button_access,
        doOnClick: () -> Unit
    ) {
        binding.permissionButton.apply {
            text = context.getString(textResId)
            setOnClickListener { doOnClick() }
        }
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", requireActivity().packageName, null)
        }
        appPermissionsSettingsLauncher.launch(intent)
    }
}