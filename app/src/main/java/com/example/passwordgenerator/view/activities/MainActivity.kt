package com.example.passwordgenerator.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.passwordgenerator.R
import com.example.passwordgenerator.databinding.ActivityMainBinding
import com.example.passwordgenerator.model.HelperClass.sharedPreferencesName

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fingerprintUsage: Boolean = false
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
    get() = object : BiometricPrompt.AuthenticationCallback(){
        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
            super.onAuthenticationError(errorCode, errString)
            Toast.makeText(this@MainActivity, getString(R.string.error_biometric), Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationFailed() {
            super.onAuthenticationFailed()
            Toast.makeText(this@MainActivity, getString(R.string.failed_biometric), Toast.LENGTH_SHORT).show()
        }

        override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
            super.onAuthenticationSucceeded(result)
            openActivity()
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = this.getSharedPreferences("fingerprintUsage", Context.MODE_PRIVATE)
        fingerprintUsage = sharedPreferences.getBoolean(sharedPreferencesName, false)

        if(fingerprintUsage && checkBiometricSupport()){
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle(getString(R.string.login_with_biometrics))
                .setNegativeButton(getString(R.string.cancel), this.mainExecutor, { _, _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.cancelled_biometric), Toast.LENGTH_SHORT).show()
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }

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
            duration = 400
            binding.lockerIV.setImageResource(R.drawable.ic_open_locker)
        }.withEndAction {
            val intent = Intent(this@MainActivity, PasswordsActivity::class.java)
            this@MainActivity.startActivity(intent)
            this@MainActivity.finish()
        }.start()
    }

    private fun getCancellationSignal(): CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            Toast.makeText(this@MainActivity, getString(R.string.cancelled_biometric), Toast.LENGTH_SHORT).show()
        }

        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport() : Boolean{
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, getString(R.string.biometric_permission), Toast.LENGTH_SHORT).show()
            return false
        }

        return if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            true
        } else true

    }
}