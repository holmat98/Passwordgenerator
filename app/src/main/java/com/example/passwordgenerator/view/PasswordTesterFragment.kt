package com.example.passwordgenerator.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.HelperClass


class PasswordTesterFragment : Fragment(R.layout.fragment_password_tester) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val image = view.findViewById<ImageView>(R.id.resultIV)

        view.findViewById<Button>(R.id.testPasswordBtn).setOnClickListener {
            if(view.findViewById<EditText>(R.id.testPasswordValueET).text.isNotEmpty()){
                val result = HelperClass.testPassword(view.findViewById<EditText>(R.id.testPasswordValueET).text.toString())

                when(result){
                    in 0.0..0.33 -> image.setImageResource(R.drawable.ic_unsafe_password)
                    in 0.34..0.66 -> image.setImageResource(R.drawable.ic_neutral_password)
                    else -> image.setImageResource(R.drawable.ic_safe_password)
                }
            }
        }

    }
}