package com.mateuszholik.passwordgenerator.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.passwordgenerator.databinding.ItemValidationResultBinding
import com.mateuszholik.passwordvalidation.models.PasswordValidationResult
import com.mateuszholik.passwordvalidation.models.PasswordValidationType

class PasswordValidationAdapter(
    private val validationTypeToText: (PasswordValidationType) -> String
) : RecyclerView.Adapter<PasswordValidationAdapter.ValidationResultViewHolder>() {

    private val validationResults: MutableList<PasswordValidationResult> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun addValidationResult(result: PasswordValidationResult) {
        validationResults.add(result)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ValidationResultViewHolder {
        val binding = ItemValidationResultBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ValidationResultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ValidationResultViewHolder, position: Int) {
        holder.bind(validationResults[position])
    }

    override fun getItemCount(): Int = validationResults.size

    inner class ValidationResultViewHolder(private val binding: ItemValidationResultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(validationResult: PasswordValidationResult) {

            binding.animatedCheckbox.apply {
                isPositive = validationResult.validationResult
                text = validationTypeToText(validationResult.validationType)
            }
        }
    }
}