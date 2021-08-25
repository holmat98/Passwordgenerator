package com.example.passwordgenerator.view

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.logInBtn.setOnClickListener{
            val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            val sharedPreferences = EncryptedSharedPreferences.create(
                "password",
                masterKeyAlias,
                this@MainActivity,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM)

            if(sharedPreferences.getString("PasswordValue", "").isNullOrEmpty()){

                if(binding.passwordValueET.text.toString().isNotEmpty()){
                    val sharedPreferencesEditor = sharedPreferences.edit()
                    sharedPreferencesEditor.apply() {
                        putString("PasswordValue", binding.passwordValueET.text.toString())
                        apply()
                    }
                    openActivity()
                }
                else{
                    Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                if(sharedPreferences.getString("PasswordValue", "").equals(binding.passwordValueET.text.toString())) {
                    openActivity()
                }
                else{
                    Toast.makeText(this, "Wrong password", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun openActivity() {
        binding.lockerIV.animate().apply {
            duration = 1000
            binding.lockerIV.setImageResource(R.drawable.ic_open_locker)
        }.withEndAction {
            val intent = Intent(this@MainActivity, PasswordsActivity::class.java)
            this@MainActivity.startActivity(intent)
            this@MainActivity.finish()
        }.start()
    }
}