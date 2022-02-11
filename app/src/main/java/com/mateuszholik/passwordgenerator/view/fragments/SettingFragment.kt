package com.mateuszholik.passwordgenerator.view.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.model.HelperClass.sharedPreferencesName
import com.mateuszholik.passwordgenerator.view.activities.MainActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class SettingFragment : Fragment(R.layout.fragment_setting) {


    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val switch = view.findViewById<Switch>(R.id.fingerprintSwitch)
        val logOutButton = view.findViewById<Button>(R.id.logOutBtn)
        val changePasswordBtn = view.findViewById<Button>(R.id.changePasswordBtn)

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

        changePasswordBtn.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialog = BottomSheetDialog(requireContext())
            val bottomSheetView = layoutInflater.inflate(
                R.layout.change_password_bottom_sheet_dialog,
                null
            )

            bottomSheetView.findViewById<Button>(R.id.saveBtn).setOnClickListener {
                val password = bottomSheetView.findViewById<EditText>(R.id.newPasswordET).text.toString()
                if(password.isNotEmpty()){
                    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
                    val sharedPreferences = EncryptedSharedPreferences.create(
                        "password",
                        masterKeyAlias,
                        requireContext(),
                        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)
                    val sharedPreferencesEditor = sharedPreferences.edit()
                    sharedPreferencesEditor.apply() {
                        putString("PasswordValue", password)
                        apply()
                    }
                    Toast.makeText(context, getString(R.string.password_edited), Toast.LENGTH_SHORT).show()
                    bottomSheetDialog.dismiss()
                }
                else{
                    Toast.makeText(context, getString(R.string.password_empty), Toast.LENGTH_SHORT).show()
                }
            }

            bottomSheetView.findViewById<Button>(R.id.cancelBtn).setOnClickListener {
                bottomSheetDialog.dismiss()
            }

            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()

        }
    }

}