package com.mateuszholik.passwordgenerator.managers.permissions.notifications

import android.Manifest
import androidx.annotation.RequiresApi
import com.mateuszholik.data.managers.io.SharedPrefManager
import com.mateuszholik.passwordgenerator.managers.permissions.PermissionManager

class NotificationPermissionManagerImpl(
    sharedPrefManager: SharedPrefManager
) : PermissionManager(sharedPrefManager) {

    @RequiresApi(33)
    override val permission: String = Manifest.permission.POST_NOTIFICATIONS
    override val minSdk: Int = 33
    override val permissionKey: String = "WAS_NOTIFICATION_PERMISSION_REQUESTED"
}