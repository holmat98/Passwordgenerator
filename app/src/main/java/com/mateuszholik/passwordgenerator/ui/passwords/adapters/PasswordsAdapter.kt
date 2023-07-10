package com.mateuszholik.passwordgenerator.ui.passwords.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.data.repositories.models.PasswordInfo
import com.mateuszholik.passwordgenerator.databinding.ItemPasswordBinding
import com.mateuszholik.passwordgenerator.extensions.getAttrColor
import com.mateuszholik.passwordgenerator.extensions.getAttrColorResId

class PasswordsAdapter(
    private val navigateToPasswordDetails: (PasswordInfo) -> Unit,
) : RecyclerView.Adapter<PasswordsAdapter.PasswordViewHolder>() {

    private val passwords: MutableList<PasswordInfo> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswords(list: List<PasswordInfo>) {
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
            passwordInfo = passwordType,
            navigateToPasswordDetails = navigateToPasswordDetails
        )
    }

    override fun getItemCount(): Int = passwords.size

    class PasswordViewHolder(private val binding: ItemPasswordBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            passwordInfo: PasswordInfo,
            navigateToPasswordDetails: (PasswordInfo) -> Unit,
        ) {
            with(binding) {
                val color =
                    root.context.getAttrColor(passwordInfo.passwordValidity.getAttrColorResId())

                root.strokeColor = color
                root.setOnClickListener { navigateToPasswordDetails(passwordInfo) }
                circularProgressBar.secondaryColor = color
                circularProgressBar.animateProgress(passwordInfo.passwordScore)
                platformNameTV.text = passwordInfo.platformName
            }
        }
    }
}
