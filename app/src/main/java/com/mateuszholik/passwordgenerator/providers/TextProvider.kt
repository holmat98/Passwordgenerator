package com.mateuszholik.passwordgenerator.providers

import android.content.Context
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.models.MessageType

interface TextProvider {

    fun provide(messageType: MessageType): String
}

class TextProviderImpl(private val context: Context) : TextProvider {

    override fun provide(messageType: MessageType): String =
        when (messageType) {
            MessageType.PASSWORD_GENERATION_ERROR -> context.getString(R.string.generate_password_error)
            MessageType.PASSWORD_EDITION_ERROR -> context.getString(R.string.edit_password_error)
            MessageType.DEFAULT_ERROR -> context.getString(R.string.error_message)
            MessageType.LOGIN_ERROR -> context.getString(R.string.log_in_error)
            MessageType.CREATE_PIN_ERROR -> context.getString(R.string.create_pin_error)
            MessageType.VALIDATION_ERROR -> context.getString(R.string.password_validation_error)
            MessageType.DELETE_PASSWORD_ERROR -> context.getString(R.string.password_details_delete_password_error)
            MessageType.GET_PASSWORDS_ERROR -> context.getString(R.string.passwords_get_passwords_error)
            MessageType.SAVE_PASSWORD_ERROR -> context.getString(R.string.save_password_error)
            MessageType.SAVE_BIOMETRIC_AUTH_OPTION_ERROR -> context.getString(R.string.settings_error_on_saving_biometric_auth_setting)
            MessageType.SAVE_PASSWORD_VALIDITY_OPTION_ERROR -> context.getString(R.string.settings_error_saving_password_validity)
            MessageType.GET_BIOMETRIC_AUTH_OPTION_ERROR -> context.getString(R.string.settings_error_get_biometric_auth_setting)
            MessageType.BIOMETRIC_AUTH_ERROR -> context.getString(R.string.biometric_authentication_error)
            MessageType.BIOMETRIC_AUTH_FAILED -> context.getString(R.string.biometric_authentication_failed)
            MessageType.COPIED_TO_CLIPBOARD -> context.getString(R.string.message_password_copied_to_clipboard)
            MessageType.LOGIN_WRONG_PIN_ERROR -> context.getString(R.string.log_in_wrong_pin)
            MessageType.CREATE_PIN_WRONG_PIN_ERROR -> context.getString(R.string.create_pin_wrong_pin)
            MessageType.SAVE_PASSWORD_SUCCESS -> context.getString(R.string.save_password_saved)
        }
}
