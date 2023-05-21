package com.mateuszholik.passwordgenerator.ui.passwords.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.getAttrColor

class PasswordsAdapter(
    private val copyToClipboard: (String, String) -> Unit,
    private val navigateToPasswordDetails: (Password) -> Unit,
) : RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder>() {

    private val passwords: MutableList<PasswordType> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswords(list: List<PasswordType>) {
        passwords.clear()
        passwords.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordViewHolder =
        PasswordViewHolder(
            ItemPasswordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: PasswordViewHolder, position: Int) {
        val passwordType = passwords[position]

        holder.bind(
            passwordType = passwordType,
            navigateToPasswordDetails = navigateToPasswordDetails,
            copyToClipboard = copyToClipboard
        )
    }

    override fun getItemCount(): Int = passwords.size

    class PasswordViewHolder(private val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            passwordType: PasswordType,
            navigateToPasswordDetails: (Password) -> Unit,
            copyToClipboard: (String, String) -> Unit
        ) {
            with(binding) {
                root.strokeColor = root.context.getAttrColor(passwordType.getAttrColorResId())
                root.setOnClickListener { navigateToPasswordDetails(passwordType.password) }
                platformNameTV.text = passwordType.password.platformName
                copyPasswordIB.setOnClickListener {
                    copyToClipboard(
                        passwordType.password.platformName,
                        passwordType.password.password
                    )
                }
            }
        }

        private fun PasswordType.getAttrColorResId(): Int =
            when (this) {
                is PasswordType.ExpiringPassword -> R.attr.colorTertiary
                is PasswordType.OutdatedPassword -> R.attr.colorError
                is PasswordType.ValidPassword -> R.attr.colorPrimary
            }
    }
}
