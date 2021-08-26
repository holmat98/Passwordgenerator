package com.example.passwordgenerator.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.Switch
import com.example.passwordgenerator.R
import com.example.passwordgenerator.model.HelperClass.sharedPreferencesName
import com.example.passwordgenerator.view.activities.MainActivity

class SettingFragment : Fragment(R.layout.fragment_setting) {


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switch = view.findViewById<Switch>(R.id.fingerprintSwitch)
        val logOutButton = view.findViewById<Button>(R.id.logOutBtn)

        val sharedPreference = context?.getSharedPreferences("fingerprintUsage", Context.MODE_PRIVATE)

        switch.isChecked = sharedPreference?.getBoolean(sharedPreferencesName, false)!!

        switch.setOnCheckedChangeListener { _, isChecked ->
            with(sharedPreference.edit()) {
                putBoolean(sharedPreferencesName, isChecked)
                apply()
            }
        }

        logOutButton.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)

            context?.startActivity(intent)
            activity?.finish()
        }
    }

}