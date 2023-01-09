package com.mateuszholik.passwordgenerator.models

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordBinding
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordExpiringBinding
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordOutdatedBinding

sealed class PasswordsViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

    class ValidPasswordViewHolder(
        private val binding: ItemPasswordBinding
    ) : PasswordsViewHolder(binding) {

        fun bind(
            passwordType: PasswordType,
            navigateToPasswordDetails: (Password) -> Unit,
            copyToClipboard: (String, String) -> Unit
        ) {
            with(binding) {
                root.setOnClickListener { navigateToPasswordDetails(passwordType.password) }
                platformNameTV.text = passwordType.password.platformName
                passwordTV.text = passwordType.password.password
                copyPasswordIB.setOnClickListener {
                    copyToClipboard(
                        passwordType.password.platformName,
                        passwordType.password.password
                    )
                }
            }
        }
    }

    class OutdatedPasswordViewHolder(
        private val binding: ItemPasswordOutdatedBinding
    ) : PasswordsViewHolder(binding) {

        fun bind(
            passwordType: PasswordType,
            copyToClipboard: (String, String) -> Unit,
            navigateToPasswordDetails: (Password) -> Unit,
        ) {
            with(binding) {
                root.setOnClickListener { navigateToPasswordDetails(passwordType.password) }
                platformNameTV.text = passwordType.password.platformName
                passwordTV.text = passwordType.password.password
                copyPasswordIB.setOnClickListener { copyToClipboard(
                    passwordType.password.platformName,
                    passwordType.password.password
                ) }
            }
        }
    }

    class ExpiringPasswordViewHolder(
        private val binding: ItemPasswordExpiringBinding
    ) : PasswordsViewHolder(binding) {

        fun bind(
            passwordType: PasswordType,
            navigateToPasswordDetails: (Password) -> Unit,
            copyToClipboard: (String, String) -> Unit,
        ) {
            with(binding) {
                root.setOnClickListener { navigateToPasswordDetails(passwordType.password) }
                platformNameTV.text = passwordType.password.platformName
                passwordTV.text = passwordType.password.password
                copyPasswordIB.setOnClickListener {
                    copyToClipboard(
                        passwordType.password.platformName,
                        passwordType.password.password
                    )
                }
            }
        }
    }
}
