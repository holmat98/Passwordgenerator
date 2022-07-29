package com.mateuszholik.passwordgenerator.ui.passwords.adapters

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.domain.models.PasswordType
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.models.PasswordsViewHolder

class PasswordsAdapter(
    private val copyToClipboard: (String, String) -> Unit,
    private val calculateProgress: (String) -> Int,
    private val navigateToPasswordDetails: (Password) -> Unit,
    private val createPasswordViewHolder: (ViewGroup, Int) -> PasswordsViewHolder
) :
    RecyclerView.Adapter<PasswordsViewHolder>() {

    private val passwords: MutableList<PasswordType> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswords(list: List<PasswordType>) {
        passwords.clear()
        passwords.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder =
        createPasswordViewHolder(parent, viewType)

    override fun onBindViewHolder(holder: PasswordsViewHolder, position: Int) {
        val passwordType = passwords[position]
        when (holder) {
            is PasswordsViewHolder.ValidPasswordViewHolder -> holder.bind(
                passwordType,
                navigateToPasswordDetails = { navigateToPasswordDetails(it) },
                calculateProgress = { calculateProgress(it) },
                copyToClipboard = { platform, password -> copyToClipboard(platform, password) }
            )
            is PasswordsViewHolder.OutdatedPasswordViewHolder -> holder.bind(
                passwordType,
                navigateToPasswordDetails = { navigateToPasswordDetails(it) },
                calculateProgress = { calculateProgress(it) }
            )
            is PasswordsViewHolder.ExpiringPasswordViewHolder -> holder.bind(
                passwordType,
                navigateToPasswordDetails = { navigateToPasswordDetails(it) },
                calculateProgress = { calculateProgress(it) },
                copyToClipboard = { platform, password -> copyToClipboard(platform, password) }
            )
        }
    }

    override fun getItemCount(): Int = passwords.size

    override fun getItemViewType(position: Int): Int =
        when(passwords[position]) {
            is PasswordType.ValidPassword -> R.layout.item_password
            is PasswordType.ExpiringPassword -> R.layout.item_password_expiring
            is PasswordType.OutdatedPassword -> R.layout.item_password_outdated
        }
}