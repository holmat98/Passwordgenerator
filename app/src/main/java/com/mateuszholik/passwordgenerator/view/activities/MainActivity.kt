package com.mateuszholik.passwordgenerator.view.activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.mateuszholik.passwordgenerator.R
import com.mateuszholik.passwordgenerator.databinding.ActivityMainBinding
import com.mateuszholik.passwordgenerator.model.Cryptography
import com.mateuszholik.passwordgenerator.model.HelperClass.sharedPreferencesName

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fingerprintUsage: Boolean = false
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
    get() = @RequiresApi(Build.VERSION_CODES.P)
    object : BiometricPrompt.AuthenticationCallback(){
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

        Log.d("TEST", "FingerprintUsage: $fingerprintUsage")

        if(fingerprintUsage && checkBiometricSupport()){
            binding.logInWithFingerprintBtn.visibility = View.VISIBLE
        }

        binding.logInWithFingerprintBtn.setOnClickListener {
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle(getString(R.string.login_with_biometrics))
                .setNegativeButton(getString(R.string.cancel), this.mainExecutor, { _, _ ->
                    Toast.makeText(this@MainActivity, getString(R.string.cancelled_biometric), Toast.LENGTH_SHORT).show()
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(), mainExecutor, authenticationCallback)
        }

        binding.displayInfoBtn.setOnClickListener{
            Toast.makeText(this, getString(R.string.info), Toast.LENGTH_SHORT).show()
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
                    Cryptography.generateKey()
                    val sharedPreferencesEditor = sharedPreferences.edit()
                    sharedPreferencesEditor.apply() {
                        putString("PasswordValue", binding.passwordValueET.text.toString())
                        apply()
                    }
                    openActivity()
                    Log.d("TEST", "1")
                }
                else{
                    Toast.makeText(this, "Password is empty", Toast.LENGTH_SHORT).show()
                }
            }
            else {
                if(sharedPreferences.getString("PasswordValue", "").equals(binding.passwordValueET.text.toString())) {
                    openActivity()
                    Log.d("TEST", "2")
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