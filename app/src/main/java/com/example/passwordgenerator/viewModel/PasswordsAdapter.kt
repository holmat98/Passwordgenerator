package com.example.passwordgenerator.viewModel

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.passwordgenerator.model.entities.Password
import com.example.passwordgenerator.model.HelperClass
import com.example.passwordgenerator.R
import java.math.RoundingMode

class PasswordsAdapter(val passwords: LiveData<List<Password>>, val fragmentManager: FragmentManager, val viewModel: PasswordViewModel, val context: Context?) : RecyclerView.Adapter<PasswordsAdapter.PasswordsHolder>() {
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

        holder.itemView.setOnClickListener {

            val newPasswordET = EditText(context).apply {
                hint = "New password"
                inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }

            val dialog = AlertDialog.Builder(context).apply {
                setTitle("Edit password")
                setView(newPasswordET)
                setPositiveButton("YES"){ dialog, _ ->
                    val newPaswd: String = newPasswordET.text.toString()
                    if(newPaswd.isNotEmpty()){
                        var item: Password = passwords.value?.get(position)!!
                        item.password = newPaswd
                        viewModel.update(item)
                        Toast.makeText(context, "You edited password", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }

                setNegativeButton("NO"){dialog, _ -> dialog.dismiss()}
            }

            dialog.show()
        }

        holder.itemView.setOnLongClickListener {
            val clipboardManager = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("text", passwords.value?.get(position)?.password)

            clipboardManager.setPrimaryClip(clipData)

            val vibrator: Vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            val effect: VibrationEffect = VibrationEffect.createOneShot(200, VibrationEffect.EFFECT_HEAVY_CLICK)

            vibrator.vibrate(effect)

            Toast.makeText(context, "You copied the password", Toast.LENGTH_SHORT).show()
            true
        }

    }

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