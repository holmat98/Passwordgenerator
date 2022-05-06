package com.mateuszholik.passwordgenerator.ui.loggeduser.passwords.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.data.repositories.models.Password
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordBinding

class PasswordsAdapter(
    private val copyToClipboard: (String, String) -> Unit,
    private val calculateProgress: (String) -> Int,
    private val navigateToPasswordDetails: (Password) -> Unit
) :
    RecyclerView.Adapter<PasswordsAdapter.PasswordsViewHolder>() {

    private val passwords: MutableList<Password> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswords(list: List<Password>) {
        passwords.clear()
        passwords.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsViewHolder {
        val binding = ItemPasswordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PasswordsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PasswordsViewHolder, position: Int) {
        holder.bind(passwords[position])
    }

    override fun getItemCount(): Int = passwords.size

    inner class PasswordsViewHolder(private val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(password: Password) {
            with(binding) {
                root.setOnClickListener { navigateToPasswordDetails(password) }
                platformNameTV.text = password.platformName
                passwordTV.text = password.password
                passwordScorePCV.progress = calculateProgress(password.password)
                copyPasswordIB.setOnClickListener {
                    copyToClipboard(
                        password.platformName,
                        password.password
                    )
                }
            }
        }
    }
}