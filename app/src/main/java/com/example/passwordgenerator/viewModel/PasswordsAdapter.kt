package com.example.passwordgenerator.viewModel

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.Cryptography
import java.math.RoundingMode

class PasswordsAdapter(private val passwords: LiveData<List<Password>>, private val viewModel: PasswordViewModel, val context: Context?) : RecyclerView.Adapter<PasswordsAdapter.PasswordsHolder>() {
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

        Log.d("TEST", passwords.value?.get(position)?.password!!)

        val password: String = Cryptography.decryptedData(
            passwords.value?.get(position)?.passwordIv!!.toByteArray(),
            passwords.value?.get(position)?.password!!.toByteArray()
        )

        platformTV.text = passwords.value?.get(position)?.platformName
        passwordTV.text = password

        passwordTV.setOnClickListener {
            if(passwordTV.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD)
                passwordTV.inputType = InputType.TYPE_CLASS_TEXT
            else{
                passwordTV.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }

        var progress: Double = HelperClass.testPassword(password)
        progress = (progress*100).toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toDouble()

        ObjectAnimator.ofInt(progressBar, "progress", progress.toInt())
                .setDuration(2000)
                .start()

        val progressText = "${progress.toInt()}%"
        progressValueTV.text = progressText

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