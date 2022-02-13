package com.mateuszholik.passwordgenerator.view.fragments

import android.animation.ObjectAnimator
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.model.HelperClass


class PasswordTesterFragment : Fragment(R.layout.fragment_password_tester) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.resultIV)
        val backgroundImage = view.findViewById<ImageView>(R.id.background_image)
        val orientation = resources.configuration.orientation
        val constraintLayout = view.findViewById<ConstraintLayout>(R.id.CLProgress)
        val progressBar = view.findViewById<ProgressBar>(R.id.passwordStrengthPb)
        val passwordStengthTV = view.findViewById<TextView>(R.id.progressValueTV)

        view.findViewById<Button>(R.id.testPasswordBtn).setOnClickListener {
            if(view.findViewById<EditText>(R.id.testPasswordValueET).text.isNotEmpty()){
                constraintLayout.visibility = View.VISIBLE
                val result: Double = HelperClass.testPassword(view.findViewById<EditText>(R.id.testPasswordValueET).text.toString())
                when(result){
                    in 0.0..0.33 -> {
                        image.setImageResource(R.drawable.ic_unsafe_password)
                        backgroundImage.setImageResource(
                                when(orientation){
                                    Configuration.ORIENTATION_LANDSCAPE -> R.drawable.background3_red_horizontal
                                    else -> R.drawable.background3_red
                                }
                        )
                    }
                    in 0.34..0.66 -> {
                        image.setImageResource(R.drawable.ic_neutral_password)
                        backgroundImage.setImageResource(
                                when(orientation){
                                    Configuration.ORIENTATION_LANDSCAPE -> R.drawable.background3_yellow_horizontal
                                    else -> R.drawable.background3_yellow
                                }
                        )
                    }
                    else -> {
                        image.setImageResource(R.drawable.ic_safe_password)
                        backgroundImage.setImageResource(
                                when(orientation){
                                    Configuration.ORIENTATION_LANDSCAPE -> R.drawable.background3_green_horizontal
                                    else -> R.drawable.background3
                                }
                        )
                    }
                }

                val resultInt = (result*100).toInt()
                ObjectAnimator.ofInt(progressBar, "progress", resultInt)
                        .setDuration(2000)
                        .start()
                val resultText = "${resultInt}%"
                passwordStengthTV.text = resultText
            }
        }

    }
}