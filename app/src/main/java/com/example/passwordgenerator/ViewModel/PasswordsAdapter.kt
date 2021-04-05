package com.example.passwordgenerator.ViewModel

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.Model.Entities.Password
import com.example.passwordgenerator.Model.HelperClass
import com.example.passwordgenerator.R
import kotlinx.android.synthetic.main.fragment_password_tester.*
import java.math.BigDecimal
import java.math.RoundingMode

class PasswordsAdapter(val passwords: LiveData<List<Password>>) : RecyclerView.Adapter<PasswordsAdapter.PasswordsHolder>() {
    inner class PasswordsHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_one_row, parent, false)
        return PasswordsHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: PasswordsHolder, position: Int) {
        val platformTV = holder.itemView.findViewById<TextView>(R.id.platformNameTV)
        val passwordTV = holder.itemView.findViewById<TextView>(R.id.passwordTV)
        val progressBar = holder.itemView.findViewById<ProgressBar>(R.id.passwordStrengthPb)
        val progressValueTV = holder.itemView.findViewById<TextView>(R.id.progressValueTV)

        platformTV.text = passwords.value?.get(position)?.platformName
        passwordTV.text = passwords.value?.get(position)?.password

        var progress: Double = HelperClass.testPassword(passwords.value?.get(position)?.password!!)
        progress = (progress*100).toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toDouble()

        ObjectAnimator.ofInt(progressBar, "progress", progress.toInt())
                .setDuration(2000)
                .start()

        progressValueTV.text = progress.toInt().toString() + "%"

        when(progress){
            in 0.0..10.0 -> {
                progressValueTV.setTextColor(Color.parseColor("#F50C05"))
            }
            in 10.0..30.0 -> {
                progressValueTV.setTextColor(Color.parseColor("#DB7216"))
            }
            in 30.0..70.0 -> {
                progressValueTV.setTextColor(Color.parseColor("#F5D10D"))
            }
            in 70.0..90.0 -> {
                progressValueTV.setTextColor(Color.parseColor("#BBEB0C"))
            }
            in 90.0..100.0 -> {
                progressValueTV.setTextColor(Color.parseColor("#13EB48"))
            }
        }

    }

    override fun getItemCount(): Int {
        return passwords.value?.size?: 0
    }
}