package com.mateuszholik.passwordgenerator.ui.permissions.notification

import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.di.utils.NamedConstants.NOTIFICATION_PERMISSION
import com.mateuszholik.passwordgenerator.ui.permissions.BasePermissionFragment

class NotificationPermissionFragment : BasePermissionFragment(NOTIFICATION_PERMISSION) {

    override val titleResId: Int = R.string.notification_permission_title
    override val descriptionResId: Int = R.string.notification_permission_description
    override val rationaleMessageResId: Int = R.string.notification_permission_rationale
    override val imageResId: Int = R.drawable.ic_push_notification
    override val onPermissionGrantedResId: Int = R.id.action_notificationPermissionFragment_to_logInFragment
    override val isBottomNavVisible: Boolean = false
}