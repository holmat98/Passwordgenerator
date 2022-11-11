package com.mateuszholik.passwordgenerator.ui.permissions

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.annotation.StringRes
import androidx.navigation.fragment.findNavController
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.FragmentPermissionScreenBinding
import com.mateuszholik.passwordgenerator.extensions.showDialog
import com.mateuszholik.passwordgenerator.extensions.viewBinding
import com.mateuszholik.passwordgenerator.managers.permissions.PermissionManager
import com.mateuszholik.passwordgenerator.managers.permissions.models.PermissionState
import com.mateuszholik.passwordgenerator.ui.base.BaseFragment
import org.koin.android.ext.android.inject
import org.koin.core.qualifier.named
import timber.log.Timber

abstract class BasePermissionFragment(permissionType: String) :
    BaseFragment(R.layout.fragment_permission_screen) {

    protected abstract val titleResId: Int
    protected abstract val descriptionResId: Int
    protected abstract val rationaleMessageResId: Int
    protected abstract val imageResId: Int
    protected abstract val onPermissionGrantedResId: Int

    protected val binding by viewBinding(FragmentPermissionScreenBinding::bind)

    private val permissionManager by inject<PermissionManager>(named(permissionType))

    private val appPermissionsSettingsLauncher = registerForActivityResult(StartActivityForResult()) {
        handlePermissionState()
    }

    private val requestPermissionLauncher = registerForActivityResult(RequestPermission()) {
        handlePermissionState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpTextsAndImage()
        handlePermissionState()
    }

    private fun setUpTextsAndImage() {
        binding.apply {
            permissionTitle.text = context?.getString(titleResId)
            permissionDescription.text = context?.getString(descriptionResId)
            permissionIcon.setImageResource(imageResId)
        }
    }

    private fun handlePermissionState() {
        when (permissionManager.checkPermission(requireActivity())) {
            PermissionState.NOT_REQUESTED -> {
                Timber.i("Testowanie: NOT_REQUESTED")
                updateButton {
                    requestPermissionLauncher.launch(permissionManager.permission)
                    permissionManager.savePermissionRequested()
                }
            }
            PermissionState.GRANTED -> {
                Timber.i("Testowanie: GRANTED")
                findNavController().navigate(onPermissionGrantedResId)
            }
            PermissionState.DENIED -> {
                Timber.i("Testowanie: DENIED")
                updateButton(
                    textResId = R.string.permission_button_settings,
                    doOnClick = ::openSettings
                )
            }
            PermissionState.SHOW_RATIONALE -> {
                Timber.i("Testowanie: SHOW_RATIONALE")
                showDialog(
                    titleRes = R.string.permission_rationale_title,
                    messageRes = rationaleMessageResId,
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

    override val isBottomNavVisible: Boolean = false
}