package com.mateuszholik.passwordgenerator.ui.onboarding.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ItemOnboardingBinding
import com.mateuszholik.passwordgenerator.ui.onboarding.model.OnBoardingScreen

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<OnBoardingScreen>() {

    override fun areItemsTheSame(oldItem: OnBoardingScreen, newItem: OnBoardingScreen) =
        oldItem === newItem

    override fun areContentsTheSame(oldItem: OnBoardingScreen, newItem: OnBoardingScreen) =
        oldItem == newItem
}

class OnBoardingAdapter :
    ListAdapter<OnBoardingScreen, OnBoardingAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding: ItemOnboardingBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_onboarding,
            parent,
            false
        )

        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ItemViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: OnBoardingScreen) {
            binding.item = item
        }
    }
}