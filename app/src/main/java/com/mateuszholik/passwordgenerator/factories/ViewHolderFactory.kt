package com.mateuszholik.passwordgenerator.factories

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordBinding
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordExpiringBinding
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordOutdatedBinding
import com.mateuszholik.passwordgenerator.models.PasswordsViewHolder

interface ViewHolderFactory {

    fun create(parent: ViewGroup, viewType: Int): PasswordsViewHolder
}

class ViewHolderFactoryImpl : ViewHolderFactory {

    override fun create(parent: ViewGroup, viewType: Int): PasswordsViewHolder =
        when(viewType) {
            R.layout.item_password -> PasswordsViewHolder.ValidPasswordViewHolder(
                ItemPasswordBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_password_expiring -> PasswordsViewHolder.ExpiringPasswordViewHolder(
                ItemPasswordExpiringBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            R.layout.item_password_outdated -> PasswordsViewHolder.OutdatedPasswordViewHolder(
                ItemPasswordOutdatedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> error("Illegal view type")
        }
}