package com.mateuszholik.passwordgenerator.managers.permissions

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.passwordgenerator.managers.permissions.models.PermissionState

abstract class PermissionManager(
    private val sharedPrefManager: SharedPrefManager
) {

    abstract val permission: String
    protected abstract val permissionKey: String

    fun checkPermission(activity: Activity): PermissionState =
        when {
            isPermissionGranted(activity) -> PermissionState.GRANTED
            !wasPermissionRequested() -> PermissionState.NOT_REQUESTED
            activity.shouldShowRequestPermissionRationale(permission) -> PermissionState.SHOW_RATIONALE
            else -> PermissionState.DENIED
        }

    fun savePermissionRequested() {
        sharedPrefManager.write(permissionKey, true)
    }

    private fun isPermissionGranted(activity: Activity): Boolean =
        ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

    private fun wasPermissionRequested(): Boolean =
        sharedPrefManager.readBoolean(permissionKey)

}