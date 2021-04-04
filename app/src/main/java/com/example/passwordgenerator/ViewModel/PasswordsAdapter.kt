package com.example.passwordgenerator.ViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.Model.Entities.Password
import com.example.passwordgenerator.R

class PasswordsAdapter(val passwords: LiveData<List<Password>>) : RecyclerView.Adapter<PasswordsAdapter.PasswordsHolder>() {
    inner class PasswordsHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_one_row, parent, false)
        return PasswordsHolder(view)
    }

    override fun onBindViewHolder(holder: PasswordsHolder, position: Int) {
        val platformTV = holder.itemView.findViewById<TextView>(R.id.platformNameTV)
        val passwordTV = holder.itemView.findViewById<TextView>(R.id.passwordTV)

        platformTV.text = passwords.value?.get(position)?.platformName
        passwordTV.text = passwords.value?.get(position)?.password
    }

    override fun getItemCount(): Int {
        return passwords.value?.size?: 0
    }
}