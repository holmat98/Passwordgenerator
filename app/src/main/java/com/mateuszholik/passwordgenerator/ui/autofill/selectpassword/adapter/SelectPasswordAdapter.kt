package com.mateuszholik.passwordgenerator.ui.autofill.selectpassword.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.data.repositories.models.AutofillPasswordDetails
import com.mateuszholik.passwordgenerator.databinding.ItemSelectablePasswordBinding

class SelectPasswordAdapter(
    private val onPasswordSelected: (AutofillPasswordDetails) -> Unit,
) : RecyclerView.Adapter<SelectPasswordAdapter.SelectPasswordViewHolder>() {

    private val passwords: MutableList<AutofillPasswordDetails> = mutableListOf()
    private var currentSelectedPassword: AutofillPasswordDetails? = null

    @SuppressLint("NotifyDataSetChanged")
    fun addPasswords(list: List<AutofillPasswordDetails>) {
        passwords.clear()
        passwords.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectPasswordViewHolder =
        SelectPasswordViewHolder(
            ItemSelectablePasswordBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: SelectPasswordViewHolder, position: Int) {
        val password = passwords[position]

        holder.bind(
            autofillPasswordDetails = password,
            isSelected = password == currentSelectedPassword,
        ) {
            val previousSelectedPassword = currentSelectedPassword
            val indexOfPreviousSelectedPassword = passwords.indexOf(previousSelectedPassword)

            currentSelectedPassword = it
            onPasswordSelected(it)

            notifyItemChanged(indexOfPreviousSelectedPassword)
        }
    }

    override fun getItemCount(): Int = passwords.size

    class SelectPasswordViewHolder(
        private val binding: ItemSelectablePasswordBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            autofillPasswordDetails: AutofillPasswordDetails,
            isSelected: Boolean,
            onPasswordSelected: (AutofillPasswordDetails) -> Unit,
        ) {
            binding.radioButton.isChecked = isSelected
            binding.platformNameTV.text = autofillPasswordDetails.platformName
            binding.root.setOnClickListener {
                onPasswordSelected(autofillPasswordDetails)
                binding.radioButton.isChecked = true
            }
        }
    }
}
