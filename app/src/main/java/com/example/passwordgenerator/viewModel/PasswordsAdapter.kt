package com.example.passwordgenerator.viewModel

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.Cryptography
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.math.RoundingMode

class PasswordsAdapter(private val passwords: LiveData<List<Password>>, private val viewModel: PasswordViewModel, val context: Context?, val activity: Activity) : RecyclerView.Adapter<PasswordsAdapter.PasswordsHolder>() {
    inner class PasswordsHolder(view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasswordsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.password_one_row, parent, false)
        return PasswordsHolder(view)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: PasswordsHolder, position: Int) {
        val platformTV = holder.itemView.findViewById<TextView>(R.id.platformNameTV)
        val passwordImage = holder.itemView.findViewById<ImageView>(R.id.passwordItemIV)

        Log.d("TEST", passwords.value?.get(position)?.password!!)

        val password: String = Cryptography.decryptedData(
            passwords.value?.get(position)?.passwordIv!!.toByteArray(Charsets.ISO_8859_1),
            passwords.value?.get(position)?.password!!.toByteArray(Charsets.ISO_8859_1)
        )

        platformTV.text = passwords.value?.get(position)?.platformName

        var progress: Double = HelperClass.testPassword(password)
        progress = (progress*100).toBigDecimal().setScale(0, RoundingMode.HALF_EVEN).toDouble()

        when(progress){
            in 0.0..33.0 -> {
                passwordImage.setImageResource(R.drawable.ic_unsafe_password)
                platformTV.setTextColor(R.color.veryWeak)
            }
            in 33.1..66.0 -> {
                passwordImage.setImageResource(R.drawable.ic_neutral_password)
                platformTV.setTextColor(R.color.neutral)
            }
            else -> {
                passwordImage.setImageResource(R.drawable.ic_safe_password)
                platformTV.setTextColor(R.color.veryStrong)
            }
        }

        holder.itemView.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(context!!)
            val bottomSheetView = activity.layoutInflater.inflate(
                R.layout.manage_password_bottom_sheet_dialog,
                null
            )

            bottomSheetView.findViewById<ImageButton>(R.id.deletePasswordBtn).setOnClickListener {
                removeAt(position)
            }

            val progressBar = bottomSheetView.findViewById<ProgressBar>(R.id.passwordStrengthPb)

            ObjectAnimator.ofInt(progressBar, "progress", progress.toInt())
                .setDuration(2000)
                .start()

            val text = "${progress}%"
            bottomSheetView.findViewById<TextView>(R.id.progressValueTV).text = text

            val passwordTV = bottomSheetView.findViewById<TextView>(R.id.passwordTV)

            passwordTV.text = password

            passwordTV.setOnClickListener {
                passwordTV.inputType = InputType.TYPE_CLASS_TEXT
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeAt(position: Int){
        val dialog = AlertDialog.Builder(context).apply{
            setTitle("Delete")
            setMessage("Do you really want to delete this?")
            setPositiveButton("YES"){dialog, _ ->
                viewModel.delete(passwords.value?.get(position)!!)
                notifyDataSetChanged()
                Toast.makeText(context,"Deleted", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            setNegativeButton("NO"){dialog, _ ->
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    override fun getItemCount(): Int {
        return passwords.value?.size?: 0
    }
}